package com.truco.modelo;
import java.util.ArrayList;
import java.util.List;

public abstract class Jogador {
    protected String nome;
    protected List<Carta> mao;
    protected int idEquipe;

    public Jogador(String nome, int idEquipe) {
        this.nome = nome;
        this.idEquipe = idEquipe;
        this.mao = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Carta> getMao() { return mao; }
    public int getIdEquipe() { return idEquipe; }
    
    public void receberCarta(Carta c) { mao.add(c); }

    // Método abstrato que será implementado pelas filhas
    public abstract Carta jogarCarta(PartidaStatus status);
}