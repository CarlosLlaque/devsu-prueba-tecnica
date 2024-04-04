package com.cllaque.personams.Domain;


import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "banco")
public class Cliente extends Persona{
    @Column(unique = true)
    private String clienteId;
    private String contrasena;
    private boolean estado;

    @OneToMany
    @JoinColumn(name="dni")
    private List<Cuenta> cuentas;
}
