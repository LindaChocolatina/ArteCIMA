package ArteCIMA.DAO;

import ArteCIMA.Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarUsuarioDAO {
    
    public boolean registrarUsuario(String rol, String nombreCompleto, String nombreUsuario, String correo, String passwordHash) {

        String sql = "INSERT INTO usuarios (nombre_rol, nombre_completo, nombre_usuario, correo, password_hash)"
                   + " VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol);
            ps.setString(2, nombreCompleto);
            ps.setString(3, nombreUsuario);
            ps.setString(4, correo);
            ps.setString(5, passwordHash);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insertando usuario: " + e.getMessage());
            return false;
        }
    }
}

