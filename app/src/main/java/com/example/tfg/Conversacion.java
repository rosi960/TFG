package com.example.tfg;

/**
 * La clase Conversacion representa una conversación con un texto asociado y un estado que indica
 * si debe mostrarse completa o no.
 *
 * @version 1.0
 * @since 2024-05-20
 *
 * Autor: Rosa Martinez
 */
public class Conversacion {
    private String texto;
    private boolean mostrarCompleto;

    /**
     * Constructor de la clase Conversacion.
     *
     * @param texto El texto de la conversación.
     * @param mostrarCompleto Indica si la conversación debe mostrarse completa.
     */
    public Conversacion(String texto, boolean mostrarCompleto) {
        this.texto = texto;
        this.mostrarCompleto = mostrarCompleto;
    }

    /**
     * Obtiene el texto de la conversación.
     *
     * @return El texto de la conversación.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Verifica si la conversación debe mostrarse completa.
     *
     * @return true si la conversación debe mostrarse completa, false en caso contrario.
     */
    public boolean isMostrarCompleto() {
        return mostrarCompleto;
    }
}
