package com.cllaque.cuentams.Domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.Data;
import reactor.core.publisher.Flux;

@Data
public class Cuenta implements Persistable{
    @Id
    private UUID cuentaId;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;

    private String dni;
    @Transient
    private Flux<Movimiento> movimientos;
    @Override
    public Object getId() {
        return this.cuentaId;
    }
    @Override
    public boolean isNew() {
        return true;
    }
}
