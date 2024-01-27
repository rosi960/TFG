package com.example.tfg;
public class Conversacion {
    private String texto;
    private boolean mostrarCompleto;

    public Conversacion(String texto, boolean mostrarCompleto) {
        this.texto = texto;
        this.mostrarCompleto = mostrarCompleto;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isMostrarCompleto() {
        return mostrarCompleto;
    }
}
