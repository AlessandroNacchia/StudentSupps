package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IndirizzoDAO {
    public static Indirizzo doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nazione, provincia, citta, CAP, via, numeroTel FROM indirizzo WHERE id= UUID_TO_BIN(?, 1)");
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
                return i;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Indirizzo> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nazione, provincia, citta, CAP, via, numeroTel FROM indirizzo");
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

                indList.add(i);
            }
            return indList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Indirizzo Ind, String userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO indirizzo (id, nazione, provincia, citta, CAP, via, numeroTel, id_utente)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?,?,?,?, UUID_TO_BIN(?, 1))");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setString(2, Ind.getNazione());
            ps.setString(3, Ind.getProvincia());
            ps.setString(4, Ind.getCitta());
            ps.setString(5, Ind.getCAP());
            ps.setString(6, Ind.getVia());
            ps.setString(7, Ind.getNumeroTel());
            ps.setString(8, userId);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            Ind.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Indirizzo Ind) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE indirizzo SET nazione= ?, provincia= ?, citta= ?, CAP= ?, via= ?, numeroTel= ? " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(7, Ind.getId());

            ps.setString(1, Ind.getNazione());
            ps.setString(2, Ind.getProvincia());
            ps.setString(3, Ind.getCitta());
            ps.setString(4, Ind.getCAP());
            ps.setString(5, Ind.getVia());
            ps.setString(6, Ind.getNumeroTel());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Indirizzo ind) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM indirizzo WHERE id= UUID_TO_BIN(?, 1)");

            ps.setString(1, ind.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doRemoveUserId(Indirizzo Ind) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE indirizzo SET id_utente= null " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, Ind.getId());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Indirizzo> doRetrieveByUserId(String userId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nazione, provincia, citta, CAP, via, numeroTel " +
                    "FROM indirizzo WHERE id_utente= UUID_TO_BIN(?, 1)");
            ps.setString(1, userId);
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

                indList.add(i);
            }
            return indList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
