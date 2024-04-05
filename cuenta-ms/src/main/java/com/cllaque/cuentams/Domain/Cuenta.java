package com.cllaque.cuentams.Domain;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "banco")
public class Cuenta {
    @Id
    @GeneratedValue
    private UUID cuentaId;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;

    @JoinColumn(name = "dni")
    private String dni;
    @OneToMany
    @JoinColumn(name = "cuentaId")
    private List<Movimiento> movimientos;
}
