package com.cllaque.personams.configuration;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cllaque.personams.dto.CrearClienteReq;
import com.cllaque.personams.service.ClienteService;

@Configuration
public class MessageProcessorConfig {
    private final ClienteService clienteService;

    public MessageProcessorConfig(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @Bean
    public Consumer<CrearClienteReq> crearClienteMessageProcessor(){
        System.out.println("From message processor");
        return crearClienteReq->{
            this.clienteService.crearCliente(crearClienteReq).block();
        };
    }
}

