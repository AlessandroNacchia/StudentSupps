package com.tsw.studentsupps.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottocategoriaDAO {
    public static List<String> doRetrieveProdotti(String catId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id_prodotto, 1) FROM prodottocategoria " +
                            "WHERE id_categoria= UUID_TO_BIN(?, 1)");
            ps.setString(1, catId);
            ResultSet rs = ps.executeQuery();

            List<String> prodList= new ArrayList<>();
            while(rs.next()) {
                prodList.add(rs.getString(1));
            }
            return prodList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> doRetrieveCategorie(String prodId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id_categoria, 1) FROM prodottocategoria " +
                            "WHERE id_prodotto= UUID_TO_BIN(?, 1)");
            ps.setString(1, prodId);
            ResultSet rs = ps.executeQuery();

            List<String> catList= new ArrayList<>();
            while(rs.next()) {
                catList.add(rs.getString(1));
            }
            return catList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(String prodottoId, String categoriaId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement(
                    "INSERT INTO prodottocategoria (id_prodotto, id_categoria)" +
                            "VALUES(UUID_TO_BIN(?, 1), UUID_TO_BIN(?, 1))");

            ps.setString(1, prodottoId);
            ps.setString(2, categoriaId);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(String prodottoId, String categoriaId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM prodottocategoria " +
                            "WHERE id_prodotto= UUID_TO_BIN(?, 1) AND id_categoria= UUID_TO_BIN(?, 1)");

            ps.setString(1, prodottoId);
            ps.setString(2, categoriaId);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
