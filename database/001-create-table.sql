\connect fiap-lanches-product;

CREATE TABLE fiap-lanches-client.cliente(
	id bigint(50) PRIMARY KEY NOT NULL,
	cpf varchar(255) NOT NULL,
	nome varchar(255) NOT NULL

);
ALTER TABLE fiap-lanches-client.cliente
  OWNER TO postgres;
  
COMMIT;
