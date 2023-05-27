package com.tsw.studentsupps.Model;
import java.sql.Timestamp;
public class Sconto {
    private short percentuale;
    private boolean stato;
    private Timestamp dataInizio;
    private Timestamp dataFine;

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
