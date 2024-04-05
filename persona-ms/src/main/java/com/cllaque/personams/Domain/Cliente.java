package com.cllaque.personams.Domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(schema = "banco")
public class Cliente extends Persona{
    @Column(unique = true)
    private String clienteId;
    private String contrasena;
    private Boolean estado;
}
