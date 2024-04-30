package com.cllaque.compositems.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaResp {
    private UUID cuentaId;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private String dni;
    private Double saldoActual;
}
