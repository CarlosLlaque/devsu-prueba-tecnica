package com.cllaque.cuentams.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Persona {
    private String dni;
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;
}
