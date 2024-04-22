package com.cllaque.personams;

import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cllaque.personams.Domain.Cliente;
import com.cllaque.personams.Repository.ClienteRepository;

import reactor.test.StepVerifier;

@DataR2dbcTest
@ExtendWith(SpringExtension.class)
// @Import(DBConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @Order(1)
    public void crearCliente_aun_no_existe(){
        var cliente = new Cliente();
        cliente.setDni("888");
        cliente.setContrasena("123");
        cliente.setClienteId(UUID.randomUUID().toString());
        cliente.setEdad(20);
        cliente.setEstado(true);
        cliente.setGenero("M");
        cliente.setNombre("Nombre");
        cliente.setTelefono("999999999");
        cliente.setDireccion("Direccion");

        var saved = clienteRepository.save(cliente);

        StepVerifier.create(saved)
            .expectNextMatches(s->{
                return s.getDni().equals("888");
            })
            .verifyComplete();
    }

    @Test
    @Order(2)
    public void obtenerCliente_existe(){
        // var cliente = clienteRepository.deleteAll().thenMany(clienteRepository.findById("12312312"));
        var cliente = clienteRepository.findById("888");

        StepVerifier.create(cliente)
            .expectNextCount(1)
            // .expectNextMatches(s->{
            //     return s.getDni().equals("12312312") &&
            //     s.getNombre().equals("Nombre");
            // })
            .verifyComplete();
    }
}
