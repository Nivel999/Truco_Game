package com.truco.crud;

import com.truco.modelo.Humano;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorUsuarios {

    // CREATE
    public boolean adicionarUsuario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("[Erro] O nome não pode ser vazio!");
            return false;
        }
        String sql = "INSERT INTO usuario (nome) VALUES (?)";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome.trim());
            ps.executeUpdate();
            System.out.println("Usuário adicionado com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("[Erro DB] " + e.getMessage());
            return false;
        }
    }

    // READ
    public void listarUsuarios() {
        String sql = "SELECT id, nome FROM usuario ORDER BY id";
        try (Connection conn = ConexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                System.out.println("[ID " + rs.getInt("id") + "] " + rs.getString("nome"));
            }
            if (!encontrou) {
                System.out.println("Nenhum usuário cadastrado.");
            }
        } catch (SQLException e) {
            System.out.println("[Erro DB] " + e.getMessage());
        }
    }

    // UPDATE
    public void atualizarUsuario(int id, String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            System.out.println("[Erro] O nome não pode ser vazio.");
            return;
        }
        String sql = "UPDATE usuario SET nome = ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novoNome.trim());
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Usuário atualizado!");
            } else {
                System.out.println("[Erro] Usuário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("[Erro DB] " + e.getMessage());
        }
    }

    // DELETE
    public void removerUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Usuário removido!");
            } else {
                System.out.println("[Erro] Usuário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("[Erro DB] " + e.getMessage());
        }
    }

    // SELECT one user by ID
    public Humano selecionarUsuario(int id) {
        String sql = "SELECT id, nome FROM usuario WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Humano h = new Humano(rs.getString("nome"), 1);
                    h.setId(rs.getInt("id"));
                    return h;
                }
            }
        } catch (SQLException e) {
            System.out.println("[Erro DB] " + e.getMessage());
        }
        return null;
    }

    // List all users (returns List for programmatic use)
    public List<Humano> obterTodosUsuarios() {
        List<Humano> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM usuario ORDER BY id";
        try (Connection conn = ConexaoBanco.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Humano h = new Humano(rs.getString("nome"), 1);
                h.setId(rs.getInt("id"));
                lista.add(h);
            }
        } catch (SQLException e) {
            System.out.println("[Erro DB] " + e.getMessage());
        }
        return lista;
    }
}