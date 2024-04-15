package com.cllaque.cuentams.Service.Impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cllaque.cuentams.DTO.Cuenta.ActualizarCuentaReq;
import com.cllaque.cuentams.DTO.Cuenta.CrearCuentaReq;
import com.cllaque.cuentams.DTO.Cuenta.CuentaResp;
import com.cllaque.cuentams.Domain.Cuenta;
import com.cllaque.cuentams.Excepcion.GenericException;
import com.cllaque.cuentams.Excepcion.NotFoundException;
import com.cllaque.cuentams.Repository.CuentaRepository;
import com.cllaque.cuentams.Repository.MovimientoRepository;
import com.cllaque.cuentams.Service.CuentaService;
import com.netflix.discovery.EurekaClient;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CuentaServiceImpl implements CuentaService{
    private CuentaRepository cuentaRepository;
    private MovimientoRepository movimientoRepository;
    private WebClient.Builder builder;
    private EurekaClient eurekaClient;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository,
    WebClient.Builder builder, EurekaClient eurekaClient){
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.builder = builder;
        this.eurekaClient = eurekaClient;
    }

    @Override
    public Mono<CuentaResp> crearCuenta(CrearCuentaReq req) {
        var personaUrl = eurekaClient.getNextServerFromEureka("persona-ms", false).getHomePageUrl();

        return builder.baseUrl(personaUrl)
        .build()
        .get()
        .uri("/clientes/" + req.getDni())
        .exchangeToMono(response->{
             if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                return Mono.error(new NotFoundException("No se encontró el cliente"));
            } else {
                var cuenta = new Cuenta();
                cuenta.setDni(req.getDni());
                cuenta.setEstado(true);
                cuenta.setTipoCuenta(req.getTipoCuenta());
                cuenta.setSaldoInicial(req.getSaldoInicial());
                return Mono.fromCallable(() -> this.cuentaRepository.save(cuenta))
                .onErrorResume(error -> {
                    error.printStackTrace();
                    return Mono.error(new GenericException("No se pudo crear la cuenta", error));
                });
            }
        })
        .flatMap(cuenta->{
            return this.obtenerSaldoCuenta(cuenta.getCuentaId())
            .map(saldo->{
                var cuentaResp = new CuentaResp();
                cuentaResp.setDni(cuenta.getDni());
                cuentaResp.setEstado(cuenta.getEstado());
                cuentaResp.setCuentaId(cuenta.getCuentaId());
                cuentaResp.setTipoCuenta(cuenta.getTipoCuenta());
                cuentaResp.setSaldoInicial(cuenta.getSaldoInicial());
                cuentaResp.setSaldoActual(saldo);

                return cuentaResp;
            });
        });
    }

    @Override
    public Mono<Void> eliminarCuenta(UUID cuentaId) {
        return Mono.fromCallable(()->this.cuentaRepository.existsById(cuentaId))
        .flatMap(exists->{
            if(!exists){
                return Mono.error(new NotFoundException("La cuenta no existe"));
            }else{
                return Mono.fromRunnable(()->this.cuentaRepository.deleteById(cuentaId))
                .onErrorResume(error -> {
                    error.printStackTrace();
                    return Mono.error(new GenericException("No se pudo eliminar la cuenta", error));
                });
            }
        }).then();
    }

    @Transactional
    @Override
    public Mono<Void> actualizarCuenta(ActualizarCuentaReq req) {
        return Mono.fromCallable(()->this.cuentaRepository.existsById(req.getCuentaId()))
        .flatMap(exists->{
            if(!exists){
                return Mono.error(new NotFoundException("La cuenta no existe"));
            }else{
                return Mono.fromRunnable(()->this.cuentaRepository.patchCuenta(req.getCuentaId(), req.getEstado(), req.getTipoCuenta()))
                .onErrorResume(error -> {
                    error.printStackTrace();
                    return Mono.error(new GenericException("No se pudo actualizar la cuenta", error));
                });
            }
        }).then();
    }

    @Override
    public Mono<CuentaResp> obtenerCuenta(UUID cuentaId) {
        return Mono.fromCallable(()->this.cuentaRepository.findById(cuentaId))
            .flatMap(opt->{
                if(opt.isPresent()){
                    return Mono.just(opt.get());
                }else{
                    return Mono.error(new NotFoundException("La cuenta no existe"));
                }
            })
            .flatMap(cuenta->{
                return this.obtenerSaldoCuenta(cuentaId)
                .map(saldo->{
                    var cuentaResp = new CuentaResp();
                    cuentaResp.setDni(cuenta.getDni());
                    cuentaResp.setEstado(cuenta.getEstado());
                    cuentaResp.setCuentaId(cuenta.getCuentaId());
                    cuentaResp.setTipoCuenta(cuenta.getTipoCuenta());
                    cuentaResp.setSaldoInicial(cuenta.getSaldoInicial());
                    cuentaResp.setSaldoActual(saldo);

                    return cuentaResp;
                });
            });
    }

    @Override
    public Flux<CuentaResp> obtenerCuentasPorDni(String dni) {
        var personaUrl = eurekaClient.getNextServerFromEureka("persona-ms", false).getHomePageUrl();
        return builder.baseUrl(personaUrl)
        .build()
        .get()
        .uri("/clientes/" + dni)
        .exchangeToMono(response->{
             if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                return Mono.error(new NotFoundException("No se encontró el cliente"));
            } else {
                return Mono.empty();
            }
        })
        .thenMany(Flux.fromIterable(this.cuentaRepository.findByDni(dni)))
        .switchIfEmpty(Flux.error(new NotFoundException("El cliente no cuenta con cuentas asociadas")))
        .flatMap(cuenta->{
            return obtenerSaldoCuenta(cuenta.getCuentaId())
            .map(saldo->{
                var cuentaResp = new CuentaResp();
                cuentaResp.setDni(cuenta.getDni());
                cuentaResp.setEstado(cuenta.getEstado());
                cuentaResp.setCuentaId(cuenta.getCuentaId());
                cuentaResp.setTipoCuenta(cuenta.getTipoCuenta());
                cuentaResp.setSaldoInicial(cuenta.getSaldoInicial());
                cuentaResp.setSaldoActual(saldo);

                return cuentaResp;
            });
        });

    }

    @Override
    public Mono<Double> obtenerSaldoCuenta(UUID cuentaId) {
        return Mono.fromCallable(()->this.cuentaRepository.findById(cuentaId))
        .flatMap(opt->{
            if(!opt.isPresent()){
                return Mono.error(new NotFoundException("La cuenta no existe"));
            } else {
                return Mono.just(opt.get());
            }
        })
        .map(cuenta->{
            var movimientos = this.movimientoRepository.findByCuentaIdOrderByFechaDesc(cuentaId, PageRequest.of(0,1));
            return movimientos.isEmpty() ? cuenta.getSaldoInicial() : movimientos.get(0).getSaldo();
        });
    }
}
