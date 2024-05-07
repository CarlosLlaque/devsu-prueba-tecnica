package com.cllaque.movimientoms.dto.movimiento;

import java.util.UUID;

import lombok.Data;

@Data
public class ActualizarMovimientoReq {
    private UUID movimientoId;
    private String tipoMovimiento;
}
