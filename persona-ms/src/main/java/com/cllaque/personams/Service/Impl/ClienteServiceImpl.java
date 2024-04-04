package com.cllaque.personams.Service.Impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cllaque.personams.DTO.CrearClienteReq;
import com.cllaque.personams.Domain.Cliente;
import com.cllaque.personams.Excepcion.ConflictException;
import com.cllaque.personams.Excepcion.NotFoundException;
import com.cllaque.personams.Repository.ClienteRepository;
import com.cllaque.personams.Service.ClienteService;

import jakarta.persistence.EntityExistsException;

@Service
public class ClienteServiceImpl implements ClienteService{

    private ClienteRepository clienteRepository;
    private PasswordEncoder encoder;

    public ClienteServiceImpl(ClienteRepository clienteRepository, PasswordEncoder encoder){
        this.clienteRepository = clienteRepository;
        this.encoder = encoder;
    }

    @Override
    public Cliente crearCliente(CrearClienteReq req) {
        var clienteOptional = this.clienteRepository.findById(req.getDni());
        clienteOptional.ifPresent(cliente->{
                throw new ConflictException("El cliente ya esta registrado");
            }
        );


        var cliente = new Cliente();
        cliente.setDni(req.getDni());
        cliente.setContrasena(encoder.encode(req.getContrasena()));
        cliente.setClienteId(UUID.randomUUID().toString());
        cliente.setEdad(req.getEdad());
        cliente.setEstado(true);
        cliente.setGenero(req.getGenero());
        cliente.setNombre(req.getNombre());
        cliente.setTelefono(req.getTelefono());
        cliente.setDireccion(req.getDireccion());

        var response = this.clienteRepository.save(cliente);
        return response;
    }

    @Override
    public Cliente actualizarCliente(CrearClienteReq req) {
        var cliente = new Cliente();
        cliente.setDni(req.getDni());
        cliente.setContrasena(encoder.encode(req.getContrasena()));
        cliente.setEdad(req.getEdad());
        cliente.setEstado(true);
        cliente.setGenero(req.getGenero());
        cliente.setNombre(req.getNombre());
        cliente.setTelefono(req.getTelefono());
        cliente.setDireccion(req.getDireccion());

        var response = this.clienteRepository.save(cliente);
        return response;
    }

    @Override
    public Cliente obtenerCliente(String dni) {
        var clienteOptional = this.clienteRepository.findById(dni);

        var cliente = clienteOptional.orElseThrow(()->new NotFoundException("El cliente no existe"));

        return cliente;
    }

    @Override
    public void eliminarCliente(String dni) {
        this.clienteRepository.deleteById(dni);
    }
    
}
