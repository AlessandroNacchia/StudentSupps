package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UtenteDAO {
    public static Utente doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), username, passwordHash, email, numeroTel, isAdmin, nome, cognome FROM utente WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utente u= new Utente();
                u.setId(rs.getString(1));
                u.setUsername(rs.getString(2));
                u.setPasswordAlreadyHashed(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setNumeroTel(rs.getString(5));
                u.setAdmin(rs.getBoolean(6));
                u.setNome(rs.getString(7));
                u.setCognome(rs.getString(8));
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Utente> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), username, email, numeroTel, isAdmin, nome, cognome FROM utente");
            ResultSet rs= ps.executeQuery();

            List<Utente> UList= new ArrayList<>();
            while(rs.next()) {
                Utente u= new Utente();
                u.setId(rs.getString(1));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setNumeroTel(rs.getString(4));
                u.setAdmin(rs.getBoolean(5));
                u.setNome(rs.getString(6));
                u.setCognome(rs.getString(7));

                UList.add(u);
            }
            return UList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doSave(Utente u) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement(
                    "INSERT INTO Utente (id, username, passwordHash, email, numeroTel, isAdmin, nome, cognome)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?,?,?,?,?)");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPasswordHash());
            ps.setString(4, u.getEmail());
            ps.setString(5,u.getNumeroTel());
            ps.setBoolean(6, u.isAdmin());
            ps.setString(7, u.getNome());
            ps.setString(8,u.getCognome());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            u.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Retrieve using Username or Email (Both are unique)
    public static Utente doRetrieveByUsernamePassword(String username, String password) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), username, email, numeroTel, isAdmin, nome, cognome " +
                            "FROM Utente WHERE (username=? OR email=?) AND passwordhash=SHA1(?)");
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, password);
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
    //Using Username or Email (Both are unique)
    public static boolean doExistsByUsername(String username) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT 1 " +
                            "FROM Utente WHERE (username=? OR email=?)");
            ps.setString(1, username);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doUpdate(Utente u) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE utente SET username= ?, passwordHash= ?, email= ?, numeroTel= ?, isAdmin= ?, nome= ?, cognome= ? " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
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

    public static void doDelete(Utente u) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM utente WHERE id= UUID_TO_BIN(?, 1)");

            ps.setString(1, u.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdateIdCart(Utente u, Carrello cart) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE utente SET id_carrello= UUID_TO_BIN(?, 1) " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(2, u.getId());
            ps.setString(1, cart.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doRetrieveIdCart(Utente u) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id_carrello, 1) FROM utente WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, u.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
