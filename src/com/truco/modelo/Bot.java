package com.truco.modelo;

public class Bot extends Jogador {
    public Bot(String nome, int idEquipe) {
        super(nome, idEquipe);
    }

    @Override
    public Carta jogarCarta(PartidaStatus status) {
        // Lógica simplificada: Joga a primeira carta
        if (!mao.isEmpty()) {
            return mao.remove(0);
        }
        return null;
    }
}