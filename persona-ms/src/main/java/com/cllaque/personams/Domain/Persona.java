package com.cllaque.personams.Domain;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
@Data
@MappedSuperclass
public class Persona {
    @Id
    private String dni;
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;
}
