package com.example.tfg;

public class GameState {
    private static final GameState instance = new GameState();

    private int jugadorId;
    private int partidaId;

    private GameState() {}

    public static GameState getInstance() {
        return instance;
    }

    public int getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(int jugadorId) {
        this.jugadorId = jugadorId;
    }

    public int getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(int partidaId) {
        this.partidaId = partidaId;
    }
}
