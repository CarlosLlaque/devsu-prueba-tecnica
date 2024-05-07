package com.cllaque.personams.ut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cllaque.personams.controller.ClienteController;
import com.cllaque.personams.dto.ClienteResp;
import com.cllaque.personams.dto.CrearClienteReq;
import com.cllaque.personams.exception.NotFoundException;
import com.cllaque.personams.service.ClienteService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClienteController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class ClienteControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ClienteService clienteService;


    @Test
    public void obtenerCliente_OK(){
        var clienteResp = new ClienteResp();
        clienteResp.setDni("12312312");
        clienteResp.setEdad(12);
        clienteResp.setEstado(true);
        clienteResp.setGenero("M");
        clienteResp.setNombre("Nombre");
        clienteResp.setTelefono("999999999");
        clienteResp.setClienteId("asdfasdf-asdfasdf");
        clienteResp.setDireccion("Direccion");

        when(clienteService.obtenerCliente("12312312")).thenReturn(Mono.just(clienteResp));

        webClient.get()
            .uri("/clientes/12312312")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(ClienteResp.class);
    }

    @Test
    public void obtenerCliente_NOT_FOUND(){
        when(clienteService.obtenerCliente("12312312")).thenReturn(Mono.error(new NotFoundException("El cliente no existe")));

        webClient.get()
            .uri("/clientes/12312312")
            .exchange()
            .expectStatus()
            .isNotFound();
    }
    
    @Test
    public void crearCliente_OK(){
        var clienteResp = new ClienteResp();
        clienteResp.setDni("12312312");
        clienteResp.setEdad(12);
        clienteResp.setEstado(true);
        clienteResp.setGenero("M");
        clienteResp.setNombre("Nombre");
        clienteResp.setTelefono("999999999");
        clienteResp.setClienteId("asdfasdf-asdfasdf");
        clienteResp.setDireccion("Direccion");

        var clienteReq = new CrearClienteReq();
        clienteReq.setEdad(12);
        clienteReq.setContrasena("123");
        clienteReq.setDireccion("Direccion");
        clienteReq.setDni("12312312");
        clienteReq.setGenero("M");
        clienteReq.setNombre("Nombre");
        clienteReq.setTelefono("999999999");
        clienteReq.setEstado(true);

        when(clienteService.crearCliente(clienteReq)).thenReturn(Mono.just(clienteResp));

        webClient.post()
            .uri("/clientes/crear")
            .body(Mono.just(clienteReq), CrearClienteReq.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ClienteResp.class)
            .value(c->{
                assertEquals("12312312", c.getDni());
            });
    }
}
