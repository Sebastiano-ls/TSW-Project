-- 1. Creazione del Database
DROP DATABASE IF EXISTS sscrociere;
CREATE DATABASE sscrociere;
USE sscrociere;

-- 2. Creazione Tabella UTENTE
CREATE TABLE utente (
    ID_utente INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL,
    genere VARCHAR(20),
    data_nascita DATE,
    num_telefono VARCHAR(20),
    ruolo VARCHAR(10) DEFAULT 'user'
);

-- 3. Creazione Tabella CROCIERA
CREATE TABLE crociera (
    ID_crociera INT PRIMARY KEY AUTO_INCREMENT,
    nome_crociera VARCHAR(100) NOT NULL,
    descrizione VARCHAR(500),
    data_inizio DATE NOT NULL,
    data_fine DATE NOT NULL,
    prezzo DECIMAL(10,2) NOT NULL,
    sconto DECIMAL(5,2) DEFAULT 0.00,
    attivo BOOLEAN DEFAULT TRUE
);

-- 4. Creazione Tabella ORDINE
CREATE TABLE ordine (
    ID_ordine INT PRIMARY KEY AUTO_INCREMENT,
    data_pagamento DATE NOT NULL,
    tot_ordine DECIMAL(10,2) NOT NULL,
    ID_utente INT NOT NULL,
    FOREIGN KEY (ID_utente) REFERENCES utente(ID_utente) ON DELETE CASCADE
);

-- 5. Creazione Tabella DETTAGLI_ORDINE
CREATE TABLE dettagli_ordine (
    ID_dettaglio INT PRIMARY KEY AUTO_INCREMENT,
    num_biglietto_adulto INT DEFAULT 0,
    num_biglietto_bambino INT DEFAULT 0,
    prezzo_archiviato DECIMAL(10,2) NOT NULL,
    ID_ordine INT NOT NULL,
    ID_crociera INT NOT NULL,
    FOREIGN KEY (ID_ordine) REFERENCES ordine(ID_ordine) ON DELETE CASCADE,
    FOREIGN KEY (ID_crociera) REFERENCES crociera(ID_crociera) ON DELETE RESTRICT
);

-- 6. Creazione Tabella TAPPA
CREATE TABLE tappa (
    ID_tappa INT PRIMARY KEY AUTO_INCREMENT,
    nome_tappa VARCHAR(100) NOT NULL,
    nome_porto VARCHAR(100) NOT NULL
);

-- 7. Creazione Tabella ATTRAVERSA
CREATE TABLE attraversa (
    ID_crociera INT NOT NULL,
    ID_tappa INT NOT NULL,
    data_sosta DATE NOT NULL,
    PRIMARY KEY (ID_crociera, ID_tappa),
    FOREIGN KEY (ID_crociera) REFERENCES crociera(ID_crociera) ON DELETE CASCADE,
    FOREIGN KEY (ID_tappa) REFERENCES tappa(ID_tappa) ON DELETE CASCADE
);
