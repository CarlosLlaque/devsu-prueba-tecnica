package com.cllaque.cuentams.Service;

import java.util.List;
import java.util.UUID;

import com.cllaque.cuentams.DTO.Cuenta.ActualizarCuentaReq;
import com.cllaque.cuentams.DTO.Cuenta.CrearCuentaReq;
import com.cllaque.cuentams.DTO.Cuenta.CuentaResp;
import com.cllaque.cuentams.Domain.Cuenta;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CuentaService {
    public Mono<CuentaResp> obtenerCuenta(UUID cuentaId);
    public Flux<CuentaResp> obtenerCuentasPorDni(String dni);
    public Mono<CuentaResp> crearCuenta(CrearCuentaReq req);
    public Mono<Void> actualizarCuenta(ActualizarCuentaReq req);
    public Mono<Void> eliminarCuenta(UUID cuentaId);
    public Mono<Double> obtenerSaldoCuenta(UUID cuentaId);
}
