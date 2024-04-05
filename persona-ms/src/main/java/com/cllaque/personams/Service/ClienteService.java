package com.cllaque.personams.Service;

import com.cllaque.personams.DTO.ClienteResp;
import com.cllaque.personams.DTO.CrearClienteReq;
import com.cllaque.personams.Domain.Cliente;

import reactor.core.publisher.Mono;

public interface ClienteService {
    public Mono<ClienteResp> crearCliente(CrearClienteReq req);
    public Mono<Void> actualizarCliente(CrearClienteReq req);
    public Mono<ClienteResp> obtenerCliente(String dni);
    public Mono<Void> eliminarCliente(String dni);
}
