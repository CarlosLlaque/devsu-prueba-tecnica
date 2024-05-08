package com.cllaque.compositems.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cllaque.compositems.dto.CrearCuentaReq;
import com.cllaque.compositems.dto.CuentaResp;
import com.cllaque.compositems.service.CuentaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CuentaServiceImpl implements CuentaService{

    private final WebClient.Builder webClientBuilder;
    @Value("${eurekaurl.cuentams}")
    private String cuentaUrl;
    private final StreamBridge streamBridge;

    public CuentaServiceImpl(WebClient.Builder builder, StreamBridge streamBridge){
        this.webClientBuilder = builder;
        this.streamBridge = streamBridge;
    }


    @Override
    public Flux<CuentaResp> obtenerCuentasPorDni(String dni) {
        return this.webClientBuilder.baseUrl(cuentaUrl).build()
            .get()
            .uri("/cuentas/cuentasPorDni/" + dni)
            .retrieve()
            .bodyToFlux(CuentaResp.class);
    }

    @Override
    public void crearCuenta(CrearCuentaReq req) {
        this.streamBridge.send("cuentas-out-0", req);
    }
    
}
