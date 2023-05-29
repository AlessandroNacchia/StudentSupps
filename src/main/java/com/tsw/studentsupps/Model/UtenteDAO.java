package com.tsw.studentsupps.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {
    public static Utente doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), username, passwordHash, email, numeroTel, isAdmin, nome, cognome FROM utente WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utente p= new Utente();
                p.setId(rs.getString(1));
                p.setUsername(rs.getString(2));
                p.setPasswordHash(rs.getString(3));
                p.setEmail(rs.getString(4));
                p.setNumeroTel(rs.getString(5));
                p.setAdmin(rs.getBoolean(6));
                p.setNome(rs.getString(7));
                p.setCognome(rs.getString(8));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Utente> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), username, passwordHash, email, numeroTel, isAdmin, nome, cognome FROM utente");
            ResultSet rs= ps.executeQuery();

            List<Utente> UList= new ArrayList<>();
            while(rs.next()) {
                Utente p= new Utente();
                p.setId(rs.getString(1));
                p.setUsername(rs.getString(2));
                p.setPasswordHash(rs.getString(3));
                p.setEmail(rs.getString(4));
                p.setNumeroTel(rs.getString(5));
                p.setAdmin(rs.getBoolean(6));
                p.setNome(rs.getString(7));
                p.setCognome(rs.getString(8));

                UList.add(p);
            }
            return UList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doSave(Utente u) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement(
                    "INSERT INTO Utente ( username, passwordHash, email, numeroTel, isAdmin, nome, cognome) VALUES(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getEmail());
            ps.setString(4,u.getNumeroTel());
            ps.setBoolean(5, u.isAdmin());
            ps.setString(6, u.getNome());
            ps.setString(7,u.getCognome());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String id= rs.getString(1);
            u.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Utente doRetrieveByUsernamePassword(String username, String password) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT id, username, email, numeroTel, isAdmin, nome, cognome " +
                            "FROM Utente WHERE username=? AND passwordhash=SHA1(?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utente u= new Utente();
                u.setId(rs.getString(1));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setNumeroTel(rs.getString(4));
                u.setAdmin(rs.getBoolean(5));
                u.setNome(rs.getString(6));
                u.setCognome(rs.getString(7));
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doUpdate(Utente u) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE utente SET username= ?, passwordHash= ?, email= ?, numeroTel= ?, isAdmin= ?, nome= ?, cognome= ? " +
                            "WHERE id= ?");
            ps.setString(8, u.getId());
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getEmail());
            ps.setString(4,u.getNumeroTel());
            ps.setBoolean(5, u.isAdmin());
            ps.setString(6, u.getNome());
            ps.setString(7,u.getCognome());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
