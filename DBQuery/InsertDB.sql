USE StudentSupps;

INSERT INTO categoria (nome, descrizione)
    VALUES ('Boosters', 'Zio Pera ....');

INSERT INTO prodotto (nome, descrizione, prezzo, IVA, quantita)
    VALUES ('Teemo', 'Broken Champ', 420.69, 22, 100);

INSERT INTO prodottocategoria (id_prodotto, id_categoria)
    VALUES (UUID_TO_BIN('80281a05-06ca-11ee-bd61-0a0027000004', 1), UUID_TO_BIN('8022dde2-06ca-11ee-bd61-0a0027000004', 1))