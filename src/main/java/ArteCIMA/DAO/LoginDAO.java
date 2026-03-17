package ArteCIMA.DAO;

import ArteCIMA.Conexion.Conexion;
import ArteCIMA.Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class LoginDAO {

    public Usuario autenticar(String usuario, String claveIngresada) {
        Usuario u = null;

        // **CORRECCIÓN CLAVE**: Incluimos 'nombre_rol' en el SELECT.
        String sql = "SELECT nombre_usuario, password_hash, nombre_completo, nombre_rol " 
                   + "FROM usuarios "
                   + "WHERE nombre_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashAlmacenado = rs.getString("password_hash");

                if (BCrypt.checkpw(claveIngresada, hashAlmacenado)) {
                    
                    // La autenticación es exitosa, cargamos el objeto Usuario
                    u = new Usuario();
                    u.setUsuario(rs.getString("nombre_usuario"));   
                    u.setNombreCompleto(rs.getString("nombre_completo"));
                    
                    // Se carga el Rol desde la base de datos.
                    u.setRol(rs.getString("nombre_rol")); 
                } else {
                    System.out.println("Contraseña incorrecta");
                }

            } else {
                System.out.println("Usuario no encontrado en BD");
            }

        } catch (Exception e) {
            System.out.println("Error en LoginDAO: " + e.getMessage());
        }

        return u;
    }
}

