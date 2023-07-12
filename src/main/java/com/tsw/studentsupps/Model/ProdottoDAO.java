package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdottoDAO {
    public static Prodotto doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nome, descrizione,prezzo,IVA,quantita FROM Prodotto WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                p.setIVA(rs.getShort(5));
                p.setQuantita(rs.getInt(6));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Prodotto doRetrieveByName(String name) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nome, descrizione,prezzo,IVA,quantita FROM Prodotto WHERE nome=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                p.setIVA(rs.getShort(5));
                p.setQuantita(rs.getInt(6));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Prodotto> doRetrieveAll(boolean available) {
        try (Connection con= ConPool.getConnection()) {
            String stmt= "SELECT BIN_TO_UUID(id, 1), nome, descrizione,prezzo,IVA,quantita FROM prodotto";
            if(available)
                stmt+= " WHERE quantita>0";

            PreparedStatement ps= con.prepareStatement(stmt);
            ResultSet rs= ps.executeQuery();

            List<Prodotto> prodList= new ArrayList<>();
            while(rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                p.setIVA(rs.getShort(5));
                p.setQuantita(rs.getInt(6));

                prodList.add(p);
            }
            return prodList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Prodotto> doRetrieveByCategoria(String nomeCat, boolean available) {
        try (Connection con= ConPool.getConnection()) {
            String stmt= "SELECT BIN_TO_UUID(P.id, 1), P.nome, P.descrizione, P.prezzo, P.IVA, P.quantita " +
                    "FROM Prodotto AS P, ProdottoCategoria, Categoria AS C " +
                    "WHERE P.id = prodottocategoria.id_prodotto AND " +
                    "prodottocategoria.id_categoria = C.id AND " +
                    "C.nome = ?";
            if(available)
                stmt+= " AND P.quantita>0";

            PreparedStatement ps= con.prepareStatement(stmt);
            ps.setString(1, nomeCat);
            ResultSet rs= ps.executeQuery();

            List<Prodotto> prodCatList= new ArrayList<>();
            while(rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                p.setIVA(rs.getShort(5));
                p.setQuantita(rs.getInt(6));
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
                    "INSERT INTO prodotto (id, nome, descrizione,prezzo,IVA,quantita)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?,?,?)");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setString(2, prod.getNome());
            ps.setString(3, prod.getDescrizione());
            ps.setDouble(4, prod.getPrezzo());
            ps.setShort(5, prod.getIVA());
            ps.setInt(6, prod.getQuantita());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            prod.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Prodotto prod) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Prodotto SET nome= ?, descrizione= ?, prezzo= ?, IVA= ?, quantita= ? " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(6, prod.getId());

            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescrizione());
            ps.setDouble(3, prod.getPrezzo());
            ps.setShort(4, prod.getIVA());
            ps.setInt(5, prod.getQuantita());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdateQuantita(String prodId, int quantita) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Prodotto SET quantita= ? " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(2, prodId);

            ps.setInt(1, quantita);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(Prodotto p) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM prodotto WHERE id= UUID_TO_BIN(?, 1)");

            ps.setString(1, p.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean doExistsByName(String name) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT 1 " +
                            "FROM Prodotto WHERE nome=?");
            ps.setString(1, name);
            ResultSet rs= ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doRemoveDiscountByDiscountId(String discountId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE prodotto SET id_sconto= null " +
                            "WHERE id_sconto= UUID_TO_BIN(?, 1)");
            ps.setString(1, discountId);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean doExistsByDiscount(String discountId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT 1 " +
                            "FROM Prodotto WHERE id_sconto= UUID_TO_BIN(?, 1)");
            ps.setString(1, discountId);
            ResultSet rs= ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdateDiscount(String prodId, String discountId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Prodotto SET id_sconto= UUID_TO_BIN(?, 1) " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(2, prodId);

            ps.setString(1, discountId);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doRemoveDiscount(String prodId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Prodotto SET id_sconto= null " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, prodId);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doRetrieveDiscountId(String prodId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps=
                    con.prepareStatement("SELECT BIN_TO_UUID(id_sconto, 1) FROM Prodotto WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, prodId);
            ResultSet rs= ps.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


