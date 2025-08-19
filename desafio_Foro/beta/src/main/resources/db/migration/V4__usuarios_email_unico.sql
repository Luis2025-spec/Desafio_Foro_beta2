-- Asegura NOT NULL y unicidad (collation por defecto ya es case-insensitive)
ALTER TABLE usuarios
  MODIFY email VARCHAR(255) NOT NULL;

CREATE UNIQUE INDEX uk_usuarios_email ON usuarios(email);
