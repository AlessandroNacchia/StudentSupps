package com.tsw.studentsupps.Model;

import java.util.UUID;
import java.sql.Timestamp;

public class Carrello {
    private String id;
    private int  totale;
    private  Timestamp updated_at;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public int getTotale() {
        return totale;
    }

    public void setTotale(int totale) {
        this.totale = totale;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
