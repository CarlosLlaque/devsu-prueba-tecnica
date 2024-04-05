package com.cllaque.cuentams.DTO.Movimiento;

import java.util.UUID;

import lombok.Data;

@Data
public class CrearMovimientoReq {
    private UUID cuentaId;
    private String tipoMovimiento;
    private Double valor;
}
