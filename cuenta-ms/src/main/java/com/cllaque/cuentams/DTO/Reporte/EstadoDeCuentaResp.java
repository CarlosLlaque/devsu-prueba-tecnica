package com.cllaque.cuentams.DTO.Reporte;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cllaque.cuentams.Domain.Movimiento;

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
