package com.cllaque.movimientoms.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;

import com.cllaque.movimientoms.domain.Movimiento;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovimientoRepository extends ReactiveCrudRepository<Movimiento, UUID>{
    Flux<Movimiento> findByCuentaIdOrderByFechaDesc(UUID cuentaId, Pageable pageable);
    Flux<Movimiento> findByCuentaIdAndFechaBetween(UUID cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    @Transactional
    @Modifying
    @Query("UPDATE Movimiento m " +
            "SET m.tipo_movimiento = COALESCE(:tipoMovimiento, m.tipo_movimiento) " +
            "WHERE m.movimiento_id = :movimientoId")
    Mono<Void> patchMovimiento(UUID movimientoId, String tipoMovimiento);
}
