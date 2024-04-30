
CREATE TABLE IF NOT EXISTS CLIENTE (
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

