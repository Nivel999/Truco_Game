package com.truco.crud;

import java.sql.*;

public class HistoricoPartidas {

    public static void salvarResultado(int usuarioId, String resultado, int pontosJogador, int pontosOponente) {
        String sql = "INSERT INTO historico_partidas (usuario_id, resultado, pontos_jogador, pontos_oponente) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, resultado);
            ps.setInt(3, pontosJogador);
            ps.setInt(4, pontosOponente);
            ps.executeUpdate();
            System.out.println("[DB] Match result saved.");
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Could not save result: " + e.getMessage());
        }
    }

    public static void exibirHistorico(int usuarioId) {
        String sql = """
            SELECT resultado, pontos_jogador, pontos_oponente, data_partida
            FROM historico_partidas
            WHERE usuario_id = ?
            ORDER BY data_partida DESC
        """;
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                boolean encontrou = false;
                while (rs.next()) {
                    encontrou = true;
                    String resultado = rs.getString("resultado");
                    int pj = rs.getInt("pontos_jogador");
                    int po = rs.getInt("pontos_oponente");
                    Timestamp data = rs.getTimestamp("data_partida");
                    System.out.println("  " + data + " | " + resultado + " | You: " + pj + " pts | Opponents: " + po + " pts");
                }
                if (!encontrou) {
                    System.out.println("No match history found for this user.");
                }
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] " + e.getMessage());
        }
    }

    public static void exibirResumo(int usuarioId) {
        String sql = """
            SELECT
                COUNT(*) AS total,
                SUM(CASE WHEN resultado = 'VITORIA' THEN 1 ELSE 0 END) AS vitorias,
                SUM(CASE WHEN resultado = 'DERROTA' THEN 1 ELSE 0 END) AS derrotas,
                SUM(CASE WHEN resultado = 'EMPATE'  THEN 1 ELSE 0 END) AS empates
            FROM historico_partidas
            WHERE usuario_id = ?
        """;
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    int wins = rs.getInt("vitorias");
                    int losses = rs.getInt("derrotas");
                    int draws = rs.getInt("empates");
                    System.out.println("  Total matches: " + total);
                    System.out.println("  Wins: " + wins + " | Losses: " + losses + " | Draws: " + draws);
                }
            }
        } catch (SQLException e) {
            System.out.println("[DB ERROR] " + e.getMessage());
        }
    }
}
