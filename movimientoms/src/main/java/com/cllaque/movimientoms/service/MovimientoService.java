package com.cllaque.movimientoms.service;

import java.time.LocalDate;
import java.util.UUID;

import com.cllaque.movimientoms.domain.Movimiento;
import com.cllaque.movimientoms.dto.movimiento.ActualizarMovimientoReq;
import com.cllaque.movimientoms.dto.movimiento.CrearMovimientoReq;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovimientoService {
    public Mono<Void> eliminarMovimiento(UUID movimientoId);
    public Mono<Movimiento> obtenerMovimiento(UUID movimientoId);
    public Mono<Movimiento> crearMovimiento(CrearMovimientoReq req);
    public Mono<Void> actualizarMovimiento(ActualizarMovimientoReq req);
    public Flux<Movimiento> obtenerMovimientosPorCuenta(UUID cuentaId, Integer page, Integer numberOfItems);
    public Flux<Movimiento> obtenerMovimientosPorCuentaRangoFechas(UUID cuentaId, LocalDate fechaInicio, LocalDate fechaFin);
}
