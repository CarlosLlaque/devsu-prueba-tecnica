package com.cllaque.compositems.dto;

import lombok.Data;

@Data
public class CrearCuentaReq {
    private String dni; 
    private Double saldoInicial;
    private String tipoCuenta;
}
