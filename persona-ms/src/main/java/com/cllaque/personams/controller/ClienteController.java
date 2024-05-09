package com.cllaque.personams.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.personams.dto.ClienteResp;
import com.cllaque.personams.dto.CrearClienteReq;
import com.cllaque.personams.service.ClienteService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping("/{dni}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClienteResp> obtenerCliente(@PathVariable String dni, @RequestParam(defaultValue = "0") int delay,
        @RequestParam(defaultValue = "0") int faultPercent){
        System.out.println("Obteniendo cliente");
        return this.clienteService.obtenerCliente(dni, delay, faultPercent);
    }

    @PutMapping("/actualizar")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> actualizarCliente(@RequestBody CrearClienteReq req){
        return this.clienteService.actualizarCliente(req);
    }

    @DeleteMapping("/{dni}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> eliminarCliente(@PathVariable String dni){
        return this.clienteService.eliminarCliente(dni);
    }
}
