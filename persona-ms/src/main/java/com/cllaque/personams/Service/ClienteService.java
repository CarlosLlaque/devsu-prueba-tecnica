package com.cllaque.personams.Service;

import com.cllaque.personams.DTO.CrearClienteReq;
import com.cllaque.personams.Domain.Cliente;

public interface ClienteService {
    public Cliente crearCliente(CrearClienteReq req);
    public Cliente actualizarCliente(CrearClienteReq req);
    public Cliente obtenerCliente(String dni);
    public void eliminarCliente(String dni);
}
