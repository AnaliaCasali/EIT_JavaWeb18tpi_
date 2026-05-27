package com.educacionit.java18tpi.entidades;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inscripcion implements Comparable<Inscripcion> {
    private int id;
    private Jugador jugador;
    private Torneo torneo;
    private LocalDateTime fechaInscripcion; // Cambiado a LocalDateTime

    public Inscripcion() {
    }

    public Inscripcion(int id, Jugador jugador, Torneo torneo, LocalDateTime fechaInscripcion) {
        this.id = id;
        this.jugador = jugador;
        this.torneo = torneo;
        this.fechaInscripcion = fechaInscripcion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }

    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }

    public LocalDateTime getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDateTime fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    // Implementación de Comparable (Por fecha de inscripción cronológica)
    @Override
    public int compareTo(Inscripcion otra) {
        if (this.fechaInscripcion == null || otra.getFechaInscripcion() == null) {
            return 0;
        }
        return this.fechaInscripcion.compareTo(otra.getFechaInscripcion());
    }

    // Métodos Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscripcion that = (Inscripcion) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método ToString
    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", jugador=" + (jugador != null ? jugador.getNickname() : "null") +
                ", torneo=" + (torneo != null ? torneo.getNombreTorneo() : "null") +
                ", fechaInscripcion=" + fechaInscripcion +
                '}';
    }
}