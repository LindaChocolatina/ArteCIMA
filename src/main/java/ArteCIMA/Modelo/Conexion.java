package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private static final String URL = "jdbc:postgresql://localhost:5433/ArteCIMA";
    private static final String USER = "postgres";
    private static final String PASS = "1222";

    public static Connection getConexion() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("¡Conexión exitosa!");
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
