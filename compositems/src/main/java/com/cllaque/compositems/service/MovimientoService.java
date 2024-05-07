package com.cllaque.compositems.service;

import com.cllaque.compositems.domain.Movimiento;
import com.cllaque.compositems.dto.CrearMovimientoReq;

import reactor.core.publisher.Flux;

public interface MovimientoService {
    Flux<Movimiento> obtenerMovimientosPorCuenta(String cuentaId, Integer page, Integer numberOfItems);
    public void crearMovimiento(CrearMovimientoReq req);
}
