package com.cllaque.compositems.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
    private String dni;
    private String nombre;
    private String genero;
    private Integer edad;
    private String direccion;
    private String telefono;
}
