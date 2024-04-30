CREATE TABLE IF NOT EXISTS cuenta (
	estado bool NOT NULL,
	saldo_inicial float8 NOT NULL,
	cuenta_id uuid NOT NULL,
	dni varchar(255) NOT NULL,
	tipo_cuenta varchar(255) NOT NULL,
	CONSTRAINT cuenta_pkey PRIMARY KEY (cuenta_id)
);
