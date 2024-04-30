package com.cllaque.personams.Config;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cllaque.personams.DTO.CrearClienteReq;
import com.cllaque.personams.Service.ClienteService;

@Configuration
public class MessageProcessorConfig {
    private final ClienteService clienteService;

    public MessageProcessorConfig(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @Bean
    public Consumer<CrearClienteReq> crearClienteMessageProcessor(){
        return crearClienteReq->{
            System.out.println(crearClienteReq.getDni());
            this.clienteService.crearCliente(crearClienteReq).block();
        };
    }
}

