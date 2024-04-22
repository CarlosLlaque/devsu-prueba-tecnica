package com.cllaque.personams.Repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cllaque.personams.Domain.Cliente;

import reactor.core.publisher.Mono;

@Repository
public interface ClienteRepository extends ReactiveCrudRepository<Cliente, String>{
    @Transactional
    @Modifying
    @Query("UPDATE Cliente c SET " +
           "c.edad = COALESCE(:edad, c.edad), " +
           "c.estado = COALESCE(:estado, c.estado), " +
           "c.contrasena = COALESCE(:contrasena, c.contrasena), " +
           "c.direccion = COALESCE(:direccion, c.direccion), " +
           "c.telefono = COALESCE(:telefono, c.telefono), " +
           "c.nombre = COALESCE(:nombre, c.nombre), " +
           "c.genero = COALESCE(:genero, c.genero) " +
           "WHERE c.dni = :dni")
    Mono<Void> actualizarCliente(String dni, Integer edad, Boolean estado, String contrasena,
                           String direccion, String telefono, String nombre, String genero);
}
