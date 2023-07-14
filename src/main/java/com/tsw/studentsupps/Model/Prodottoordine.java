package com.tsw.studentsupps.Model;

import java.util.UUID;

public class Prodottoordine {
    private String id;
    private String nome_prodotto;
    private short quantita;
    private double prezzo_acquisto;
    private short IVA_acquisto;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(id!=null && UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public String getNome_prodotto() {
        return nome_prodotto;
    }

    public void setNome_prodotto(String nome_prodotto) {
        if(nome_prodotto.matches("^[\\w\\-. ]{2,50}$"))
            this.nome_prodotto= nome_prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(short quantita) {
        if(quantita>=0)
            this.quantita = quantita;
    }

    public double getPrezzo_acquisto() {
        return prezzo_acquisto;
    }

    public void setPrezzo_acquisto(double prezzo_acquisto) {
        if(prezzo_acquisto>=0)
            this.prezzo_acquisto = prezzo_acquisto;
    }

    public short getIVA_acquisto() {
        return IVA_acquisto;
    }

    public void setIVA_acquisto(short IVA_acquisto) {
        if(IVA_acquisto>=0 && IVA_acquisto<=100)
            this.IVA_acquisto = IVA_acquisto;
    }
}
