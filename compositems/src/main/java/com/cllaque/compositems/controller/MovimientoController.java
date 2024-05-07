package com.cllaque.compositems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.compositems.domain.Movimiento;
import com.cllaque.compositems.dto.CrearMovimientoReq;
import com.cllaque.compositems.service.MovimientoService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService){
        this.movimientoService = movimientoService;
    }

    @GetMapping("/obtenerMovimientos/{cuentaId}")
    public Flux<Movimiento> obtenerMovimientosPorCuentaId(@PathVariable String cuentaId, @RequestParam(defaultValue = "0") Integer page, 
    @RequestParam(defaultValue = "5") Integer numberOfItems){
        return this.movimientoService.obtenerMovimientosPorCuenta(cuentaId, page, numberOfItems);
    }

    @PostMapping("/crearMovimiento")
    public void crearMovimiento(@RequestBody CrearMovimientoReq req){
        this.movimientoService.crearMovimiento(req);
    }

    
}
