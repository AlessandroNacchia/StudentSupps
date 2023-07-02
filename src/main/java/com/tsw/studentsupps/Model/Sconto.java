package com.tsw.studentsupps.Model;
import java.sql.Timestamp;
import java.util.UUID;

public class Sconto {

    private String id;
    private String nome;
    private short percentuale;
    private boolean stato;
    private Timestamp dataInizio;
    private Timestamp dataFine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if(nome.matches("^[a-zA-Z0-9\\-\\s]{2,50}$"))
            this.nome = nome;
    }

    public int getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(short percentuale) {
        if(percentuale>=0 && percentuale<=100)
            this.percentuale = percentuale;
    }

    public boolean isStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public Timestamp getDataFine() {
        return dataFine;
    }

    public void setDataFine(Timestamp dataFine) {
        this.dataFine = dataFine;
    }

    public Timestamp getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Timestamp dataInizio) {
        this.dataInizio = dataInizio;
    }
}
