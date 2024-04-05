package com.cllaque.cuentams.Service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.cllaque.cuentams.DTO.Reporte.EstadoDeCuentaResp;
import com.cllaque.cuentams.Domain.Movimiento;
import com.cllaque.cuentams.Service.CuentaService;
import com.cllaque.cuentams.Service.MovimientoService;
import com.cllaque.cuentams.Service.ReporteService;

import reactor.core.publisher.Flux;

@Service
public class ReporteServiceImpl implements ReporteService{
    private CuentaService cuentaService;
    private MovimientoService movimientoService;

    public ReporteServiceImpl(CuentaService cuentaService, MovimientoService movimientoService){
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
    }

    @Override
    public Flux<EstadoDeCuentaResp> obtenerEstadoDeCuenta(String dni, LocalDate fechaInicio, LocalDate fechaFin) {
        return this.cuentaService.obtenerCuentasPorDni(dni)
        .map(cuenta->{
            var estadoCuenta = new EstadoDeCuentaResp();
            estadoCuenta.setDni(cuenta.getDni());
            estadoCuenta.setEstado(cuenta.getEstado());
            estadoCuenta.setCuentaId(cuenta.getCuentaId());
            estadoCuenta.setTipoCuenta(cuenta.getTipoCuenta());
            estadoCuenta.setSaldoActual(cuenta.getSaldoActual());
            estadoCuenta.setSaldoInicial(cuenta.getSaldoInicial());
            estadoCuenta.setMovimientos(new ArrayList<Movimiento>());

            this.movimientoService.obtenerMovimientosPorCuentaRangoFechas(cuenta.getCuentaId(), fechaInicio, fechaFin)
                .subscribe(movimiento->{
                    estadoCuenta.getMovimientos().add(movimiento);
                });

            return estadoCuenta;
        });
    }
    
}
