package com.cllaque.cuentams.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cllaque.cuentams.Domain.Cuenta;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CuentaRepository extends ReactiveCrudRepository<Cuenta, UUID> {
    Flux<Cuenta> findByDni(String dni);

    @Transactional
    @Modifying
    @Query("UPDATE Cuenta c " +
            "SET c.estado = COALESCE(:estado, c.estado), " +
            "c.tipo_cuenta = COALESCE(:tipoCuenta, c.tipo_cuenta) " +
            "WHERE c.cuenta_id = :cuentaId")
    Mono<Void> patchCuenta(UUID cuentaId, Boolean estado, String tipoCuenta);
}
