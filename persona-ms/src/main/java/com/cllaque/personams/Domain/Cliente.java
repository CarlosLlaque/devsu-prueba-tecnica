package com.cllaque.personams.Domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente extends Persona implements Persistable<String>{
    private String clienteId;
    private String contrasena;
    private Boolean estado;
    @Transient
    private boolean isNew = true;
    @Override
    public String getId() {
        return this.getDni();
    }
    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
