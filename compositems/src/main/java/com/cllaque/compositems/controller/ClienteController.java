package com.cllaque.compositems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.compositems.domain.Cliente;
import com.cllaque.compositems.dto.CrearClienteReq;
import com.cllaque.compositems.service.ClienteService;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping("/obtenerCliente/{dni}")
    public Mono<Cliente> obtenerCliente(@PathVariable String dni, @RequestParam(defaultValue = "0") int delay, 
            @RequestParam(defaultValue = "0") int faultPercent){
        System.out.println("delay: " + delay);
        System.out.println("faultPercent: " + faultPercent);
        return this.clienteService.obtenerCliente(dni, delay, faultPercent);
    }

    @PostMapping("/crearCliente")
    public void crearCliente(@RequestBody CrearClienteReq req){
        this.clienteService.crearCliente(req);
    }
}
