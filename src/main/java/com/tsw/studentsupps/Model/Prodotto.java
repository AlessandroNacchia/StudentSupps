package com.tsw.studentsupps.Model;

import java.util.UUID;

public class Prodotto {
    private String id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private short IVA;
    private int quantita;

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
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        if(prezzo>=0)
            this.prezzo = prezzo;
    }

    public short getIVA() {
        return IVA;
    }
    public void setIVA(short IVA) {
        this.IVA = IVA;
    }

    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
