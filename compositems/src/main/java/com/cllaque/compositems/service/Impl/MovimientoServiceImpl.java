package com.cllaque.compositems.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cllaque.compositems.domain.Movimiento;
import com.cllaque.compositems.dto.CrearMovimientoReq;
import com.cllaque.compositems.service.MovimientoService;

import reactor.core.publisher.Flux;

@Service
public class MovimientoServiceImpl implements MovimientoService{
    private final WebClient.Builder builder;
    private final StreamBridge streamBridge;
    @Value("${ms.url.movimiento}")
    private String movimientoUrl;

    public MovimientoServiceImpl(WebClient.Builder builder, StreamBridge streamBridge){
        this.builder = builder;
        this.streamBridge = streamBridge;
    }

    @Override
    public Flux<Movimiento> obtenerMovimientosPorCuenta(String cuentaId, Integer page, Integer numberOfItems) {
        return this.builder.baseUrl(movimientoUrl).build()
            .get()
            .uri(uriBuilder->{
                return uriBuilder.path("/movimientos/porCuenta/"+cuentaId)
                    .queryParam("page", page)
                    .queryParam("numberOfItems", numberOfItems)
                    .build();
            })
            .retrieve()
            .bodyToFlux(Movimiento.class);
    }

    @Override
    public void crearMovimiento(CrearMovimientoReq req) {
        this.streamBridge.send("movimientos-out-0", req);
    }
    
}
