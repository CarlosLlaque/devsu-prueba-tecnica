package com.cllaque.personams.ut;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.cllaque.personams.dto.CrearClienteReq;
import com.cllaque.personams.domain.Cliente;
import com.cllaque.personams.repository.ClienteRepository;
import com.cllaque.personams.service.ClienteService;
import com.cllaque.personams.service.Impl.ClienteServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void crearCliente_ClienteNoExiste_RegistroExitoso() {
        when(clienteRepository.existsById("12312312")).thenReturn(Mono.just(false));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation ->Mono.just(invocation.getArgument(0)));

        when(encoder.encode("contrasena")).thenReturn("$2a$10$bBpDm3zM0t3Q/6M9PVoqZOlvWxS6NfR9YcKJ2G2vVLp55rAafE.lC");

        CrearClienteReq req = new CrearClienteReq();
        req.setDni("12312312");
        req.setContrasena("contrasena");
        req.setEdad(30);
        req.setGenero("Masculino");
        req.setNombre("Nombre prueba");
        req.setTelefono("999999999");
        req.setDireccion("Direccion");

        var cliente = clienteService.crearCliente(req);
        StepVerifier.create(cliente)
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

    @Test
    void crearCliente_ClienteExiste(){
        when(clienteRepository.existsById("12312312")).thenReturn(Mono.just(true));

        CrearClienteReq req = new CrearClienteReq();
        req.setDni("12312312");
        req.setContrasena("contrasena");
        req.setEdad(30);
        req.setGenero("Masculino");
        req.setNombre("Nombre prueba");
        req.setTelefono("999999999");
        req.setDireccion("Direccion");

        var cliente = clienteService.crearCliente(req);
        StepVerifier.create(cliente)
            .verifyErrorMessage("El cliente ya esta registrado");
    }
}
