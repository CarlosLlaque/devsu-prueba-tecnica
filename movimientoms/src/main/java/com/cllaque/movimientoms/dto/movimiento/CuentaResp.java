package com.cllaque.movimientoms.dto.movimiento;

import java.util.UUID;

import lombok.Data;

@Data
public class CuentaResp {
    private UUID cuentaId;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private String dni;
    private Double saldoActual;
}
