package com.truco.jogo;

import com.truco.modelo.*;
import java.util.List;

public class Partida {
    private final Humano jogadorHumano;
    private final Bot parceiro;
    private final Bot op1;
    private final Bot op2;
    private int scoreDupla = 0;
    private int scoreInimigo = 0;

    public Partida(Humano humano) {
        this.jogadorHumano = humano;
        this.parceiro = new Bot("Partner", 1);
        this.op1 = new Bot("Opponent 1", 2);
        this.op2 = new Bot("Opponent 3", 2);
    }

    public void iniciar() {
        System.out.println("\n==================================================");
        System.out.println(">>>   TRUCO MINEIRO - First to 12 points wins!  <<<");
        System.out.println("==================================================");

        while (scoreDupla < 12 && scoreInimigo < 12) {
            jogarMao();
        }

        exibirResultadoFinal();
    }

    private void jogarMao() {
        distribuirCartas();

        int valorMao = 2;         // Truco Mineiro: each hand starts at 2 points
        int[] vitorias = {0, 0}; // [team1 wins, team2 wins] across tricks
        int vencedorRound1 = -1; // winner of the 1st trick: -1=unknown, 0=tie, 1=team1, 2=team2

        System.out.println("\n==================== NEW HAND ====================");
        System.out.println("Score: You " + scoreDupla + " pts  |  Opponents: " + scoreInimigo + " pts");
        System.out.println("==================================================");

        for (int vez = 1; vez <= 3; vez++) {
            PartidaStatus status = new PartidaStatus();
            status.placarDupla = scoreDupla;
            status.placarInimigo = scoreInimigo;
            status.valorMao = valorMao;
            status.cartasRestantesOp1 = op1.getMao().size();
            status.cartasRestantesOp2 = op2.getMao().size();
            status.cartasRestantesParceiro = parceiro.getMao().size();
            status.cartaJogadaOp1 = null;
            status.cartaJogadaOp2 = null;
            status.cartaJogadaParceiro = null;
            status.vezAtual = vez;

            // Human plays (pressing T calls Truco before playing a real card)
            Carta jogadaHumano = jogadorHumano.jogarCarta(status);
            while (jogadaHumano.getNome().equals("TRUCO")) {
                int novoValor = proximoValorTruco(valorMao);
                System.out.println("\n[!] You called Truco! (" + novoValor + " pts)");
                System.out.println("[!] Opponents: ACCEPTED!");
                valorMao = novoValor;
                status.valorMao = valorMao;
                jogadaHumano = jogadorHumano.jogarCarta(status);
            }
            System.out.println("\n[You played: " + jogadaHumano.getNome() + "]");

            // Opponent 1 plays
            Carta jogadaOp1 = op1.jogarCarta(status);
            status.cartaJogadaOp1 = jogadaOp1;
            status.cartasRestantesOp1 = op1.getMao().size();
            System.out.println("[" + op1.getNome() + " played: " + jogadaOp1.getNome() + "]");

            // Partner plays
            Carta jogadaParceiro = parceiro.jogarCarta(status);
            status.cartaJogadaParceiro = jogadaParceiro;
            status.cartasRestantesParceiro = parceiro.getMao().size();
            System.out.println("[" + parceiro.getNome() + " played: " + jogadaParceiro.getNome() + "]");

            // Opponent 3 plays
            Carta jogadaOp2 = op2.jogarCarta(status);
            status.cartaJogadaOp2 = jogadaOp2;
            status.cartasRestantesOp2 = op2.getMao().size();
            System.out.println("[" + op2.getNome() + " played: " + jogadaOp2.getNome() + "]");

            // Decide trick winner (best card per team)
            int vencedorVez = determinarVencedorVez(jogadaHumano, jogadaOp1, jogadaParceiro, jogadaOp2);
            if (vez == 1) vencedorRound1 = vencedorVez;

            if (vencedorVez == 1) {
                vitorias[0]++;
                System.out.println(">> Your team wins round " + vez + "! <<");
            } else if (vencedorVez == 2) {
                vitorias[1]++;
                System.out.println(">> Opponents win round " + vez + "! <<");
            } else {
                System.out.println(">> Round " + vez + ": Tie! <<");
            }

            // Stop early if one team already has 2 wins,
            // or after round 2 if the win counts differ (round 1 winner settles a tie)
            if (vitorias[0] >= 2 || vitorias[1] >= 2) break;
            if (vez >= 2 && vitorias[0] != vitorias[1]) break;
        }

        // Determine hand winner; if tricks are tied, round 1 winner breaks the tie
        int vencedorMao;
        if (vitorias[0] > vitorias[1]) {
            vencedorMao = 1;
        } else if (vitorias[1] > vitorias[0]) {
            vencedorMao = 2;
        } else {
            vencedorMao = vencedorRound1; // 0 means all tricks tied -> no points
        }

        System.out.println("\n--------------------------------------------------");
        if (vencedorMao == 1) {
            scoreDupla += valorMao;
            System.out.println(">>> Your team wins the hand! (+" + valorMao + " pts)");
        } else if (vencedorMao == 2) {
            scoreInimigo += valorMao;
            System.out.println(">>> Opponents win the hand! (+" + valorMao + " pts)");
        } else {
            System.out.println(">>> Tied hand! No points awarded.");
        }
        System.out.println(">>> Score: You " + scoreDupla + " pts  |  Opponents: " + scoreInimigo + " pts");
        System.out.println("--------------------------------------------------");
    }

    // Truco Mineiro hand values: 2 -> 4 -> 6 -> 10 -> 12
    private int proximoValorTruco(int valorAtual) {
        if (valorAtual == 2)  return 4;
        if (valorAtual == 4)  return 6;
        if (valorAtual == 6)  return 10;
        if (valorAtual == 10) return 12;
        return valorAtual;
    }

    private void distribuirCartas() {
        List<Carta> deck = Baralho.criarBaralho();
        
        jogadorHumano.getMao().clear();
        parceiro.getMao().clear();
        op1.getMao().clear();
        op2.getMao().clear();

        for (int i = 0; i < 3; i++) {
            jogadorHumano.receberCarta(deck.remove(0));
            op1.receberCarta(deck.remove(0));
            parceiro.receberCarta(deck.remove(0));
            op2.receberCarta(deck.remove(0));
        }
    }

    // Returns: 1 = team1 (you+partner), 2 = team2 (opponents), 0 = tie
    private int determinarVencedorVez(Carta c1, Carta c2, Carta c3, Carta c4) {
        int melhorTeam1 = Math.max(
            c1 != null ? c1.getForca() : -1,
            c3 != null ? c3.getForca() : -1
        );
        int melhorTeam2 = Math.max(
            c2 != null ? c2.getForca() : -1,
            c4 != null ? c4.getForca() : -1
        );
        if (melhorTeam1 > melhorTeam2) return 1;
        if (melhorTeam2 > melhorTeam1) return 2;
        return 0;
    }

    private void exibirResultadoFinal() {
        System.out.println("\n==================================================");
        System.out.println("                   GAME OVER                      ");
        System.out.println("==================================================");
        if (scoreDupla >= 12) {
            System.out.println("CONGRATULATIONS! YOUR TEAM WON WITH " + scoreDupla + " POINTS!");
        } else {
            System.out.println("GAME OVER! OPPONENTS WON WITH " + scoreInimigo + " POINTS!");
        }
        System.out.println("Final Score: You " + scoreDupla + " pts  |  Opponents: " + scoreInimigo + " pts");
        System.out.println("==================================================\n");
    }
}