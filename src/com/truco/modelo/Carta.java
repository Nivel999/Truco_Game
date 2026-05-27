package com.truco.modelo;

public class Carta {
    private String nome;
    private int forca;

    public Carta(String nome, int forca) {
        this.nome = nome;
        this.forca = forca;
    }

    public String getNome() { return nome; }
    public int getForca() { return forca; }
    
    @Override
    public String toString() { return nome; }
}