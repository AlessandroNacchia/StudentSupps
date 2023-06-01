package com.tsw.studentsupps.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndirizzoDAO {
    public static Indirizzo doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nazione, provincia, citta, CAP, via, numeroTel, is_fatt FROM indirizzo WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                    Indirizzo i= new Indirizzo();
                i.setId(rs.getString(1));
                i.setNazione(rs.getString(2));
                i.setProvincia(rs.getString(3));
                i.setCitta(rs.getString(4));
                i.setCAP(rs.getString(5));
                i.setVia(rs.getString(6));
                i.setNumeroTel(rs.getString(7));
                i.setFatt(rs.getBoolean(8));
                return i;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Indirizzo> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nazione, provincia, citta, CAP, via, numeroTel, is_fatt FROM indirizzo");
            ResultSet rs= ps.executeQuery();

            List<Indirizzo> indList= new ArrayList<>();
            while(rs.next()) {
                Indirizzo i= new Indirizzo();
                i.setId(rs.getString(1));
                i.setNazione(rs.getString(2));
                i.setProvincia(rs.getString(3));
                i.setCitta(rs.getString(4));
                i.setCAP(rs.getString(5));
                i.setVia(rs.getString(6));
                i.setNumeroTel(rs.getString(7));
                i.setFatt(rs.getBoolean(8));

                indList.add(i);
            }
            return indList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Indirizzo Ind) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO indirizzo (nazione, provincia, citta, CAP, via, numeroTel, is_fatt) VALUES(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Ind.getNazione());
            ps.setString(2, Ind.getProvincia());
            ps.setString(3, Ind.getCitta());
            ps.setString(4, Ind.getCAP());
            ps.setString(5, Ind.getVia());
            ps.setString(6, Ind.getNumeroTel());
            ps.setBoolean(7, Ind.isFatt());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String id = rs.getString(1);
            Ind.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Indirizzo Ind) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE indirizzo SET nazione= ?, provincia= ?, citta= ?, CAP= ?, via= ?, numeroTel= ?, is_fatt= ? " +
                            "WHERE id= ?");
            ps.setString(8, Ind.getId());

            ps.setString(1, Ind.getNazione());
            ps.setString(2, Ind.getProvincia());
            ps.setString(3, Ind.getCitta());
            ps.setString(4, Ind.getCAP());
            ps.setString(5, Ind.getVia());
            ps.setString(6, Ind.getNumeroTel());
            ps.setBoolean(7, Ind.isFatt());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
