package com.truco.modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    public static List<Carta> criarBaralho() {
        List<Carta> deck = new ArrayList<>();
        // Manilhas
        deck.add(new Carta("4 PAUS", 100));
        deck.add(new Carta("7 CORACAO", 99));
        deck.add(new Carta("A ESPADA", 98));
        deck.add(new Carta("7 OURO", 97));
        
        // Jokers
        deck.add(new Carta("JOKER", 50));
        deck.add(new Carta("JOKER", 50));
        
        // Cartas normais (duplicadas para dar volume ao baralho de 4 pessoas)
        String[] normais = {"3", "2", "A", "K", "J", "Q"};
        int[] forcas = {15, 14, 13, 12, 11, 10};
        
        for (int i = 0; i < normais.length; i++) {
            deck.add(new Carta(normais[i], forcas[i]));
            deck.add(new Carta(normais[i], forcas[i]));
            deck.add(new Carta(normais[i], forcas[i]));
            deck.add(new Carta(normais[i], forcas[i]));
        }
        
        Collections.shuffle(deck);
        return deck;
    }
}