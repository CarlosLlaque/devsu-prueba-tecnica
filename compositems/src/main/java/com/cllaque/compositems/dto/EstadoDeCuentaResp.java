package com.cllaque.compositems.dto;

import java.util.List;
import java.util.UUID;

import com.cllaque.compositems.domain.Movimiento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoDeCuentaResp {
    private UUID cuentaId;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private String dni;
    private Double saldoActual;

    List<Movimiento> movimientos;
}
