package com.cllaque.personams.it;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.cllaque.personams.configuration.TestDBConfig;
import com.cllaque.personams.domain.Cliente;
import com.cllaque.personams.repository.ClienteRepository;

import reactor.test.StepVerifier;

@DataR2dbcTest
@Testcontainers
@Import(TestDBConfig.class)
public class ClienteRepositoryIT {
    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setClientes(){
        var cliente = new Cliente();
        cliente.setDni("888000");
        cliente.setContrasena("123");
        cliente.setClienteId(UUID.randomUUID().toString());
        cliente.setEdad(20);
        cliente.setEstado(true);
        cliente.setGenero("M");
        cliente.setNombre("NombreBeforeEach");
        cliente.setTelefono("999999999");
        cliente.setDireccion("Direccion");

        this.clienteRepository.deleteAll().then(this.clienteRepository.save(cliente)).block();
    }

    @Test
    public void crearCliente_aun_no_existe(){
        var cliente = new Cliente();
        cliente.setDni("999");
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
                return s.getDni().equals("999");
            })
            .verifyComplete();
    }

    @Test
    public void crearCliente_existe(){
        var cliente = new Cliente();
        cliente.setDni("888000");
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
            .expectError()
            .verify();
    }

    @Test
    public void obtenerCliente_existe(){
        var cliente = clienteRepository.findById("888000");

        StepVerifier.create(cliente)
            .expectNextCount(1)
            .verifyComplete();
    }
}
