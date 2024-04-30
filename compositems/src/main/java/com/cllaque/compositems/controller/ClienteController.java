package com.cllaque.compositems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cllaque.compositems.domain.Cliente;
import com.cllaque.compositems.dto.CrearClienteReq;
import com.cllaque.compositems.service.ClienteService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping("/obtenerCliente/{dni}")
    public Mono<Cliente> obtenerCliente(@PathVariable String dni){
        return this.clienteService.obtenerCliente(dni);
    }

    @PostMapping("/crearCliente")
    public Mono<Void> crearCliente(@RequestBody CrearClienteReq req){
        return this.clienteService.crearCliente(req);
    }


    
}
