package com.cllaque.personams.ut;

import static org.mockito.Mockito.when;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.cllaque.personams.configuration.MessageProcessorConfig;
import com.cllaque.personams.dto.ClienteResp;
import com.cllaque.personams.dto.CrearClienteReq;
import com.cllaque.personams.exception.ConflictException;
import com.cllaque.personams.service.ClienteService;

import reactor.core.publisher.Mono;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MessageProcessorTest {
    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private MessageProcessorConfig processorConfig;

    @Test
    public void crearCliente_no_existe(){
        when(this.clienteService.crearCliente(any(CrearClienteReq.class))).thenReturn(Mono.just(new ClienteResp()));

        this.sendCrearClienteMessage();

    }

    @Test
    public void crearCliente_existe(){
        when(this.clienteService.crearCliente(any(CrearClienteReq.class))).thenReturn(Mono.error(new ConflictException("El cliente ya esta registrado")));

        assertThrows(ConflictException.class, ()->this.sendCrearClienteMessage());
    }

    private void sendCrearClienteMessage(){
        var messageProcessor = this.processorConfig.crearClienteMessageProcessor();

        var req = new CrearClienteReq();
        req.setDni("74061955");
        req.setEdad(12);
        req.setEstado(true);
        req.setGenero("M");
        req.setNombre("MiNombre");
        req.setTelefono("999123999");
        req.setDireccion("Mi direccion");
        req.setContrasena("micontra");

        messageProcessor.accept(req);
    }
    
}
