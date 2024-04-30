package com.cllaque.compositems.controller;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.compositems.dto.EstadoDeCuentaResp;
import com.cllaque.compositems.service.ReporteService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private ReporteService reporteService;

    public ReporteController(ReporteService reporteService){
        this.reporteService = reporteService;
    }

    @GetMapping("/estadoCuenta")
    public Flux<EstadoDeCuentaResp> obtenerEstadoDeCuenta(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin, @RequestParam String dni){
        return this.reporteService.obtenerEstadoDeCuenta(dni, fechaInicio, fechaFin);
    }

}
