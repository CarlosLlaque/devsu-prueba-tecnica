package com.cllaque.personams.dto;

import lombok.Data;

@Data
public class CrearClienteReq {
    private Integer edad;
    private String contrasena;
    private String direccion;
    private String dni;
    private String genero;
    private String nombre;
    private String telefono;
    private Boolean estado;
}
