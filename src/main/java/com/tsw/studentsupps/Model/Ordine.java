package com.tsw.studentsupps.Model;
import java.sql.Timestamp;
import java.util.UUID;

public class Ordine {
    private String id;
    private double totale;
    private Timestamp dataAcquisto;
    private Timestamp dataConsegna;
    private String stato;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public Timestamp getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(Timestamp dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public Timestamp getDataConsegna() {
        return dataConsegna;
    }

    public void setDataConsegna(Timestamp dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        if(totale>=0)
            this.totale = totale;
    }
}
