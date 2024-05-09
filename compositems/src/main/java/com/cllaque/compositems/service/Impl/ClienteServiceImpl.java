package com.cllaque.compositems.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cllaque.compositems.domain.Cliente;
import com.cllaque.compositems.dto.CrearClienteReq;
import com.cllaque.compositems.service.ClienteService;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Value("${eurekaurl.personams}")
    private String personaUrl;
    private final WebClient.Builder webClientBuilder;
    private final StreamBridge streamBridge;


    public ClienteServiceImpl(WebClient.Builder builder, StreamBridge streamBridge){
        this.webClientBuilder = builder;
        this.streamBridge = streamBridge;
    }

    @TimeLimiter(name = "cliente")
    @CircuitBreaker(name = "cliente", fallbackMethod = "obtenerClienteFallback")
    @Override
    public Mono<Cliente> obtenerCliente(String dni, int delaySeconds, int faultPercent) {
        return this.webClientBuilder.baseUrl(personaUrl).build()
            .get()
            .uri(uriBuilder->uriBuilder
                .path("/clientes/" + dni)
                .queryParam("delay", delaySeconds)
                .queryParam("faultPercent", faultPercent)
                .build()
            )
            .retrieve()
            .bodyToMono(Cliente.class);
    }

    @Override
    public void crearCliente(CrearClienteReq req) {
        this.streamBridge.send("clientes-out-0", req);
    }
    
    public Mono<Cliente> obtenerClienteFallback(String dni, int delay, int faultPercent, CallNotPermittedException e){
        var cliente = new Cliente();
        cliente.setDni("00000000");
        cliente.setNombre("foo");
        cliente.setGenero("foo");
        cliente.setEdad(0);
        cliente.setDireccion("foo");
        cliente.setTelefono("000000000");

        return Mono.just(cliente);
    }
}
