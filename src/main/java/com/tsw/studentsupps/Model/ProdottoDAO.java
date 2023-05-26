package com.tsw.studentsupps.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {
    public static Prodotto doRetrieveById(int id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT id, nome, descrizione, prezzo FROM Prodotto WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Prodotto> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT id, nome, descrizione, prezzo FROM Prodotto");
            ResultSet rs= ps.executeQuery();

            List<Prodotto> prodList= new ArrayList<>();
            while(rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));

                prodList.add(p);
            }
            return prodList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Prodotto> doRetrieveByCategoria(int idCat) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT id, nome, descrizione, prezzo " +
                    "FROM Prodotto, ProdottoCategoria " +
                    "WHERE Prodotto.id = prodottocategoria.idProdotto AND " +
                    "prodottocategoria.idCategoria = ?");
            ps.setInt(1, idCat);
            ResultSet rs= ps.executeQuery();

            List<Prodotto> prodCatList= new ArrayList<>();
            while(rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getInt(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));

                prodCatList.add(p);
            }
            return prodCatList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(Prodotto prod) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Prodotto (nome, descrizione, prezzo) VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescrizione());
            ps.setDouble(3, prod.getPrezzo());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            prod.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Prodotto prod) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Prodotto SET nome= ?, descrizione= ?, prezzo= ? " +
                            "WHERE id= ?");
            ps.setInt(4, prod.getId());

            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescrizione());
            ps.setDouble(3, prod.getPrezzo());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


