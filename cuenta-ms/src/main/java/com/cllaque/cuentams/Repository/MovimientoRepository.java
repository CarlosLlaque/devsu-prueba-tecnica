package com.cllaque.cuentams.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cllaque.cuentams.Domain.Movimiento;

import jakarta.transaction.Transactional;

public interface MovimientoRepository extends CrudRepository<Movimiento, UUID>{
    List<Movimiento> findByCuentaIdOrderByFechaDesc(UUID cuentaId, Pageable pageable);
    List<Movimiento> findByCuentaIdAndFechaBetween(UUID cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    @Transactional
    @Modifying
    @Query("UPDATE Movimiento m " +
            "SET m.tipoMovimiento = COALESCE(:tipoMovimiento, m.tipoMovimiento) " +
            "WHERE m.movimientoId = :movimientoId")
    void patchMovimiento(UUID movimientoId, String tipoMovimiento);
}
