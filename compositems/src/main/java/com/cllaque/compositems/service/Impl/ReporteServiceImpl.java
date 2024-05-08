package com.cllaque.compositems.service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cllaque.compositems.dto.CuentaResp;
import com.cllaque.compositems.dto.EstadoDeCuentaResp;
import com.cllaque.compositems.domain.Movimiento;
import com.cllaque.compositems.service.ReporteService;

import reactor.core.publisher.Flux;

@Service
public class ReporteServiceImpl implements ReporteService{
    @Autowired
    private final WebClient.Builder builder;

    @Value("${eurekaurl.cuentams}")
    private String cuentaUrl;

    @Value("${eurekaurl.movimientoms}")
    private String movimientoUrl;

    public ReporteServiceImpl(WebClient.Builder builder){
        this.builder = builder;
    }

    @Override
    public Flux<EstadoDeCuentaResp> obtenerEstadoDeCuenta(String dni, LocalDate fechaInicio, LocalDate fechaFin) {
        return this.builder.baseUrl(cuentaUrl).build()
            .get()
            .uri("/cuentas/cuentasPorDni/" + dni)
            .retrieve()
            .bodyToFlux(CuentaResp.class)
            .flatMap(cuenta->{
                var estadoCuenta = new EstadoDeCuentaResp();
                estadoCuenta.setDni(cuenta.getDni());
                estadoCuenta.setEstado(cuenta.getEstado());
                estadoCuenta.setCuentaId(cuenta.getCuentaId());
                estadoCuenta.setTipoCuenta(cuenta.getTipoCuenta());
                estadoCuenta.setSaldoActual(cuenta.getSaldoActual());
                estadoCuenta.setSaldoInicial(cuenta.getSaldoInicial());
                estadoCuenta.setMovimientos(new ArrayList<Movimiento>());

                return this.builder.baseUrl(movimientoUrl).build()
                    .get()
                    .uri(uriBuilder->uriBuilder
                        .path("/movimientos/porCuenta/" + cuenta.getCuentaId())
                        .queryParam("page", 0)
                        .queryParam("numberOfItems", 10)
                        .build()
                    )
                    .retrieve()
                    .bodyToFlux(Movimiento.class)
                    .onErrorResume(e->Flux.empty())
                    .collectList()
                    .map(movimientos->{
                        estadoCuenta.setMovimientos(movimientos);
                        return estadoCuenta;
                    });

            });
    }
}
