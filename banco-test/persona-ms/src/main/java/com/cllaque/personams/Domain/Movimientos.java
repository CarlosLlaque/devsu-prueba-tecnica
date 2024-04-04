package com.cllaque.personams.Domain;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(schema = "banco")
public class Movimientos {
    @Id
    private String movimientoId;

    private Date fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
    
    private String cuentaId;
}
