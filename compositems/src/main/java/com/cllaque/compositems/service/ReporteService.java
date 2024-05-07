package com.cllaque.compositems.service;

import java.time.LocalDate;
import com.cllaque.compositems.dto.EstadoDeCuentaResp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReporteService {
    public Flux<EstadoDeCuentaResp> obtenerEstadoDeCuenta(String dni, LocalDate fechaInicio, LocalDate fechaFin);
}
