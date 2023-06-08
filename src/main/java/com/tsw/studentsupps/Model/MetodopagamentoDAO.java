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
                        con.prepareStatement("SELECT BIN_TO_UUID(id, 1), provider, numeroHash, dataScadenza FROM metodopagamento WHERE id= UUID_TO_BIN(?, 1)");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Metodopagamento m= new Metodopagamento();
                    m.setId(rs.getString(1));
                    m.setProvider(rs.getString(2));
                    m.setNumeroHash(rs.getString(3));
                    m.setDataScadenza(rs.getDate(4));
                    return m;
                }
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public static List<Metodopagamento> doRetrieveAll() {
            try (Connection con= ConPool.getConnection()) {
                PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), provider, numeroHash, dataScadenza FROM metodopagamento");
                ResultSet rs= ps.executeQuery();

                List<Metodopagamento> mpList= new ArrayList<>();
                while(rs.next()) {
                    Metodopagamento m= new Metodopagamento();
                    m.setId(rs.getString(1));
                    m.setProvider(rs.getString(2));
                    m.setNumeroHash(rs.getString(3));
                    m.setDataScadenza(rs.getDate(4));

                    mpList.add(m);
                }
                return mpList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        public static void doSave(Metodopagamento mp) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO metodopagamento (id, provider, numeroHash, dataScadenza)" +
                                "VALUES(UUID_TO_BIN(?, 1),?,?,?)");

                UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
                ps.setString(1, randUUID.toString());
                ps.setString(2, mp.getProvider());
                ps.setString(3, mp.getNumeroHash());
                ps.setDate(4, mp.getDataScadenza());
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
                        "UPDATE metodopagamento SET provider= ?, numeroHash= ?, dataScadenza= ?" +
                                "WHERE id= UUID_TO_BIN(?, 1)");
                ps.setString(4, mp.getId());

                ps.setString(1, mp.getProvider());
                ps.setString(2, mp.getNumeroHash());
                ps.setDate(3, mp.getDataScadenza());
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("UPDATE error.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

