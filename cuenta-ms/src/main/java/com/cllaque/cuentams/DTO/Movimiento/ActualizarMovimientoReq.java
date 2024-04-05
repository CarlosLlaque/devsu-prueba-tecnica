package com.cllaque.cuentams.DTO.Movimiento;

import java.util.UUID;

import lombok.Data;

@Data
public class ActualizarMovimientoReq {
    private UUID movimientoId;
    private String tipoMovimiento;
}
