package com.cllaque.cuentams.DTO.Cuenta;

import lombok.Data;

@Data
public class CrearCuentaReq {
    private String dni; 
    private Double saldoInicial;
    private String tipoCuenta;
}
