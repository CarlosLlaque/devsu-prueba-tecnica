package com.cllaque.cuentams.DTO.Cuenta;

import java.util.UUID;

import lombok.Data;

@Data
public class ActualizarCuentaReq {
    private UUID cuentaId;
    private String tipoCuenta;
    private Boolean estado;
}
