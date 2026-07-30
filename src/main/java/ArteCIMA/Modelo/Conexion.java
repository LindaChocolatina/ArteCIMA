package ArteCIMA.Modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Conexion {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5442/ArteCIMA";
    private static final String DEFAULT_USER = "artecima";
    private static final String DEFAULT_PASS = "";

    private static final Properties CONFIG = cargarConfiguracion();

    private static Properties cargarConfiguracion() {
        Properties props = new Properties();

        try (InputStream in = Conexion.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (in != null) {
                props.load(in);
                return props;
            }
        } catch (IOException ex) {
            System.err.println("No se pudo leer database.properties del classpath: " + ex.getMessage());
        }

        try (FileInputStream in = new FileInputStream("database.properties")) {
            props.load(in);
        } catch (IOException ex) {
            System.err.println("Usando valores por defecto (sin database.properties): " + ex.getMessage());
        }

        return props;
    }

    private static String prop(String clave, String valorDefecto) {
        String valor = CONFIG.getProperty(clave);
        return (valor == null || valor.isBlank()) ? valorDefecto : valor.trim();
    }

    public static Connection getConexion() {
        String url = prop("db.url", DEFAULT_URL);
        String user = prop("db.user", DEFAULT_USER);
        String pass = prop("db.password", DEFAULT_PASS);

        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión exitosa a " + url);
            return con;
        } catch (SQLException e) {
            String mensaje = "Error de conexión: " + e.getMessage()
                    + "\n\nVerifique:\n"
                    + "• DBeaver → ArteCIMA → SSH → Local port = 5442\n"
                    + "• DBeaver conectado (Prueba conexión en verde)\n"
                    + "• database.properties con usuario/contraseña de Coolify";
            JOptionPane.showMessageDialog(null, mensaje, "ArteCIMA — Base de datos", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
