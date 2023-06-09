USE StudentSupps;

INSERT INTO categoria (nome, descrizione)
    VALUES ('Boosters', 'Zio Pera ....'),
           ('Shakers', 'Fozza Napoli'),
           ('Merch', 'Ufficiale');

INSERT INTO prodotto (nome, descrizione, prezzo, IVA, quantita)
    VALUES ('Teemo', 'Broken Champ', 420.69, 22, 100),
           ('Yasuo', '0/10 Power Spike', 10.10, 22, 50),
           ('Ornn', 'Omnichamp', 100.0, 22, 21);

INSERT INTO prodottocategoria (id_prodotto, id_categoria)
    VALUES (UUID_TO_BIN('aed20a92-0707-11ee-ac43-0a0027000004', 1), UUID_TO_BIN('aecfb880-0707-11ee-ac43-0a0027000004', 1)),
           (UUID_TO_BIN('aed281dc-0707-11ee-ac43-0a0027000004', 1), UUID_TO_BIN('aecfbd97-0707-11ee-ac43-0a0027000004', 1)),
           (UUID_TO_BIN('aed28e1e-0707-11ee-ac43-0a0027000004', 1), UUID_TO_BIN('aecfc95b-0707-11ee-ac43-0a0027000004', 1));