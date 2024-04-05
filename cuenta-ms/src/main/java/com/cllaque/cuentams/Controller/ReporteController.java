package com.cllaque.cuentams.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.cuentams.DTO.Reporte.EstadoDeCuentaResp;
import com.cllaque.cuentams.Domain.Cuenta;
import com.cllaque.cuentams.Service.ReporteService;

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
