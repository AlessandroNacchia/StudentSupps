package com.tsw.studentsupps.Model;

import java.util.UUID;

public class Recensione {

    private String id;
    private String descrizione;
    private short voto;
    private String autore;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public short getVoto() {
        return voto;
    }

    public void setVoto(short voto) {
        if(voto>=0 && voto<=10)
            this.voto = voto;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        if(autore.matches("^[a-zA-Z\\s]*$"))
            this.autore = autore;
    }
}

