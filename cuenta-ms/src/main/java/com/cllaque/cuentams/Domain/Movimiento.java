package com.cllaque.cuentams.Domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Movimiento {
    @Id
    private UUID movimientoId;

    private LocalDateTime fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
    
    private UUID cuentaId;
}
