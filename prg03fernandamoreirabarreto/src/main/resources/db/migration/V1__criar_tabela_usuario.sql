-- V1: cria a tabela principal do projeto (usuario)
CREATE TABLE usuario (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(120) NOT NULL,
    cpf             VARCHAR(11)  NOT NULL,
    genero          VARCHAR(20),
    data_nascimento VARCHAR(10),
    telefone        VARCHAR(20),
    email           VARCHAR(150),
    login           VARCHAR(50)  NOT NULL,
    senha           VARCHAR(255) NOT NULL,

    CONSTRAINT pk_usuario       PRIMARY KEY (id),
    CONSTRAINT uk_usuario_cpf   UNIQUE (cpf),
    CONSTRAINT uk_usuario_login UNIQUE (login)
);