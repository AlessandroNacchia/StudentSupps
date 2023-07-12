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
        if(nome.matches("^[\\w\\-. ]{2,50}$"))
            this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        if(descrizione.matches("^[\\s\\S]{2,1000}$"))
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
        if(IVA>=0 && IVA<=100)
            this.IVA= IVA;
    }

    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        if(quantita>=0)
            this.quantita = quantita;
    }
}
