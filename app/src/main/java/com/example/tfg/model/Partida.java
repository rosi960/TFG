package com.example.tfg.model;

public class Partida {
    private int id;
    private int idJugador;  // ID del jugador asociado a la partida
    private String fechaCreacion;

    // Constructor
    public Partida(int idJugador, String fechaCreacion) {
        this.idJugador = idJugador;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters (puedes generarlos automáticamente en Android Studio)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}