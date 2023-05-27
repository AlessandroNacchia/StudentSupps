package com.tsw.studentsupps.Model;

public class Recensione {
    private String descrizione;
    private short voto;
    private String autore;


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

