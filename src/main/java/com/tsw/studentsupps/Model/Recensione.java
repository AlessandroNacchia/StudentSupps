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
        if(descrizione.matches("^[\\s\\S]{2,1000}$"))
            this.descrizione = descrizione;
    }

    public short getVoto() {
        return voto;
    }

    public void setVoto(short voto) {
        if(voto>=1 && voto<=5)
            this.voto = voto;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        if(autore.matches("^[A-Za-z][A-Za-z0-9_]{5,29}$"))
            this.autore = autore;
    }
}

