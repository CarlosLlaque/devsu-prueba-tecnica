package com.cllaque.personams.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cllaque.personams.Domain.Cliente;

import jakarta.transaction.Transactional;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, String>{
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
    void actualizarCliente(String dni, Integer edad, Boolean estado, String contrasena,
                           String direccion, String telefono, String nombre, String genero);
}
