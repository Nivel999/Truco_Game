package com.truco.crud;
import com.truco.modelo.Humano;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorUsuarios {
    private List<Humano> usuarios = new ArrayList<>();

    // CREATE com validação
    public boolean adicionarUsuario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("[Erro] O nome não pode ser vazio!");
            return false;
        }
        usuarios.add(new Humano(nome.trim(), 1)); // Equipe 1
        System.out.println("Usuário adicionado com sucesso!");
        return true;
    }

    // READ
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i).getNome());
        }
    }

    // UPDATE
    public void atualizarUsuario(int index, String novoNome) {
        if (index >= 0 && index < usuarios.size() && novoNome != null && !novoNome.trim().isEmpty()) {
            usuarios.get(index).setNome(novoNome.trim());
            System.out.println("Usuário atualizado!");
        } else {
            System.out.println("[Erro] Índice ou nome inválido.");
        }
    }

    // DELETE
    public void removerUsuario(int index) {
        if (index >= 0 && index < usuarios.size()) {
            usuarios.remove(index);
            System.out.println("Usuário removido!");
        } else {
            System.out.println("[Erro] Índice inválido.");
        }
    }

    public Humano selecionarUsuario(int index) {
        if (index >= 0 && index < usuarios.size()) {
            return usuarios.get(index);
        }
        return null;
    }
}