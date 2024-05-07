package com.cllaque.cuentams.Service;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cllaque.cuentams.DTO.Cuenta.CrearCuentaReq;

@Configuration
public class CuentaMessageProcessor {
    private final CuentaService cuentaService;

    public CuentaMessageProcessor(CuentaService cuentaService){
        this.cuentaService = cuentaService;
    }

    @Bean
    public Consumer<CrearCuentaReq> crearCuentaMessageProcessor(){
        return crearCuentaReq->{
            this.cuentaService.crearCuenta(crearCuentaReq).block();
        };
    }
}

