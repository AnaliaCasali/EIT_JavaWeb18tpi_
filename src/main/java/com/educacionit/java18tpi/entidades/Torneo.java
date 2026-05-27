package com.educacionit.java18tpi.entidades;

 
import java.math.BigDecimal;
import java.util.Objects;

public class Torneo implements Comparable<Torneo> {
    private int id;
    private String nombreTorneo;
    private String nombreJuego;
    private int cupo;
    private BigDecimal precio;
    private String plataforma;

    public Torneo() {
    }

    public Torneo(int id, String nombreTorneo, String nombreJuego, int cupo, BigDecimal precio, String plataforma) {
        this.id = id;
        this.nombreTorneo = nombreTorneo;
        this.nombreJuego = nombreJuego;
        this.cupo = cupo;
        this.precio = precio;
        this.plataforma = plataforma;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreTorneo() { return nombreTorneo; }
    public void setNombreTorneo(String nombreTorneo) { this.nombreTorneo = nombreTorneo; }

    public String getNombreJuego() { return nombreJuego; }
    public void setNombreJuego(String nombreJuego) { this.nombreJuego = nombreJuego; }

    public int getCupo() { return cupo; }
    public void setCupo(int cupo) { this.cupo = cupo; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    // Implementación de Comparable (Por precio ascendente, desempata por nombre de torneo)
    @Override
    public int compareTo(Torneo otro) {
        int compPrecio = this.precio.compareTo(otro.getPrecio());
        if (compPrecio != 0) {
            return compPrecio;
        }
        return this.nombreTorneo.compareToIgnoreCase(otro.getNombreTorneo());
    }

    // Métodos Equals y HashCode (Basados en el ID único del torneo)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Torneo torneo = (Torneo) o;
        return id == torneo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método ToString
    @Override
    public String toString() {
        return "Torneo{" +
                "id=" + id +
                ", nombreTorneo='" + nombreTorneo + '\'' +
                ", nombreJuego='" + nombreJuego + '\'' +
                ", cupo=" + cupo +
                ", precio=" + precio +
                ", plataforma='" + plataforma + '\'' +
                '}';
    }
}