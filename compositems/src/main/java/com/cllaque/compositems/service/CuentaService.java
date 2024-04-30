package com.cllaque.compositems.service;

import com.cllaque.compositems.dto.CrearCuentaReq;
import com.cllaque.compositems.dto.CuentaResp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CuentaService {
    Flux<CuentaResp> obtenerCuentasPorDni(String dni);
    Mono<CuentaResp> creacCuenta(CrearCuentaReq req);
}
