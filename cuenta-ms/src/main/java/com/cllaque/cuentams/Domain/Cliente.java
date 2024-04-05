package com.cllaque.cuentams.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente extends Persona{
    private String clienteId;
    private String contrasena;
    private boolean estado;
}
