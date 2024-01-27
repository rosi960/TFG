package com.example.tfg.model;

public class Dialogo {
    private String personaje;

    private String reaccion;

    private String fondo;

    private String linea;

    public Dialogo(String personaje, String reaccion, String fondo, String linea) {
        this.personaje = personaje;
        this.reaccion = reaccion;
        this.fondo = fondo;
        this.linea = linea;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    public String getReaccion() {
        return reaccion;
    }

    public void setReaccion(String reaccion) {
        this.reaccion = reaccion;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }
}
