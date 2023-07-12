package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdineDAO {        
    public static Ordine doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), totale, dataAcquisto, dataConsegna,stato FROM ordine WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ordine o= new Ordine();
                o.setId(rs.getString(1));
                o.setTotale(rs.getDouble(2));
                o.setDataAcquisto(rs.getTimestamp(3));
                o.setDataConsegna(rs.getTimestamp(4));
                o.setStato(rs.getString(5));

                return o;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doRetrieveMpById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT bin_to_uuid(id_mp,1) FROM ordine WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String doRetrieveIndById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT bin_to_uuid(id_ind,1) FROM ordine WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String doRetrieveUserById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id_utente, 1) FROM ordine WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Ordine> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), totale, dataAcquisto, dataConsegna,stato FROM ordine");
            ResultSet rs= ps.executeQuery();

            List<Ordine> ordList= new ArrayList<>();
            while(rs.next()) {
                Ordine o= new Ordine();
                o.setId(rs.getString(1));
                o.setTotale(rs.getDouble(2));
                o.setDataAcquisto(rs.getTimestamp(3));
                o.setDataConsegna(rs.getTimestamp(4));
                o.setStato(rs.getString(5));

                ordList.add(o);
            }
            return ordList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Ordine> doRetrieveByUserId(String userId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("" +
                    "SELECT BIN_TO_UUID(id, 1), totale, dataAcquisto, dataConsegna, stato, id_mp, id_ind " +
                    "FROM ordine WHERE id_utente= UUID_TO_BIN(?, 1)");
            ps.setString(1, userId);
            ResultSet rs= ps.executeQuery();

            List<Ordine> ordList= new ArrayList<>();
            while(rs.next()) {
                Ordine o= new Ordine();
                o.setId(rs.getString(1));
                o.setTotale(rs.getDouble(2));
                o.setDataAcquisto(rs.getTimestamp(3));
                o.setDataConsegna(rs.getTimestamp(4));
                o.setStato(rs.getString(5));

                ordList.add(o);
            }
            return ordList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(Ordine ord, String userId, String address, String payMethod) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ordine (id, totale, dataAcquisto, dataConsegna, stato, id_utente, id_ind, id_mp)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?,?,UUID_TO_BIN(?, 1),UUID_TO_BIN(?, 1),UUID_TO_BIN(?, 1))");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setDouble(2, ord.getTotale());
            ps.setTimestamp(3, ord.getDataAcquisto());
            ps.setTimestamp(4, ord.getDataConsegna());
            ps.setString(5, ord.getStato());
            ps.setString(6, userId);
            ps.setString(7, address);
            ps.setString(8, payMethod);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ord.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Ordine ord) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ordine SET totale= ?, dataAcquisto= ?, dataConsegna= ?,stato= ?" +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(5, ord.getId());

            ps.setDouble(1, ord.getTotale());
            ps.setTimestamp(2, ord.getDataAcquisto());
            ps.setTimestamp(3, ord.getDataConsegna());
            ps.setString(4, ord.getStato());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean doExistsById_UserId(String orderid, String userId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT 1 " +
                            "FROM ordine WHERE id=UUID_TO_BIN(?, 1) AND id_utente=UUID_TO_BIN(?, 1)");
            ps.setString(1, orderid);
            ps.setString(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean doExistsById(String orderid) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT 1 " +
                            "FROM ordine WHERE id=UUID_TO_BIN(?, 1)");
            ps.setString(1, orderid);
            ResultSet rs= ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Ordine o) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM ordine WHERE id= UUID_TO_BIN(?, 1)");

            ps.setString(1, o.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
