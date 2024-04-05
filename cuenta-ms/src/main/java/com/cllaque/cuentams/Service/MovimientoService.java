package com.cllaque.cuentams.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.cllaque.cuentams.DTO.Movimiento.ActualizarMovimientoReq;
import com.cllaque.cuentams.DTO.Movimiento.CrearMovimientoReq;
import com.cllaque.cuentams.Domain.Movimiento;

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
