package com.tsw.studentsupps.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {
    public static void doSave(Utente u) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement(
                    "INSERT INTO Utente (username, passwordHash, email, isAdmin, nome) VALUES(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getEmail());
            ps.setBoolean(4, u.isAdmin());
            ps.setString(5, u.getNome());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id= rs.getInt(1);
            u.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Utente doRetrieveByUsernamePassword(String username, String password) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT id, username, email, isAdmin, nome " +
                            "FROM Utente WHERE username=? AND passwordhash=SHA1(?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utente u= new Utente();
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setAdmin(rs.getBoolean(4));
                u.setNome(rs.getString(5));
                return u;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
