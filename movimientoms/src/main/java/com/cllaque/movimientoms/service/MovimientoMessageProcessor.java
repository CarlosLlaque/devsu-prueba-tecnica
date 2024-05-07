package com.cllaque.movimientoms.service;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cllaque.movimientoms.dto.movimiento.CrearMovimientoReq;

@Configuration
public class MovimientoMessageProcessor {
    private final MovimientoService movimientoService;

    public MovimientoMessageProcessor(MovimientoService movimientoService){
        this.movimientoService = movimientoService;
    }

    @Bean
    public Consumer<CrearMovimientoReq> crearMovimientoMessageProcessor(){
        return crearMovimientoReq->{
            this.movimientoService.crearMovimiento(crearMovimientoReq).block();
        };
    }
}

