-- Esquema mínimo para ejecutar el prototipo en una instalación local de MySQL.
-- No contiene usuarios ni credenciales de demostración.
CREATE DATABASE IF NOT EXISTS zoologic CHARACTER SET utf8mb4;
USE zoologic;

CREATE TABLE IF NOT EXISTS usuario (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    contrasena CHAR(64) NOT NULL,
    rol VARCHAR(20) NOT NULL DEFAULT 'CLIENTE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_usuario_email (email)
);
