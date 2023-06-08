package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.UUID;

public class ScontoDAO {
    public static Sconto doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), percentuale, stato, dataInizio, dataFine FROM sconto WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Sconto s= new Sconto();
                s.setId(rs.getString(1));
                s.setPercentuale(rs.getShort(2));
                s.setStato(rs.getBoolean(3));
                s.setDataInizio(rs.getTimestamp(4));
                s.setDataFine(rs.getTimestamp(5));
                return s;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Sconto> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), percentuale, stato, dataInizio, dataFine FROM sconto");
            ResultSet rs= ps.executeQuery();

            List<Sconto> scList= new ArrayList<>();
            while(rs.next()) {
                Sconto s= new Sconto();
                s.setId(rs.getString(1));
                s.setPercentuale(rs.getShort(2));
                s.setStato(rs.getBoolean(3));
                s.setDataInizio(rs.getTimestamp(4));
                s.setDataFine(rs.getTimestamp(5));


                scList.add(s);
            }
            return scList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Sconto sc) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO sconto (id, percentuale, stato, dataInizio, dataFine)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?,?)");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setInt(2, sc.getPercentuale());
            ps.setBoolean(3, sc.isStato());
            ps.setTimestamp(4, sc.getDataInizio());
            ps.setTimestamp(5, sc.getDataFine());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            sc.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Sconto sc) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE sconto SET percentuale= ?, stato= ?, dataInizio= ?, dataFine= ?" +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(5, sc.getId());

            ps.setInt(1, sc.getPercentuale());
            ps.setBoolean(2, sc.isStato());
            ps.setTimestamp(3, sc.getDataInizio());
            ps.setTimestamp(4, sc.getDataFine());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
