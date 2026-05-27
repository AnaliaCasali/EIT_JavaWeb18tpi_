drop database if exists gamermatch_db;
CREATE DATABASE IF NOT EXISTS gamermatch_db;
USE gamermatch_db;

-- 1. Crear tabla de Jugadores
CREATE TABLE jugadores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rango VARCHAR(30) DEFAULT 'Unranked'
);

-- 2. Crear tabla de Torneos
CREATE TABLE torneos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_torneo VARCHAR(100) NOT NULL,
    nombre_juego VARCHAR(50) NOT NULL,
    cupo INT NOT NULL,
    precio DECIMAL(10,2) DEFAULT 0.00,
    plataforma VARCHAR(30) NOT NULL
);

-- 3. Crear tabla intermedia de Inscripciones
CREATE TABLE inscripciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jugador_id INT NOT NULL,
    torneo_id INT NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (jugador_id) REFERENCES jugadores(id) ON DELETE CASCADE,
    FOREIGN KEY (torneo_id) REFERENCES torneos(id) ON DELETE CASCADE
);

-- Inserción de datos de prueba (Seeders)
INSERT INTO torneos (nombre_torneo, nombre_juego, cupo, precio, plataforma) VALUES
('Torneo Relámpago Spike', 'Valorant', 16, 0.00, 'PC'),
('Liga de Invocadores PRO', 'League of Legends', 32, 15.50, 'PC'),
('Showdown de Consolas', 'Rocket League', 8, 5.00, 'PS5/Xbox'),
('Clasificatorio Open', 'Counter-Strike 2', 64, 0.00, 'PC');


-- 4. Inserción de Jugadores de prueba
-- (Las contraseñas están en texto plano para facilitar tus pruebas locales de login en el Servlet)
INSERT INTO jugadores (nickname, email, password, rango) VALUES
('FakerJunior', 'faker@gmail.com', 'pass1234', 'Diamante'),
('Simple', 'simple@gmail.com', 'pass1234', 'Global Elite'),
('King', 'rey@gmail.com', 'admin123', 'Oro'),
('ChicaGamer', 'val@gmail.com', 'pass1234', 'Inmortal'),
('Thor', 'thor@gmail.com', 'pass1234', 'Unranked');

-- 5. Inserción de Inscripciones de prueba (Tabla intermedia)
-- Esto te servirá para probar los JOINs en el DAO de "Mis Torneos"
INSERT INTO inscripciones (jugador_id, torneo_id) VALUES
(1, 2),
(4, 1),
(2, 4),
(3, 3),
(1, 1);