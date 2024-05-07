package com.cllaque.movimientoms.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.movimientoms.dto.movimiento.ActualizarMovimientoReq;
import com.cllaque.movimientoms.dto.movimiento.CrearMovimientoReq;
import com.cllaque.movimientoms.domain.Movimiento;
import com.cllaque.movimientoms.service.MovimientoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService){
        this.movimientoService = movimientoService;
    }

    @GetMapping("{movimientoId}")
    public Mono<Movimiento> obtenerMovimiento(@PathVariable UUID movimientoId){
        return this.movimientoService.obtenerMovimiento(movimientoId);
    }

    @GetMapping("/porCuenta/{cuentaId}")
    public Flux<Movimiento> obtenerMovimientosPorCuenta(@PathVariable UUID cuentaId, @RequestParam Integer page, 
        @RequestParam Integer numberOfItems){
        return this.movimientoService.obtenerMovimientosPorCuenta(cuentaId, page, numberOfItems);
    }

    @PutMapping("/actualizar")
    public Mono<Void> actualizarMovimiento(@RequestBody ActualizarMovimientoReq req){
        return this.movimientoService.actualizarMovimiento(req);
    }

    @DeleteMapping("/eliminar/{movimientoId}")
    public Mono<Void> eliminarMovimiento(@PathVariable UUID movimientoId){
        return this.movimientoService.eliminarMovimiento(movimientoId);
    }

}
