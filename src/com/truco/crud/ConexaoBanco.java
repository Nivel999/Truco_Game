package com.truco.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {
    private static final String URL_BASE = "jdbc:mysql://127.0.0.1:3306";
    private static final String DATABASE = "truco_game_sql";
    private static final String URL = URL_BASE + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static void inicializarBanco() {
        // Create database if it doesn't exist
        try (Connection conn = DriverManager.getConnection(URL_BASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", USUARIO, SENHA);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE);
            System.out.println("[DB] Database '" + DATABASE + "' verified.");

        } catch (SQLException e) {
            System.out.println("[DB ERROR] Could not create database: " + e.getMessage());
            return;
        }

        // Create tables
        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS usuario (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS historico_partidas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    usuario_id INT NOT NULL,
                    resultado ENUM('VITORIA', 'DERROTA', 'EMPATE') NOT NULL,
                    pontos_jogador INT NOT NULL,
                    pontos_oponente INT NOT NULL,
                    data_partida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
                )
            """);

            System.out.println("[DB] Tables initialized successfully.");

        } catch (SQLException e) {
            System.out.println("[DB ERROR] Could not create tables: " + e.getMessage());
        }
    }
}
