package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetodopagamentoDAO {
    public static Metodopagamento doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), provider, numeroHash, lastDigits, dataScadenza FROM metodopagamento WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Metodopagamento m= new Metodopagamento();
                m.setId(rs.getString(1));
                m.setProvider(rs.getString(2));
                m.setNumeroHash(rs.getString(3));
                m.setLastDigits(rs.getString(4));
                m.setDataScadenza(rs.getDate(5));
                return m;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Metodopagamento> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), provider, numeroHash, lastDigits, dataScadenza FROM metodopagamento");
            ResultSet rs= ps.executeQuery();

            List<Metodopagamento> mpList= new ArrayList<>();
            while(rs.next()) {
                Metodopagamento m= new Metodopagamento();
                m.setId(rs.getString(1));
                m.setProvider(rs.getString(2));
                m.setNumeroHash(rs.getString(3));
                m.setLastDigits(rs.getString(4));
                m.setDataScadenza(rs.getDate(5));

                mpList.add(m);
            }
            return mpList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Metodopagamento mp, String userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO metodopagamento (id, provider, numeroHash, lastDigits, dataScadenza, id_utente)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?,?,UUID_TO_BIN(?, 1))");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setString(2, mp.getProvider());
            ps.setString(3, mp.getNumeroHash());
            ps.setString(4, mp.getLastDigits());
            ps.setDate(5, mp.getDataScadenza());
            ps.setString(6, userId);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            mp.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Metodopagamento mp) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE metodopagamento SET provider= ?, numeroHash= ?, lastDigits=?, dataScadenza= ?" +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(4, mp.getId());

            ps.setString(1, mp.getProvider());
            ps.setString(2, mp.getNumeroHash());
            ps.setString(3, mp.getLastDigits());
            ps.setDate(4, mp.getDataScadenza());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Metodopagamento mp) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM metodopagamento WHERE id= UUID_TO_BIN(?, 1)");

            ps.setString(1, mp.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doRemoveUserId(Metodopagamento mp) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE metodopagamento SET id_utente= null " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, mp.getId());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Metodopagamento> doRetrieveByUserId(String userId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), provider, lastDigits, dataScadenza " +
                    "FROM metodopagamento WHERE id_utente= UUID_TO_BIN(?, 1)");
            ps.setString(1, userId);
            ResultSet rs= ps.executeQuery();

            List<Metodopagamento> mpList= new ArrayList<>();
            while(rs.next()) {
                Metodopagamento m= new Metodopagamento();
                m.setId(rs.getString(1));
                m.setProvider(rs.getString(2));
                m.setLastDigits(rs.getString(3));
                m.setDataScadenza(rs.getDate(4));

                mpList.add(m);
            }
            return mpList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

