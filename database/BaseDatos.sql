CREATE SCHEMA IF NOT EXISTS banco;

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

ALTER TABLE banco.movimiento ADD CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES banco.cuenta(cuenta_id);

INSERT INTO banco.cliente
(edad, estado, cliente_id, contrasena, direccion, dni, genero, nombre, telefono)
VALUES(15, true, '00563204-0d0a-44b7-b30d-807861a5dd0a', '$2a$10$G09YutXHdgaZLHsF.EjQbuBXGPJpOBYK9YmBhMKz.yI926gCEOUHi', 'Av. aaa', '74011343', 'M', 'Nombre', '912912912');
INSERT INTO banco.cliente
(edad, estado, cliente_id, contrasena, direccion, dni, genero, nombre, telefono)
VALUES(15, true, '2209a15c-25c5-4ead-a1f5-53c6211d1a56', '$2a$10$bBpDm3zM0t3Q/6M9PVoqZOlvWxS6NfR9YcKJ2G2vVLp55rAafE.lC', 'Av. aaa', '74011344', 'M', 'Nombre', '912912912');
INSERT INTO banco.cliente
(edad, estado, cliente_id, contrasena, direccion, dni, genero, nombre, telefono)
VALUES(15, true, '2da8383e-e83b-4d2a-bd43-c05cefb69af5', '$2a$10$kEBpiimz/Anc4JHhEGXy6OgblB7ulptisoDXHnN7eqf69oTmAv70W', 'Av. aaa', '74011346', 'M', 'Nombre', '912912912');
INSERT INTO banco.cliente
(edad, estado, cliente_id, contrasena, direccion, dni, genero, nombre, telefono)
VALUES(22, true, '2209a15c-25c5-4ead-a1f5-53c6211d1a57', '$2a$10$xr7wu4d2nryuHDjrJ0dCmOeIJonSn5X0YAVsrzZVs1j8.hf/jpxHG', 'Av. ccc', '12312312', 'F', 'Nombre2', '91239123');

INSERT INTO banco.cuenta
(estado, saldo_inicial, cuenta_id, dni, tipo_cuenta)
VALUES(true, 123.12, '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '12312312', 'Cuenta Corriente');
INSERT INTO banco.cuenta
(estado, saldo_inicial, cuenta_id, dni, tipo_cuenta)
VALUES(true, 12.0, '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '12312312', 'Cuenta plus');

INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(23.120000000000005, -100.0, '2024-04-04 17:26:49.414', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '3308bfb1-8432-4c7c-9eae-46e55e4ab1b3'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(123.12, 100.0, '2024-04-04 17:48:37.852', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '0c46a43c-7735-4fce-b308-bafb50179ade'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(223.12, 100.0, '2024-04-04 17:48:39.206', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '40fdf7d7-13b1-4154-9048-e523c9677941'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(323.12, 100.0, '2024-04-04 17:48:40.030', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, 'bfbc4eca-7666-4022-be11-6fa8f6e0d91d'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(223.12, -100.0, '2024-04-04 17:48:44.878', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, 'a0c4b4d1-e959-44bf-8954-6fa2eb8feb8e'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(123.12, -100.0, '2024-04-04 17:48:46.250', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '5ee01ee6-cf1d-4176-9025-fb22b2502472'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(23.120000000000005, -100.0, '2024-04-04 17:48:47.585', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '6ead593d-20c2-4715-ba03-8de63ca12356'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(123.12, 100.0, '2024-04-04 17:48:54.349', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '4a90ee30-12d9-4656-b710-7fc954f233e4'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(223.12, 100.0, '2024-04-04 17:48:56.109', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, 'c91f7c3c-0804-4778-b3c2-e5e63cc0e791'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(323.12, 100.0, '2024-04-04 17:48:56.791', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, '32e4a155-f090-44e6-9090-542f4a3d9c69'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(423.12, 100.0, '2024-04-04 17:48:58.826', '01334344-b1ba-4d4e-a540-81c09ea1e291'::uuid, 'b3c34ae9-e411-4fde-a5ab-774ee218e6d0'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(512.0, 500.0, '2024-04-04 18:52:14.673', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '9a81df0a-c0f7-4aa6-b92d-74f4f4966748'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(412.0, -100.0, '2024-04-04 18:52:26.180', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '98ab5bed-c881-48ce-a23f-d39b91ef637f'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(312.0, -100.0, '2024-04-04 18:52:27.203', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '82405336-e8c6-4d5f-847b-e303088b435c'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(212.0, -100.0, '2024-04-04 18:52:27.915', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '7d9ade3a-ce9c-4229-9e98-353f3921a7fb'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(112.0, -100.0, '2024-04-04 18:52:29.045', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '823f7879-b03c-4cdd-a20e-1e0723e1850a'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(12.0, -100.0, '2024-04-04 18:52:30.047', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '73c561c0-afe0-4eaa-ade8-9b65c9dbaf6a'::uuid, 'Retiro');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(112.0, 100.0, '2024-04-04 19:04:54.097', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, 'ac730722-cf4a-493d-bf15-8364d239a8b4'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(212.0, 100.0, '2024-04-04 19:04:56.139', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '5973c8bf-ef8a-40a0-9f3b-ce0577baea7d'::uuid, 'Deposito');
INSERT INTO banco.movimiento
(saldo, valor, fecha, cuenta_id, movimiento_id, tipo_movimiento)
VALUES(312.0, 100.0, '2024-04-04 23:41:39.574', '84b2b517-7c1d-4995-8529-929f3f4d838e'::uuid, '04859f6e-21bc-4e4c-97ba-c64cbd8db6e7'::uuid, 'Deposito');

