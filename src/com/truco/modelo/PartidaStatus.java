package com.truco.modelo;

public class PartidaStatus {
    public int placarDupla;
    public int placarInimigo;
    public int valorMao;               // current hand value: 2, 4, 6, 10, or 12
    public int cartasRestantesOp1;
    public int cartasRestantesOp2;
    public int cartasRestantesParceiro;
    public Carta cartaJogadaOp1;
    public Carta cartaJogadaOp2;
    public Carta cartaJogadaParceiro;
    public int vezAtual;               // current trick: 1, 2, or 3
}