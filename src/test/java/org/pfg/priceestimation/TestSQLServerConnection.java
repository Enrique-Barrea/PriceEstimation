package org.pfg.priceestimation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSQLServerConnection {

    // Crear una instancia de logger
    private static final Logger logger = LoggerFactory.getLogger(TestSQLServerConnection.class);

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://EB02-INTERNET\\EB00SQL:49895;database=RETROPFG;encrypt=false;trustServerCertificate=true";
        String username = "Ender";
        String password = "Ender";

        // Bloque try-with-resources para manejar la conexión y los recursos de forma automática
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            logger.info("Conexión exitosa!");

            // Crear y ejecutar una consulta SQL simple
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT 1")) {

                // Procesar el resultado de la consulta
                if (rs.next()) {
                    int result = rs.getInt(1);
                    logger.info("Resultado de la consulta: {}", result);
                }
            }
        } catch (SQLException e) {
            // Registrar el error en lugar de imprimir la traza de la excepción
            logger.error("Error al conectar con la base de datos o al ejecutar la consulta", e);
        }
    }
}


