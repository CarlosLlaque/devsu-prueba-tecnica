CREATE TABLE IF NOT EXISTS movimiento (
	saldo float8 NOT NULL,
	valor float8 NOT NULL,
	fecha timestamp(6) NOT NULL,
	cuenta_id uuid NOT NULL,
	movimiento_id uuid NOT NULL,
	tipo_movimiento varchar(255) NOT NULL,
	CONSTRAINT movimiento_pkey PRIMARY KEY (movimiento_id)
);


-- banco.movimiento foreign keys

