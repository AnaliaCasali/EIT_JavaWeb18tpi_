package com.educacionit.java18tpi.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.educacionit.java18tpi.entidades.Torneo;
import com.educacionit.java18tpi.interfaces.AdmConexiones;
import com.educacionit.java18tpi.interfaces.DAO;


public class TorneoImpl implements DAO<Torneo, Integer>, AdmConexiones {

    private static final String SQL_INSERT = "INSERT INTO torneos (nombre_torneo, nombre_juego, cupo, precio, plataforma) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE torneos SET nombre_torneo=?, nombre_juego=?, cupo=?, precio=?, plataforma=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM torneos WHERE id=?";
    private static final String SQL_GET_ALL = "SELECT * FROM torneos";
    private static final String SQL_EXISTS_BY_ID = "SELECT * FROM torneos WHERE id=?";

    @Override
    public List<Torneo> getAll() {
        List<Torneo> lista = new ArrayList<>();
        try (Connection conn = this.ObtenerConexion(); 
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            
            while (rs.next()) {
                Torneo t = new Torneo();
                t.setId(rs.getInt("id"));
                t.setNombreTorneo(rs.getString("nombre_torneo"));
                t.setNombreJuego(rs.getString("nombre_juego"));
                t.setCupo(rs.getInt("cupo"));
                t.setPrecio(rs.getBigDecimal("precio"));
                t.setPlataforma(rs.getString("plataforma"));
                lista.add(t);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Torneo getById(Integer id) {
        Torneo t = new Torneo();
        try (Connection conn = this.ObtenerConexion(); 
             PreparedStatement pst = conn.prepareStatement(SQL_EXISTS_BY_ID)) {
            
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    t.setId(id);
                    t.setNombreTorneo(rs.getString("nombre_torneo"));
                    t.setNombreJuego(rs.getString("nombre_juego"));
                    t.setCupo(rs.getInt("cupo"));
                    t.setPrecio(rs.getBigDecimal("precio"));
                    t.setPlataforma(rs.getString("plataforma"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void insert(Torneo objeto) {
        try (Connection conn = this.ObtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_INSERT)) {

            pst.setString(1, objeto.getNombreTorneo());
            pst.setString(2, objeto.getNombreJuego());
            pst.setInt(3, objeto.getCupo());
            pst.setBigDecimal(4, objeto.getPrecio());
            pst.setString(5, objeto.getPlataforma());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Torneo objeto, Integer id) {
        try (Connection conn = this.ObtenerConexion(); 
             PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)) {
            
            pst.setString(1, objeto.getNombreTorneo());
            pst.setString(2, objeto.getNombreJuego());
            pst.setInt(3, objeto.getCupo());
            pst.setBigDecimal(4, objeto.getPrecio());
            pst.setString(5, objeto.getPlataforma());
            pst.setInt(6, id);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection conn = this.ObtenerConexion(); 
             PreparedStatement pst = conn.prepareStatement(SQL_DELETE)) {
            
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exists(Integer id) {
        boolean existe = false;
        try (Connection conn = this.ObtenerConexion(); 
             PreparedStatement pst = conn.prepareStatement(SQL_EXISTS_BY_ID)) {
            
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
}