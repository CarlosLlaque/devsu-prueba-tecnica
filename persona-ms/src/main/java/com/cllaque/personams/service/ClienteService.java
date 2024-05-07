package com.cllaque.personams.service;

import com.cllaque.personams.dto.ClienteResp;
import com.cllaque.personams.dto.CrearClienteReq;

import reactor.core.publisher.Mono;

public interface ClienteService {
    public Mono<ClienteResp> crearCliente(CrearClienteReq req);
    public Mono<Void> actualizarCliente(CrearClienteReq req);
    public Mono<ClienteResp> obtenerCliente(String dni);
    public Mono<Void> eliminarCliente(String dni);
}
