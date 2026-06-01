package com.truco;

import com.truco.crud.ConexaoBanco;
import com.truco.crud.GerenciadorUsuarios;
import com.truco.crud.HistoricoPartidas;
import com.truco.jogo.Partida;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConexaoBanco.inicializarBanco();

        Scanner sc = new Scanner(System.in);
        GerenciadorUsuarios crud = new GerenciadorUsuarios();
        
        while(true) {
            System.out.println("\n=== SISTEMA DE TRUCO ===");
            System.out.println("1. Adicionar Jogador");
            System.out.println("2. Listar Jogadores");
            System.out.println("3. Atualizar Jogador");
            System.out.println("4. Remover Jogador");
            System.out.println("5. JOGAR TRUCO");
            System.out.println("6. Histórico de Partidas");
            System.out.println("7. Sair");
            System.out.print("Escolha: ");
            
            String opcao = sc.nextLine();
            
            switch (opcao) {
                case "1":
                    System.out.print("Digite o nome do jogador: ");
                    crud.adicionarUsuario(sc.nextLine());
                    break;
                case "2":
                    crud.listarUsuarios();
                    break;
                case "3":
                    crud.listarUsuarios();
                    System.out.print("Digite o ID do jogador: ");
                    int idAlt = Integer.parseInt(sc.nextLine());
                    System.out.print("Novo nome: ");
                    crud.atualizarUsuario(idAlt, sc.nextLine());
                    break;
                case "4":
                    crud.listarUsuarios();
                    System.out.print("Digite o ID do jogador para remover: ");
                    int idRem = Integer.parseInt(sc.nextLine());
                    crud.removerUsuario(idRem);
                    break;
                case "5":
                    crud.listarUsuarios();
                    System.out.print("Selecione o ID do jogador: ");
                    int idJoga = Integer.parseInt(sc.nextLine());
                    var jogador = crud.selecionarUsuario(idJoga);
                    if (jogador != null) {
                        Partida partida = new Partida(jogador);
                        partida.iniciar();
                    } else {
                        System.out.println("Jogador não encontrado. Cadastre antes de jogar!");
                    }
                    break;
                case "6":
                    crud.listarUsuarios();
                    System.out.print("Digite o ID do jogador: ");
                    int idHist = Integer.parseInt(sc.nextLine());
                    System.out.println("\n--- Match History ---");
                    HistoricoPartidas.exibirHistorico(idHist);
                    System.out.println("\n--- Summary ---");
                    HistoricoPartidas.exibirResumo(idHist);
                    break;
                case "7":
                    System.out.println("Saindo...");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}