package com.cllaque.personams.DTO;

import com.cllaque.personams.Domain.Persona;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResp extends Persona{
    private String clienteId;
    private boolean estado;
}
