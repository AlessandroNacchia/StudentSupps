package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarrelloDAO {
    public static Carrello doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), totale, updated_at FROM carrello WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Carrello cr= new Carrello();
                cr.setId(rs.getString(1));
                cr.setTotale(rs.getDouble(2));
                cr.setUpdated_at(rs.getTimestamp(3));
                return cr;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Carrello> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), totale, updated_at FROM carrello");
            ResultSet rs= ps.executeQuery();

            List<Carrello> carList= new ArrayList<>();
            while(rs.next()) {
                Carrello cr= new Carrello();
                cr.setId(rs.getString(1));
                cr.setTotale(rs.getDouble(2));
                cr.setUpdated_at(rs.getTimestamp(3));

                carList.add(cr);
            }
            return carList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(Carrello car) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO carrello (id, totale, updated_at)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?)");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setDouble(2, car.getTotale());
            ps.setTimestamp(3, car.getUpdated_at());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            car.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doUpdate(Carrello car) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE carrello SET totale= ?, updated_at= ? " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(3, car.getId());

            ps.setDouble(1, car.getTotale());
            ps.setTimestamp(2, car.getUpdated_at());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Carrello car) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM carrello WHERE id= UUID_TO_BIN(?, 1)");

            ps.setString(1, car.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDeleteUnlinkedCarts(long timeInMillis) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM carrello WHERE id NOT IN (SELECT id_carrello FROM utente) " +
                            "AND updated_at<(current_timestamp-?)");

            ps.setLong(1, timeInMillis);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

