package com.cllaque.cuentams.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.cuentams.DTO.Cuenta.ActualizarCuentaReq;
import com.cllaque.cuentams.DTO.Cuenta.CrearCuentaReq;
import com.cllaque.cuentams.DTO.Cuenta.CuentaResp;
import com.cllaque.cuentams.Domain.Cuenta;
import com.cllaque.cuentams.Service.CuentaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService){
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{cuentaId}")
    public Mono<CuentaResp> obtenerCuenta(@PathVariable UUID cuentaId){
        return this.cuentaService.obtenerCuenta(cuentaId);
    }

    @GetMapping("/obtenerSaldo/{cuentaId}")
    public Mono<Double> obtenerSaldoCuenta(@PathVariable UUID cuentaId){
        return this.cuentaService.obtenerSaldoCuenta(cuentaId);
    }

    @GetMapping("/cuentasPorDni/{dni}")
    public Flux<CuentaResp> obtenerCuentasPorDni(@PathVariable String dni){
        return this.cuentaService.obtenerCuentasPorDni(dni);
    }

    @DeleteMapping("/eliminar/{cuentaId}")
    public Mono<Void> eliminarCuenta(@PathVariable UUID cuentaId){
        return this.cuentaService.eliminarCuenta(cuentaId);
    }

    @PutMapping("/actualizar")
    public Mono<Void> actualizarCuenta(@RequestBody ActualizarCuentaReq req){
        return this.cuentaService.actualizarCuenta(req);
    }

}
