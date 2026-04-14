package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    Connection con;
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ciberseguridad", "root", "");
        } catch (Exception e) {
            System.err.println("Error en Conexión: " + e);
        }
        return con;
    }
}