package com.cllaque.personams.DTO;

import com.cllaque.personams.Domain.Persona;

import lombok.Data;

@Data
public class ClienteResp extends Persona{
    private String clienteId;
    private boolean estado;
}
