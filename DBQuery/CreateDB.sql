DROP DATABASE IF EXISTS StudentSupps;
CREATE DATABASE StudentSupps;
USE StudentSupps;

CREATE TABLE Carrello
(
	id				binary(16) 		DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    totale			decimal(10,2)	NOT NULL	CHECK(totale>=0),
    updated_at		timestamp 		DEFAULT current_timestamp ON UPDATE current_timestamp
);

CREATE TABLE Utente 
(
	id 			 	binary(16)	 	DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    username      	varchar(30)		NOT NULL UNIQUE,
    passwordHash	varchar(40)	    NOT NULL,
    email			varchar(30)		NOT NULL UNIQUE,
    numeroTel		varchar(24),
    isAdmin         boolean         NOT NULL,
    nome      		varchar(30)     NOT NULL,
    cognome      	varchar(30)     NOT NULL,
    
    id_carrello		binary(16),
    FOREIGN KEY (id_carrello) REFERENCES Carrello(id) ON UPDATE CASCADE
);

CREATE TABLE MetodoPagamento
(
	id 			 	binary(16)	 	DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    provider		varchar(30)		NOT NULL,
    lastDigits      char(4)         NOT NULL,
    numeroHash		varchar(40)		NOT NULL,
    dataScadenza	date			NOT NULL,
    
    id_utente		binary(16),
    FOREIGN KEY (id_utente) REFERENCES Utente(id) ON UPDATE CASCADE
);

CREATE TABLE Indirizzo
(
	id 			 	binary(16)	 	DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    nazione			varchar(60)		NOT NULL,
    provincia		varchar(60)		NOT NULL,
    citta			varchar(60)		NOT NULL,
    CAP				varchar(10)		NOT NULL,
    via				varchar(100)	NOT NULL,
    numeroTel		varchar(24),
    
    id_utente		binary(16),
    FOREIGN KEY (id_utente) REFERENCES Utente(id) ON UPDATE CASCADE
);

CREATE TABLE Ordine
(
	id				binary(16)	 	DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    totale			decimal(10,2)	NOT NULL	CHECK(totale>=0),
    dataAcquisto	datetime		NOT NULL,
    dataConsegna	datetime		NOT NULL,
    stato			varchar(20)		NOT NULL,
    id_utente		binary(16),
    id_mp			binary(16),
    id_ind			binary(16),
    FOREIGN KEY (id_utente) REFERENCES Utente(id) ON UPDATE CASCADE,
    FOREIGN KEY (id_mp) REFERENCES MetodoPagamento(id) ON UPDATE CASCADE,
    FOREIGN KEY (id_ind) REFERENCES Indirizzo(id) ON UPDATE CASCADE
);

CREATE TABLE Sconto
(
	id				binary(16)	 		DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
	nome            varchar(50)         NOT NULL UNIQUE,
    percentuale		tinyint UNSIGNED 	NOT NULL,
    stato			boolean,
    dataInizio		datetime			NOT NULL,
    dataFine		datetime			NOT NULL
);

CREATE TABLE Prodotto
(
	id				binary(16)	 		DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    nome			varchar(50)			NOT NULL UNIQUE,
    descrizione		varchar(1000),
    prezzo			decimal(10,2)		NOT NULL	CHECK(prezzo>=0),
    IVA				tinyint UNSIGNED 	NOT NULL,	
    quantita		int UNSIGNED 		NOT NULL,
    
    id_sconto		binary(16),
    FOREIGN KEY (id_sconto) REFERENCES Sconto(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Categoria
(
	id				binary(16)	  	DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    nome			varchar(30)		NOT NULL,
    descrizione		varchar(60)
);

CREATE TABLE Recensione
(
	id				binary(16)	 		DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
    descrizione		varchar(1000),
    voto			tinyint UNSIGNED	NOT NULL,
    autore			varchar(30)			NOT NULL,
    
    id_prodotto		binary(16),
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (autore) REFERENCES Utente(username) ON UPDATE CASCADE
);

CREATE TABLE ProdottoCategoria
(
	id_prodotto 	binary(16),
    id_categoria 	binary(16),
    
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id_prodotto, id_categoria)
);

CREATE TABLE ProdottoCarrello
(
    id_carrello 	binary(16),
    id_prodotto 	binary(16),
    quantita		smallint UNSIGNED 	NOT NULL,

    FOREIGN KEY (id_carrello) REFERENCES Carrello(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id_carrello, id_prodotto)
);

CREATE TABLE ProdottoOrdine
(
    id_ordine 			binary(16),
    id_prodotto 		binary(16),
    nome_prodotto		varchar(50)			NOT NULL,
    quantita			smallint UNSIGNED 	NOT NULL,
    prezzo_acquisto		decimal(10,2)		NOT NULL	CHECK(prezzo_acquisto>=0),
    IVA_acquisto		tinyint UNSIGNED 	NOT NULL,	

    FOREIGN KEY (id_ordine) REFERENCES Ordine(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id) ON UPDATE CASCADE ON DELETE SET NULL,
    UNIQUE (id_ordine, id_prodotto)
);