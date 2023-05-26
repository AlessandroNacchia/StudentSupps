package com.tsw.studentsupps.Model;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.UUID;

public class Metodopagamento {

    private String id;
    private String Provider;
    private String numeroHash;
    private  Date dataScadenza;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public String getNumeroHash() {
        return numeroHash;
    }

    public void setNumeroHash(String numero) {
        if(numero.matches("^\\b(\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}$)\\b$"))
        {
            try {
                MessageDigest digest= MessageDigest.getInstance("SHA-1");
                digest.reset();
                digest.update(numero.getBytes(StandardCharsets.UTF_8));
                this.numeroHash= String.format("%040x", new BigInteger(1, digest.digest()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
}
