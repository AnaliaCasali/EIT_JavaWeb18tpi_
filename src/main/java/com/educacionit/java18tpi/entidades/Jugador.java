package com.educacionit.java18tpi.entidades;

 
import java.util.Objects;

public class Jugador implements Comparable<Jugador> {
    private int id;
    private String nickname;
    private String email;
    private String password;
    private String rango;

    public Jugador() {
    }

    public Jugador(int id, String nickname, String email, String password, String rango) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.rango = rango;
    }

    public Jugador(String nickname, String email, String password, String rango) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.rango = rango;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRango() { return rango; }
    public void setRango(String rango) { this.rango = rango; }

    // Implementación de Comparable (Ordena por Nickname de la A a la Z)
    @Override
    public int compareTo(Jugador otro) {
        if (this.nickname == null || otro.getNickname() == null) {
            return 0;
        }
        return this.nickname.compareToIgnoreCase(otro.getNickname());
    }

    // Métodos Equals y HashCode (Basados en el ID único y el Email)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return id == jugador.id && Objects.equals(email, jugador.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    // Método ToString
    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", rango='" + rango + '\'' +
                '}';
    }
}