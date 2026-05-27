package com.educacionit.java18tpi.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.educacionit.java18tpi.entidades.Inscripcion;
import com.educacionit.java18tpi.entidades.Jugador;
import com.educacionit.java18tpi.entidades.Torneo;
import com.educacionit.java18tpi.interfaces.AdmConexiones;
import com.educacionit.java18tpi.interfaces.DAO;

public class InscripcionImpl implements DAO<Inscripcion, Integer>, AdmConexiones {

    // Consultas relacionales complejas para armar los objetos en Java
    private static final String SQL_GET_ALL = 
            "SELECT i.id AS ins_id, i.fecha_inscripcion, " +
            "j.id AS jug_id, j.nickname, j.email, j.rango, " +
            "t.id AS tor_id, t.nombre_torneo, t.nombre_juego, t.cupo, t.precio, t.plataforma " +
            "FROM inscripciones i " +
            "INNER JOIN jugadores j ON i.jugador_id = j.id " +
            "INNER JOIN torneos t ON i.torneo_id = t.id";

    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE i.id = ?";
    
    // Al insertar o actualizar, le pasamos las claves foráneas numéricas usando los IDs de las entidades
    private static final String SQL_INSERT = "INSERT INTO inscripciones (jugador_id, torneo_id, fecha_inscripcion) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE inscripciones SET jugador_id=?, torneo_id=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM inscripciones WHERE id=?";
    private static final String SQL_EXISTS_BY_ID = "SELECT id FROM inscripciones WHERE id=?";

    @Override
    public List<Inscripcion> getAll() {
        List<Inscripcion> lista = new ArrayList<>();
        try (Connection conn = this.ObtenerConexion(); 
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            
            while (rs.next()) {
                // 1. Reconstruir Jugador
                Jugador j = new Jugador(
                    rs.getInt("jug_id"), rs.getString("nickname"),
                    rs.getString("email"), null, rs.getString("rango")
                );
                
                // 2. Reconstruir Torneo
                Torneo t = new Torneo(
                    rs.getInt("tor_id"), rs.getString("nombre_torneo"),
                    rs.getString("nombre_juego"), rs.getInt("cupo"),
                    rs.getBigDecimal("precio"), rs.getString("plataforma")
                );
                
                // 3. Crear Inscripción vinculando ambos objetos y mapeando el LocalDateTime
                Inscripcion ins = new Inscripcion(
                    rs.getInt("ins_id"), j, t,
                    rs.getObject("fecha_inscripcion", LocalDateTime.class)
                );
                
                lista.add(ins);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Inscripcion getById(Integer id) {
        Inscripcion ins = new Inscripcion();
        try (Connection conn = this.ObtenerConexion(); 
             PreparedStatement pst = conn.prepareStatement(SQL_GET_BY_ID)) {
            
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Jugador j = new Jugador(
                        rs.getInt("jug_id"), rs.getString("nickname"),
                        rs.getString("email"), null, rs.getString("rango")
                    );
                    
                    Torneo t = new Torneo(
                        rs.getInt("tor_id"), rs.getString("nombre_torneo"),
                        rs.getString("nombre_juego"), rs.getInt("cupo"),
                        rs.getBigDecimal("precio"), rs.getString("plataforma")
                    );
                    
                    ins.setId(id);
                    ins.setJugador(j);
                    ins.setTorneo(t);
                    ins.setFechaInscripcion(rs.getObject("fecha_inscripcion", LocalDateTime.class));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public void insert(Inscripcion objeto) {
        try (Connection conn = this.ObtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_INSERT)) {

            pst.setInt(1, objeto.getJugador().getId());
            pst.setInt(2, objeto.getTorneo().getId());
            // Guardamos el objeto LocalDateTime directamente en la base de datos
            pst.setObject(3, objeto.getFechaInscripcion());
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Inscripcion objeto, Integer id) {
        try (Connection conn = this.ObtenerConexion(); 
             PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)) {
            
            pst.setInt(1, objeto.getJugador().getId());
            pst.setInt(2, objeto.getTorneo().getId());
            pst.setInt(3, id);
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