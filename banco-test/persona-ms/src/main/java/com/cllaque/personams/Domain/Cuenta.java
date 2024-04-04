package com.cllaque.personams.Domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "banco")
public class Cuenta {
    @Id
    private String cuentaId;
    private String tipoCuenta;
    private Double saldoInicial;
    private boolean estado;

    private String dni;
    @OneToMany
    @JoinColumn(name = "cuentaId")
    private List<Movimientos> movimientos;
}
