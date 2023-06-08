package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriaDAO {
    public static Categoria doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nome, descrizione FROM Categoria WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categoria ca= new Categoria();
                ca.setId(rs.getString(1));
                ca.setNome(rs.getString(2));
                ca.setDescrizione(rs.getString(3));
                return ca;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Categoria> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nome, descrizione FROM Categoria");
            ResultSet rs= ps.executeQuery();

            List<Categoria> catList= new ArrayList<>();
            while(rs.next()) {
                Categoria ca= new Categoria();
                ca.setId(rs.getString(1));
                ca.setNome(rs.getString(2));
                ca.setDescrizione(rs.getString(3));

                catList.add(ca);
            }
            return catList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(Categoria cat) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Categoria (id, nome, descrizione)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?)");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setString(2, cat.getNome());
            ps.setString(3, cat.getDescrizione());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            cat.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Categoria cat) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Categoria SET nome= ?, descrizione= ? " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(3, cat.getId());

            ps.setString(1, cat.getNome());
            ps.setString(2, cat.getDescrizione());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


