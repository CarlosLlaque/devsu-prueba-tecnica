package com.cllaque.personams.service.Impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cllaque.personams.dto.ClienteResp;
import com.cllaque.personams.dto.CrearClienteReq;
import com.cllaque.personams.domain.Cliente;
import com.cllaque.personams.exception.ConflictException;
import com.cllaque.personams.exception.GenericException;
import com.cllaque.personams.exception.NotFoundException;
import com.cllaque.personams.repository.ClienteRepository;
import com.cllaque.personams.service.ClienteService;

import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService{

    private ClienteRepository clienteRepository;
    private PasswordEncoder encoder;

    public ClienteServiceImpl(ClienteRepository clienteRepository, PasswordEncoder encoder){
        this.clienteRepository = clienteRepository;
        this.encoder = encoder;
    }

    @Override
    public Mono<ClienteResp> crearCliente(CrearClienteReq req) {
        return this.clienteRepository.existsById(req.getDni())
        .flatMap(exists->{
            if(exists){
                return Mono.error(new ConflictException("El cliente ya esta registrado"));
            }else{
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

                return this.clienteRepository.save(cliente);
            }
        })
        .map(clienteGuardado->{
            var clienteResp = new ClienteResp();
            clienteResp.setDni(clienteGuardado.getDni());
            clienteResp.setDireccion(clienteGuardado.getDireccion());
            clienteResp.setTelefono(clienteGuardado.getTelefono());
            clienteResp.setNombre(clienteGuardado.getNombre());
            clienteResp.setEdad(clienteGuardado.getEdad());
            clienteResp.setEstado(clienteGuardado.getEstado());
            clienteResp.setGenero(clienteGuardado.getGenero());
            clienteResp.setClienteId(clienteGuardado.getClienteId());

            return clienteResp;
        });
    }

    @Override
    public Mono<Void> actualizarCliente(CrearClienteReq req) {
        return this.clienteRepository.existsById(req.getDni())
        .flatMap(exists->{
            if(!exists){
                return Mono.error(new NotFoundException("El cliente no existe"));
            }else{
                var contrasenaEncriptada = req.getContrasena() != null? encoder.encode(req.getContrasena()) : null;

                return this.clienteRepository.actualizarCliente(req.getDni(), req.getEdad(), 
                    req.getEstado(), contrasenaEncriptada, req.getDireccion(), req.getTelefono(), req.getNombre(), 
                    req.getGenero())
                .onErrorResume(error -> {
                    error.printStackTrace();
                    return Mono.error(new GenericException("No se pudo eliminar al cliente", error));
                });
            }
        });
    }

    @Override
    public Mono<ClienteResp> obtenerCliente(String dni) {
        return this.clienteRepository.findById(dni)
        .switchIfEmpty(Mono.error(new NotFoundException("El cliente no existe")))
        .map(cliente->{
            var clienteResp = new ClienteResp();
            clienteResp.setDni(cliente.getDni());
            clienteResp.setDireccion(cliente.getDireccion());
            clienteResp.setTelefono(cliente.getTelefono());
            clienteResp.setNombre(cliente.getNombre());
            clienteResp.setEdad(cliente.getEdad());
            clienteResp.setEstado(cliente.getEstado());
            clienteResp.setGenero(cliente.getGenero());
            clienteResp.setClienteId(cliente.getClienteId());

            return clienteResp;
        });
    }

    @Override
    public Mono<Void> eliminarCliente(String dni) {
        return this.clienteRepository.existsById(dni)
        .flatMap(exists -> {
            if (!exists) {
                return Mono.error(new NotFoundException("El cliente no existe"));
            } else {
                return this.clienteRepository.deleteById(dni)
                .onErrorResume(error -> {
                    return Mono.error(new GenericException("No se pudo eliminar al cliente", error));
                });
            }
        });
    }
    
}
