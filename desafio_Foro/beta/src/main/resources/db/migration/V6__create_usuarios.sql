CREATE TABLE IF NOT EXISTS usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(200) NOT NULL
);

INSERT INTO usuarios(login, password)
VALUES ('admin', '{noop}admin123')
ON DUPLICATE KEY UPDATE login = login;
