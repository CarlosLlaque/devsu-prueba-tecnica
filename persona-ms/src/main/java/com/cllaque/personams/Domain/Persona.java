package com.cllaque.personams.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;

import lombok.Builder;
import lombok.Data;

@Data
public class Persona {
    @Id
    private String dni;
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;
}
