package com.tsw.studentsupps.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoordineDAO {
    public static List<Prodottoordine> doRetrieveProdotti(String orderId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id_prodotto, 1), nome_prodotto, quantita, prezzo_acquisto, IVA_acquisto " +
                            "FROM prodottoordine WHERE id_ordine= UUID_TO_BIN(?, 1)");
            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            List<Prodottoordine> prodList= new ArrayList<>();
            while(rs.next()) {
                Prodottoordine prod= new Prodottoordine();
                prod.setId(rs.getString(1));
                prod.setNome_prodotto(rs.getString(2));
                prod.setQuantita(rs.getShort(3));
                prod.setPrezzo_acquisto(rs.getDouble(4));
                prod.setIVA_acquisto(rs.getShort(5));

                prodList.add(prod);
            }
            return prodList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(String orderId, Prodottoordine prodotto) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement(
                    "INSERT INTO prodottoordine (id_ordine, id_prodotto, nome_prodotto, quantita, prezzo_acquisto, IVA_acquisto)" +
                            "VALUES(UUID_TO_BIN(?, 1), UUID_TO_BIN(?, 1), ?, ?, ?, ?)");

            ps.setString(1, orderId);
            ps.setString(2, prodotto.getId());
            ps.setString(3, prodotto.getNome_prodotto());
            ps.setInt(4, prodotto.getQuantita());
            ps.setDouble(5, prodotto.getPrezzo_acquisto());
            ps.setShort(6, prodotto.getIVA_acquisto());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(String orderId, String prodottoId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM prodottoordine " +
                            "WHERE id_ordine= UUID_TO_BIN(?, 1) AND id_prodotto= UUID_TO_BIN(?, 1)");

            ps.setString(1, orderId);
            ps.setString(2, prodottoId);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
