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
import com.cllaque.cuentams.Domain.Movimiento;
import com.cllaque.cuentams.Excepcion.GenericException;
import com.cllaque.cuentams.Excepcion.NotFoundException;
import com.cllaque.cuentams.Repository.CuentaRepository;
import com.cllaque.cuentams.Service.CuentaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CuentaServiceImpl implements CuentaService{
    private CuentaRepository cuentaRepository;
    private WebClient.Builder builder;
    @Value("${eurekaurl.personams}")
    private String personaUrl;
    @Value("${eurekaurl.movimientoms}")
    private String movimientoUrl;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, WebClient.Builder builder){
        this.cuentaRepository = cuentaRepository;
        this.builder = builder;
    }

    @Override
    public Mono<CuentaResp> crearCuenta(CrearCuentaReq req) {
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
                cuenta.setCuentaId(UUID.randomUUID());
                return this.cuentaRepository.save(cuenta)
                .onErrorMap(error -> {
                    error.printStackTrace();
                    return new GenericException("No se pudo crear la cuenta", error);
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
                cuentaResp.setSaldoActual(cuenta.getSaldoInicial());

                return cuentaResp;
            });
        });
    }

    @Override
    public Mono<Void> eliminarCuenta(UUID cuentaId) {
        return this.cuentaRepository.existsById(cuentaId)
        .flatMap(exists->{
            if(!exists){
                return Mono.error(new NotFoundException("La cuenta no existe"));
            }else{
                return this.cuentaRepository.deleteById(cuentaId)
                .onErrorMap(error -> {
                    error.printStackTrace();
                    return new GenericException("No se pudo eliminar la cuenta", error);
                });
            }
        });
    }

    @Override
    public Mono<Void> actualizarCuenta(ActualizarCuentaReq req) {
        return this.cuentaRepository.existsById(req.getCuentaId())
        .flatMap(exists->{
            if(!exists){
                return Mono.error(new NotFoundException("La cuenta no existe"));
            }else{
                return this.cuentaRepository.patchCuenta(req.getCuentaId(), req.getEstado(), req.getTipoCuenta())
                .onErrorMap(error -> {
                    error.printStackTrace();
                    return new GenericException("No se pudo actualizar la cuenta", error);
                });
            }
        });
    }

    @Override
    public Mono<CuentaResp> obtenerCuenta(UUID cuentaId) {
        return this.cuentaRepository.findById(cuentaId)
        .switchIfEmpty(Mono.error(new NotFoundException("La cuenta no existe")))
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
        .thenMany(this.cuentaRepository.findByDni(dni))
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
        return this.cuentaRepository.findById(cuentaId)
        .switchIfEmpty(Mono.error(new NotFoundException("La cuenta no existe")))
        .flatMap(cuenta->{
            return this.builder.baseUrl(movimientoUrl).build()
            .get()
            // .uri("/movimientos/porCuenta/" + cuentaId)
            .uri(uriBuilder-> {
                    return uriBuilder.path("/movimientos/porCuenta/" + cuentaId)
                    .queryParam("page", 0)
                    .queryParam("numberOfItems", 10)
                    .build();
                })
            .retrieve()
            .bodyToFlux(Movimiento.class)
            .onErrorResume(e->{
            e.printStackTrace();
                return Flux.empty();
            })
            .next()
            .map(movimiento->movimiento.getSaldo())
            .defaultIfEmpty(cuenta.getSaldoInicial());
        });
    }
}
