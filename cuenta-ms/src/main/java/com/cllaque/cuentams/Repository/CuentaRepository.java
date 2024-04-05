package com.cllaque.cuentams.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cllaque.cuentams.Domain.Cuenta;

import jakarta.transaction.Transactional;

public interface CuentaRepository extends CrudRepository<Cuenta, UUID> {
    List<Cuenta> findByDni(String dni);

    @Transactional
    @Modifying
    @Query("UPDATE Cuenta c " +
            "SET c.estado = COALESCE(:estado, c.estado), " +
            "c.tipoCuenta = COALESCE(:tipoCuenta, c.tipoCuenta) " +
            "WHERE c.cuentaId = :cuentaId")
    void patchCuenta(UUID cuentaId, Boolean estado, String tipoCuenta);
}
