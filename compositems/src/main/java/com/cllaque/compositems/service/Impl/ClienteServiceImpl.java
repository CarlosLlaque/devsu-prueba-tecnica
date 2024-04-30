package com.cllaque.compositems.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cllaque.compositems.domain.Cliente;
import com.cllaque.compositems.dto.CrearClienteReq;
import com.cllaque.compositems.service.ClienteService;

import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Value("${ms.url.persona}")
    private String personaUrl;
    private final WebClient.Builder webClientBuilder;
    private final StreamBridge streamBridge;


    public ClienteServiceImpl(WebClient.Builder builder, StreamBridge streamBridge){
        this.webClientBuilder = builder;
        this.streamBridge = streamBridge;
    }

    @Override
    public Mono<Cliente> obtenerCliente(String dni) {
        return this.webClientBuilder.baseUrl(personaUrl).build()
            .get()
            .uri("/clientes/" + dni)
            .retrieve()
            .bodyToMono(Cliente.class);
    }

    @Override
    public Mono<Void> crearCliente(CrearClienteReq req) {
        this.streamBridge.send("clientes-out-0", req);

        return Mono.empty();
    }
    
}
