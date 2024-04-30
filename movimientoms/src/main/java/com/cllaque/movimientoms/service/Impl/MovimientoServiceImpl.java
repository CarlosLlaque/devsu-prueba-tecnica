package com.cllaque.movimientoms.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cllaque.movimientoms.dto.movimiento.ActualizarMovimientoReq;
import com.cllaque.movimientoms.dto.movimiento.CrearMovimientoReq;
import com.cllaque.movimientoms.dto.movimiento.CuentaResp;
import com.cllaque.movimientoms.domain.Movimiento;
import com.cllaque.movimientoms.exception.BadRequestException;
import com.cllaque.movimientoms.exception.GenericException;
import com.cllaque.movimientoms.exception.NotFoundException;
import com.cllaque.movimientoms.repository.MovimientoRepository;
import com.cllaque.movimientoms.service.MovimientoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovimientoServiceImpl implements MovimientoService{
    private MovimientoRepository movimientoRepository;
    private final WebClient.Builder builder;

    @Value("${cuentams.url}")
    private String cuentaUrl;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, WebClient.Builder builder){
        this.movimientoRepository = movimientoRepository;
        this.builder = builder;
    }

    @Override
    public Mono<Void> eliminarMovimiento(UUID movimientoId) {
        return this.movimientoRepository.existsById(movimientoId)
            .flatMap(exists->{
                if(!exists){
                    return Mono.error(new NotFoundException("El movimiento no existe"));
                } else {
                    return this.movimientoRepository.deleteById(movimientoId)
                    .onErrorMap(error -> {
                        error.printStackTrace();
                        return new GenericException("No se pudo eliminar el movimiento", error);
                    });
                }
            });
    }

    @Override
    public Mono<Movimiento> obtenerMovimiento(UUID movimientoId) {
        return this.movimientoRepository.findById(movimientoId)
        .switchIfEmpty(Mono.error(new NotFoundException("El movimiento no existe")));
    }

    @Override
    public Mono<Movimiento> crearMovimiento(CrearMovimientoReq req) {
        return this.builder.baseUrl(cuentaUrl).build()
            .get()
            .uri("/cuentas/obtenerSaldo/" + req.getCuentaId())
            .retrieve()
            .bodyToMono(Double.class)
            .onErrorMap(e->{
                e.printStackTrace();
                return new NotFoundException("La cuenta no existe");
            })
            .flatMap(saldo->{
                var movimiento = new Movimiento();
                movimiento.setFecha(LocalDateTime.now());
                movimiento.setCuentaId(req.getCuentaId());
                movimiento.setTipoMovimiento(req.getTipoMovimiento());
                movimiento.setMovimientoId(UUID.randomUUID());

                var valorMovimiento = req.getValor();
                movimiento.setValor(valorMovimiento);

                if(valorMovimiento < 0 && saldo < Math.abs(valorMovimiento)){
                    return Mono.error(new BadRequestException("La cuenta no cuenta con el saldo suficiente para realizar el movimiento"));
                }

                movimiento.setSaldo(saldo + valorMovimiento);

                return this.movimientoRepository.save(movimiento);
            });
    }

    @Override
    public Mono<Void> actualizarMovimiento(ActualizarMovimientoReq req) {
        return this.movimientoRepository.existsById(req.getMovimientoId())
        .flatMap(exists->{
            if(!exists){
                return Mono.error(new NotFoundException("El movimiento no existe"));
            } else {
                return this.movimientoRepository.patchMovimiento(req.getMovimientoId(), req.getTipoMovimiento())
                .onErrorMap(e->new GenericException("No se pudo actualziar el movimiento", e));
            }
        });
    }

    @Override
    public Flux<Movimiento> obtenerMovimientosPorCuenta(UUID cuentaId, Integer page, Integer numberOfItems) {
        return this.movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId, PageRequest.of(page, numberOfItems))
        .switchIfEmpty(Flux.error(new NotFoundException("La cuenta no cuenta con movimientos")));
    }

    @Override
    public Flux<Movimiento> obtenerMovimientosPorCuentaRangoFechas(UUID cuentaId, LocalDate fechaInicio,
            LocalDate fechaFin) {

        var fechaHoraInicio = fechaInicio.atStartOfDay();
        var fechaHoraFin = fechaFin.atTime(LocalTime.MAX);

        return this.builder.baseUrl(cuentaUrl).build()
        .get()
        .retrieve()
        .bodyToMono(CuentaResp.class)
        .flatMapMany(cuenta->this.movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaHoraInicio, fechaHoraFin))
        .onErrorResume(e->Flux.error(new NotFoundException("La cuenta "+cuentaId+" no existe")));
    }
    
}
