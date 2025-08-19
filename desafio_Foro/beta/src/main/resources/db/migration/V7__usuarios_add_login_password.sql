-- V7__usuarios_add_login_password.sql

-- 1) Agregar columnas como NULL primero (para no romper filas existentes)
ALTER TABLE usuarios
  ADD COLUMN login VARCHAR(100) NULL AFTER email;
ALTER TABLE usuarios
  ADD COLUMN `password` VARCHAR(255) NULL AFTER login;

-- 2) Rellenar datos existentes
-- login = email recortado (email ya es UNIQUE)
UPDATE usuarios
SET login = LEFT(email, 100)
WHERE (login IS NULL OR login = '') AND email IS NOT NULL;

-- si existe 'contrasena', copiarla a `password`
UPDATE usuarios
SET `password` = contrasena
WHERE `password` IS NULL;

-- 3) Endurecer restricciones
ALTER TABLE usuarios
  MODIFY COLUMN login VARCHAR(100) NOT NULL,
  MODIFY COLUMN `password` VARCHAR(255) NOT NULL;

-- 4) Índice único para login
CREATE UNIQUE INDEX uq_usuarios_login ON usuarios (login);

-- 5) (opcional) eliminar columna vieja 'contrasena' si ya no la usas
ALTER TABLE usuarios
  DROP COLUMN contrasena;