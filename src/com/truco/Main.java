package com.truco;
import com.truco.crud.GerenciadorUsuarios;
import com.truco.jogo.Partida;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GerenciadorUsuarios crud = new GerenciadorUsuarios();
        
        while(true) {
            System.out.println("\n=== SISTEMA DE TRUCO ===");
            System.out.println("1. Adicionar Jogador");
            System.out.println("2. Listar Jogadores");
            System.out.println("3. Atualizar Jogador");
            System.out.println("4. Remover Jogador");
            System.out.println("5. JOGAR TRUCO");
            System.out.println("6. Sair");
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
                    System.out.print("Digite o número do jogador na lista (1, 2...): ");
                    int idxAlt = Integer.parseInt(sc.nextLine()) - 1;
                    System.out.print("Novo nome: ");
                    crud.atualizarUsuario(idxAlt, sc.nextLine());
                    break;
                case "4":
                    System.out.print("Digite o número do jogador para remover: ");
                    int idxRem = Integer.parseInt(sc.nextLine()) - 1;
                    crud.removerUsuario(idxRem);
                    break;
                case "5":
                    System.out.print("Selecione o jogador (1, 2...): ");
                    int idxJoga = Integer.parseInt(sc.nextLine()) - 1;
                    var jogador = crud.selecionarUsuario(idxJoga);
                    if (jogador != null) {
                        Partida partida = new Partida(jogador);
                        partida.iniciar();
                    } else {
                        System.out.println("Jogador não encontrado. Cadastre antes de jogar!");
                    }
                    break;
                case "6":
                    System.out.println("Saindo...");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}