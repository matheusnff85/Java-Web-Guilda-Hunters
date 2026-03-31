CREATE DATABASE IF NOT EXISTS guilda_cacadores;
USE guilda_cacadores;

-- Entidade 1: Caçador
CREATE TABLE cacador (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    papel ENUM('ADMIN', 'CACADOR') NOT NULL DEFAULT 'CACADOR'
);

-- Entidade 2: Arma
CREATE TABLE arma (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    dano_base INT NOT NULL
);

-- Entidade 3: Monstro
CREATE TABLE monstro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nivel_ameaca INT NOT NULL,
    fraqueza VARCHAR(100)
);

-- Entidade 4: Local de Caçada
CREATE TABLE local_cacada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    bioma VARCHAR(50) NOT NULL,
    nivel_perigo INT NOT NULL
);

-- Entidade 5 (Relacionamento/Log): HuntLog (Registro de Caçadas)
CREATE TABLE hunt_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_cacada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_cacador BIGINT NOT NULL,
    id_monstro BIGINT NOT NULL,
    id_local BIGINT NOT NULL,
    FOREIGN KEY (id_cacador) REFERENCES cacador(id) ON DELETE CASCADE,
    FOREIGN KEY (id_monstro) REFERENCES monstro(id) ON DELETE CASCADE,
    FOREIGN KEY (id_local) REFERENCES local_cacada(id) ON DELETE CASCADE
);

-- Inserindo um Admin padrão (senha: admin123)
INSERT INTO cacador (nome, login, senha, papel) VALUES ('Administrador da Guilda', 'admin', 'admin123', 'ADMIN');