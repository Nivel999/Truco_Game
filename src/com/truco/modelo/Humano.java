package com.truco.modelo;
import java.util.Scanner;

public class Humano extends Jogador {
    private static final Scanner sc = new Scanner(System.in);

    public Humano(String nome, int idEquipe) {
        super(nome, idEquipe);
    }

    @Override
    public Carta jogarCarta(PartidaStatus status) {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println(" Round " + status.vezAtual + "/3  |  You: " + status.placarDupla + " pts  |  Opponents: " + status.placarInimigo + " pts  |  Hand: " + status.valorMao + " pts");
            System.out.println("==================================================");

            System.out.println("\nMe (" + this.nome + "):");
            for (int i = 0; i < mao.size(); i++) {
                System.out.println("  " + (i + 1) + " - " + mao.get(i).getNome());
            }
            int proxTruco = proximoTruco(status.valorMao);
            if (proxTruco != -1) {
                System.out.println("  T - Call Truco (" + proxTruco + " pts)");
            }

            System.out.println("\nOpponent 1:");
            System.out.println("  Cards remaining: " + status.cartasRestantesOp1);
            System.out.println("  Card played: " + (status.cartaJogadaOp1 != null ? status.cartaJogadaOp1.getNome() : "None"));

            System.out.println("\nPartner:");
            System.out.println("  Cards remaining: " + status.cartasRestantesParceiro);
            System.out.println("  Card played: " + (status.cartaJogadaParceiro != null ? status.cartaJogadaParceiro.getNome() : "None"));

            System.out.println("\nOpponent 3:");
            System.out.println("  Cards remaining: " + status.cartasRestantesOp2);
            System.out.println("  Card played: " + (status.cartaJogadaOp2 != null ? status.cartaJogadaOp2.getNome() : "None"));

            System.out.print("\nYour choice: ");
            String input = sc.nextLine().trim().toUpperCase();

            if (input.equals("T") && proxTruco != -1) {
                return new Carta("TRUCO", -1);
            }

            try {
                int escolha = Integer.parseInt(input);
                if (escolha >= 1 && escolha <= mao.size()) {
                    return mao.remove(escolha - 1);
                } else {
                    System.out.println("[!] Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input!");
            }
        }
    }

    // Truco Mineiro escalation: 2 -> 4 -> 6 -> 10 -> 12, returns -1 if no raise is possible
    private int proximoTruco(int valorAtual) {
        if (valorAtual == 2)  return 4;
        if (valorAtual == 4)  return 6;
        if (valorAtual == 6)  return 10;
        if (valorAtual == 10) return 12;
        return -1;
    }
}