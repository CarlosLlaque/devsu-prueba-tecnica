package com.cllaque.cuentams.Service;

import java.time.LocalDate;
import java.util.List;

import com.cllaque.cuentams.DTO.Reporte.EstadoDeCuentaResp;
import com.cllaque.cuentams.Domain.Cuenta;

import reactor.core.publisher.Flux;

public interface ReporteService {
    public Flux<EstadoDeCuentaResp> obtenerEstadoDeCuenta(String dni, LocalDate fechaInicio, LocalDate fechaFin);
}
