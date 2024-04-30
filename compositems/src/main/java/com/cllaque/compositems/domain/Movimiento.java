package com.cllaque.compositems.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class Movimiento {
    private UUID movimientoId;

    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
    
    private UUID cuentaId;
}
