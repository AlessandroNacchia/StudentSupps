package com.tsw.studentsupps.Model;
import java.sql.Date;
import java.util.UUID;

public class Ordine {
    private String id;
    private Date dataAquisto;
    private Date dataConsegna;
    private String Stato;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public Date getDataAquisto() {
        return dataAquisto;
    }

    public void setDataAquisto(Date dataAquisto) {
        this.dataAquisto = dataAquisto;
    }

    public Date getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public String getStato() {
        return Stato;
    }

    public void setStato(String stato) {
        Stato = stato;
    }
}
