package com.tsw.studentsupps.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

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
            o.setDataAquisto(rs.getTimestamp(3));
            o.setDataConsegna(rs.getTimestamp(4));
            o.setStato(rs.getString(5));

            return o;
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
                o.setDataAquisto(rs.getTimestamp(3));
                o.setDataConsegna(rs.getTimestamp(4));
                o.setStato(rs.getString(5));

                ordList.add(o);
            }
            return ordList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Ordine ord) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ordine (totale, dataAcquisto, dataConsegna,stato) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, ord.getTotale());
            ps.setTimestamp(2, ord.getDataAquisto());
            ps.setTimestamp(3, ord.getDataConsegna());
            ps.setString(4, ord.getStato());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String id = rs.getString(1);
            ord.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Ordine ord) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ordine SET totale= ?, dataAcquisto= ?, dataConsegna= ?,stato= ?" +
                            "WHERE id= ?");
            ps.setString(5, ord.getId());

            ps.setDouble(1, ord.getTotale());
            ps.setTimestamp(2, ord.getDataAquisto());
            ps.setTimestamp(3, ord.getDataConsegna());
            ps.setString(4, ord.getStato());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
