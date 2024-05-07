package com.cllaque.personams.dto;


import com.cllaque.personams.domain.Persona;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResp extends Persona{
    private String clienteId;
    private boolean estado;
}
