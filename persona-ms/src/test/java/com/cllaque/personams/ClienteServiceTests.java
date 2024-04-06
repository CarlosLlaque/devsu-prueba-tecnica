package com.cllaque.personams;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cllaque.personams.DTO.CrearClienteReq;
import com.cllaque.personams.Domain.Cliente;
import com.cllaque.personams.Repository.ClienteRepository;
import com.cllaque.personams.Service.ClienteService;
import com.cllaque.personams.Service.Impl.ClienteServiceImpl;

import reactor.test.StepVerifier;

@SpringBootTest
public class ClienteServiceTests {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder encoder;

    @Test
    void crearCliente_ClienteNoExiste_RegistroExitoso() {
        MockitoAnnotations.openMocks(this);

        when(clienteRepository.findById("12312312")).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation ->invocation.getArgument(0));

        when(encoder.encode("contrasena")).thenReturn("$2a$10$bBpDm3zM0t3Q/6M9PVoqZOlvWxS6NfR9YcKJ2G2vVLp55rAafE.lC");

        ClienteService clienteService = new ClienteServiceImpl(clienteRepository, encoder);

        CrearClienteReq req = new CrearClienteReq();
        req.setDni("12312312");
        req.setContrasena("contrasena");
        req.setEdad(30);
        req.setGenero("Masculino");
        req.setNombre("Nombre prueba");
        req.setTelefono("999999999");
        req.setDireccion("Direccion");

        clienteService.crearCliente(req)
                .as(StepVerifier::create)
                .expectNextMatches(clienteResp -> {
                    return clienteResp.getDni().equals("12312312") &&
                            clienteResp.getNombre().equals("Nombre prueba") &&
                            clienteResp.getEdad().equals(30) &&
                            clienteResp.getGenero().equals("Masculino") &&
                            clienteResp.getTelefono().equals("999999999") &&
                            clienteResp.getDireccion().equals("Direccion");
                })
                .verifyComplete();
    }
}
