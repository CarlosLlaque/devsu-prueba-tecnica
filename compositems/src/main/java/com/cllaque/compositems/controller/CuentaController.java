package com.cllaque.compositems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.compositems.dto.CrearCuentaReq;
import com.cllaque.compositems.dto.CuentaResp;
import com.cllaque.compositems.service.CuentaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/cuenta")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService){
        this.cuentaService = cuentaService;
    }

    @GetMapping("/cuentasPorDni/{dni}")
    public Flux<CuentaResp> obtenerCuentasPorDni(@PathVariable String dni){
        return this.cuentaService.obtenerCuentasPorDni(dni);
    }

    @PostMapping("/crearCuenta")
    public Mono<CuentaResp> crearCuenta(@RequestBody CrearCuentaReq req){
        return this.cuentaService.creacCuenta(req);
    }

}
