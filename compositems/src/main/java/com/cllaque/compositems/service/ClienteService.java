package com.cllaque.compositems.service;

import com.cllaque.compositems.domain.Cliente;
import com.cllaque.compositems.dto.CrearClienteReq;

import reactor.core.publisher.Mono;

public interface ClienteService {
    Mono<Cliente> obtenerCliente(String dni, int delaySeconds, int faultPercent);
    void crearCliente(CrearClienteReq req);
}
