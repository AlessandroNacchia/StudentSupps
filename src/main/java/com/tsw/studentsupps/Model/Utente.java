package com.tsw.studentsupps.Model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Utente {
    private String id;
    private String username;
    private String passwordHash;
    private String email;
    private String numeroTel;

    private boolean isAdmin= false;

    private String nome;
    private String cognome;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(UUID.fromString(id).toString().equals(id))
            this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if(username.matches("^[A-Za-z][A-Za-z0-9_]{5,29}$"))
            this.username= username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordToHash) {
        if(passwordToHash.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\[{}\\]:;',?*~$^\\-+=<>]).{8,30}$"))
        {
            try {
                MessageDigest digest= MessageDigest.getInstance("SHA-1");
                digest.reset();
                digest.update(passwordToHash.getBytes(StandardCharsets.UTF_8));
                this.passwordHash= String.format("%040x", new BigInteger(1, digest.digest()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setPasswordAlreadyHashed(String hashedPassword) {
        this.passwordHash= hashedPassword;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if(email.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$"))
            this.email= email;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        if(numeroTel.isEmpty() || numeroTel.matches("^([+]?[(]?[0-9]{1,3}[)]?[-\\s])?([(]?[0-9]{3}[)]?[-\\s]?)?([0-9][-\\s]?){3,10}[0-9]$"))
            this.numeroTel= numeroTel;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin= isAdmin;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if(nome.matches("^[a-zA-Z.\\s]{2,30}$"))
            this.nome= nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        if(cognome.matches("^[a-zA-Z\\s]{2,30}$"))
            this.cognome= cognome;
    }


    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Utente)) {
            return false;
        }

        Utente u= (Utente) o;
        return id.equals(u.getId()) &&
                username.equals(u.getUsername()) &&
                email.equals(u.getEmail()) &&
                numeroTel.equals(u.getNumeroTel()) &&
                isAdmin == u.isAdmin &&
                nome.equals(u.getNome()) &&
                cognome.equals(u.getCognome());
    }
}
