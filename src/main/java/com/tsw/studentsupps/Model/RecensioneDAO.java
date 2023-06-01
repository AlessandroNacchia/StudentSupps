package com.tsw.studentsupps.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO {
    public static Recensione doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), descrizione, voto, autore FROM recensione WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Recensione r= new Recensione();
                r.setId(rs.getString(1));
                r.setDescrizione(rs.getString(2));
                r.setVoto(rs.getShort(3));
                r.setAutore(rs.getString(4));

                return r;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Recensione> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), descrizione, voto, autore FROM recensione");
            ResultSet rs= ps.executeQuery();

            List<Recensione> recList= new ArrayList<>();
            while(rs.next()) {
                Recensione r= new Recensione();
                r.setId(rs.getString(1));
                r.setDescrizione(rs.getString(2));
                r.setVoto(rs.getShort(3));
                r.setAutore(rs.getString(4));

                recList.add(r);
            }
            return recList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Recensione rec) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO recensione (descrizione, voto, autore) VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rec.getDescrizione());
            ps.setShort(2, rec.getVoto());
            ps.setString(3, rec.getAutore());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String id = rs.getString(1);
            rec.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Recensione rec) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE recensione SET descrizione= ?, voto= ?, autore= ?" +
                            "WHERE id= ?");
            ps.setString(4, rec.getId());

            ps.setString(1, rec.getDescrizione());
            ps.setShort(2, rec.getVoto());
            ps.setString(3, rec.getAutore());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
