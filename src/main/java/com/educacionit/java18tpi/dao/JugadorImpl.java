package com.educacionit.java18tpi.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.educacionit.java18tpi.entidades.Jugador;
import com.educacionit.java18tpi.interfaces.AdmConexiones;
import com.educacionit.java18tpi.interfaces.DAO;


public class JugadorImpl implements DAO<Jugador, Integer> {

    private static final String SQL_INSERT = "INSERT INTO jugadores (nickname, email, password, rango) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE jugadores SET nickname=?, email=?, password=?, rango=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM jugadores WHERE id=?";
    private static final String SQL_GET_ALL = "SELECT * FROM jugadores";
    private static final String SQL_EXISTS_BY_ID = "SELECT * FROM jugadores WHERE id=?";

    @Override
    public List<Jugador> getAll() {
        List<Jugador> lista = new ArrayList<>();
        try (Connection conn =  AdmConexiones.INSTANCE.obtenerConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            
            while (rs.next()) {
                Jugador j = new Jugador();
                j.setId(rs.getInt("id"));
                j.setNickname(rs.getString("nickname"));
                j.setEmail(rs.getString("email"));
                j.setPassword(rs.getString("password"));
                j.setRango(rs.getString("rango"));
                lista.add(j);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Jugador getById(Integer id) {
        Jugador j = new Jugador();
        try (Connection conn =  AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_EXISTS_BY_ID)) {
            
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    j.setId(id);
                    j.setNickname(rs.getString("nickname"));
                    j.setEmail(rs.getString("email"));
                    j.setPassword(rs.getString("password"));
                    j.setRango(rs.getString("rango"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return j;
    }

    @Override
    public void insert(Jugador objeto) {
        try (Connection conn =  AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_INSERT)) {

            pst.setString(1, objeto.getNickname());
            pst.setString(2, objeto.getEmail());
            pst.setString(3, objeto.getPassword());
            pst.setString(4, objeto.getRango());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Jugador objeto, Integer id) {
        try (Connection conn =  AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)) {
            
            pst.setString(1, objeto.getNickname());
            pst.setString(2, objeto.getEmail());
            pst.setString(3, objeto.getPassword());
            pst.setString(4, objeto.getRango());
            pst.setInt(5, id);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection conn =  AdmConexiones.INSTANCE.obtenerConexion();
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
        try (Connection conn =  AdmConexiones.INSTANCE.obtenerConexion();
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