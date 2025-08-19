-- agrega marca de creación
ALTER TABLE topicos
  ADD COLUMN creado_en DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- (opcional) agrega marca de actualización automática
ALTER TABLE topicos
  ADD COLUMN actualizado_en DATETIME DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP;
