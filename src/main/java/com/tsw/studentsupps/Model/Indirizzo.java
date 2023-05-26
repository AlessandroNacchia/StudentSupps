package com.tsw.studentsupps.Model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Indirizzo {
    private String id;
    private String Nazione;
    private String Provincia;
    private String citta;
    private String CAP;

    private String via;

    private String numeroTel;
    private boolean is_fatt=false;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public String getNazione() {
        return Nazione;
    }

    public void setNazione(String nazione) {
        Nazione = nazione;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
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
        this.numeroTel = numeroTel;
    }

}