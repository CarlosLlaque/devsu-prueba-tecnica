package com.cllaque.cuentams.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cllaque.cuentams.DTO.Movimiento.ActualizarMovimientoReq;
import com.cllaque.cuentams.DTO.Movimiento.CrearMovimientoReq;
import com.cllaque.cuentams.Domain.Movimiento;
import com.cllaque.cuentams.Excepcion.BadRequestException;
import com.cllaque.cuentams.Excepcion.GenericException;
import com.cllaque.cuentams.Excepcion.NotFoundException;
import com.cllaque.cuentams.Repository.CuentaRepository;
import com.cllaque.cuentams.Repository.MovimientoRepository;
import com.cllaque.cuentams.Service.CuentaService;
import com.cllaque.cuentams.Service.MovimientoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovimientoServiceImpl implements MovimientoService{
    private MovimientoRepository movimientoRepository;
    private CuentaService cuentaService;
    private CuentaRepository cuentaRepository;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaService cuentaService, CuentaRepository cuentaRepository){
        this.movimientoRepository = movimientoRepository;
        this.cuentaService = cuentaService;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Mono<Void> eliminarMovimiento(UUID movimientoId) {
        return Mono.fromCallable(()->this.movimientoRepository.existsById(movimientoId))
            .map(exists->{
                if(!exists){
                    return Mono.error(new NotFoundException("El movimiento no existe"));
                } else {
                    return Mono.fromRunnable(()->this.movimientoRepository.deleteById(movimientoId))
                    .onErrorResume(error -> {
                        error.printStackTrace();
                        return Mono.error(new GenericException("No se pudo eliminar el movimiento", error));
                    });
                }
            }).then();
    }

    @Override
    public Mono<Movimiento> obtenerMovimiento(UUID movimientoId) {
        return Mono.fromCallable(()->this.movimientoRepository.findById(movimientoId))
            .flatMap(opt->{
                if(!opt.isPresent()){
                    return Mono.error(new NotFoundException("El movimiento no existe"));
                } else {
                    return Mono.just(opt.get());
                }
            });
    }

    @Override
    public Mono<Movimiento> crearMovimiento(CrearMovimientoReq req) {
        return this.cuentaService.obtenerSaldoCuenta(req.getCuentaId())
            .flatMap(saldo->{
                var movimiento = new Movimiento();
                movimiento.setFecha(LocalDateTime.now());
                movimiento.setCuentaId(req.getCuentaId());
                movimiento.setTipoMovimiento(req.getTipoMovimiento());

                var valorMovimiento = req.getValor();
                movimiento.setValor(valorMovimiento);

                if(valorMovimiento < 0 && saldo < Math.abs(valorMovimiento)){
                    return Mono.error(new BadRequestException("La cuenta no cuenta con el saldo suficiente para realizar el movimiento"));
                }

                movimiento.setSaldo(saldo + valorMovimiento);

                return Mono.just(this.movimientoRepository.save(movimiento));
            });
    }

    @Override
    public Mono<Void> actualizarMovimiento(ActualizarMovimientoReq req) {
        var exists = this.movimientoRepository.existsById(req.getMovimientoId());
        if(!exists){
            return Mono.error(new NotFoundException("El movimiento no existe"));
        }

        return Mono.fromRunnable(()->this.movimientoRepository.patchMovimiento(req.getMovimientoId(), req.getTipoMovimiento()));
    }

    @Override
    public Flux<Movimiento> obtenerMovimientosPorCuenta(UUID cuentaId, Integer page, Integer numberOfItems) {
        return Flux.fromIterable(this.movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId, PageRequest.of(page, numberOfItems)))
        .switchIfEmpty(Flux.error(new NotFoundException("La cuenta no cuenta con movimientos")));
    }

    @Override
    public Flux<Movimiento> obtenerMovimientosPorCuentaRangoFechas(UUID cuentaId, LocalDate fechaInicio,
            LocalDate fechaFin) {
        var fechaHoraInicio = fechaInicio.atStartOfDay();
        var fechaHoraFin = fechaFin.atTime(LocalTime.MAX);

        if(!this.cuentaRepository.existsById(cuentaId))
            return Flux.error(new NotFoundException("La cuenta "+cuentaId+" no existe"));

        return Flux.fromIterable(this.movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaHoraInicio, fechaHoraFin));
    }
    
}
