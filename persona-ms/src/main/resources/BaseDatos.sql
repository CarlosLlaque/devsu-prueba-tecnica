CREATE TABLE banco.cliente (
	edad int4 NULL,
	estado bool NOT NULL,
	cliente_id varchar(255) NOT NULL,
	contrasena varchar(255) NOT NULL,
	direccion varchar(255) NULL,
	dni varchar(255) NOT NULL,
	genero varchar(255) NOT NULL,
	nombre varchar(255) NOT NULL,
	telefono varchar(255) NOT NULL,
	CONSTRAINT cliente_cliente_id_key UNIQUE (cliente_id),
	CONSTRAINT cliente_pkey PRIMARY KEY (dni)
);

CREATE TABLE banco.cuenta (
	estado bool NOT NULL,
	saldo_inicial float8 NOT NULL,
	cuenta_id uuid NOT NULL,
	dni varchar(255) NOT NULL,
	tipo_cuenta varchar(255) NOT NULL,
	CONSTRAINT cuenta_pkey PRIMARY KEY (cuenta_id)
);


-- banco.cuenta foreign keys

ALTER TABLE banco.cuenta ADD CONSTRAINT fk_cuenta_cliente FOREIGN KEY (dni) REFERENCES banco.cliente(dni);

CREATE TABLE banco.movimiento (
	saldo float8 NOT NULL,
	valor float8 NOT NULL,
	fecha timestamp(6) NOT NULL,
	cuenta_id uuid NOT NULL,
	movimiento_id uuid NOT NULL,
	tipo_movimiento varchar(255) NOT NULL,
	CONSTRAINT movimiento_pkey PRIMARY KEY (movimiento_id)
);


-- banco.movimientos foreign keys

ALTER TABLE banco.movimientos ADD CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES banco.cuenta(cuenta_id);
