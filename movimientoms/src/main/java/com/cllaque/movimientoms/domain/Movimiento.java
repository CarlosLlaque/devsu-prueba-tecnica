package com.cllaque.movimientoms.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import lombok.Data;

@Data
public class Movimiento implements Persistable<UUID>{
    @Id
    private UUID movimientoId;

    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
    
    private UUID cuentaId;

    @Override
    public UUID getId() {
        return movimientoId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
