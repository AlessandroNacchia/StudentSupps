package com.tsw.studentsupps.Model;

import java.util.UUID;

public class Indirizzo {
    private String id;
    private String nazione;
    private String provincia;
    private String citta;
    private String CAP;
    private String via;
    private String numeroTel;
    private boolean is_fatt;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        if(CAP.matches("^[0-9]{5}$"))
            this.CAP= CAP;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        if(numeroTel.matches("^([+]?[(]?[0-9]{1,3}[)]?[-\\s])?([(]?[0-9]{3}[)]?[-\\s]?)?([0-9][-\\s]?){3,10}[0-9]$"))
            this.numeroTel= numeroTel;
    }

    public boolean isFatt() {
        return is_fatt;
    }

    public void setFatt(boolean is_fatt) {
        this.is_fatt = is_fatt;
    }
}