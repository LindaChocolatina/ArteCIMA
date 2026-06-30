package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class Usuario {

    private int idUsuario;
    private String usuario;
    private String rol;
    private String nombreCompleto;
    private String correo;
    private String passwordHash;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public static Usuario autenticar(String nombreUsuario, String clave) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()
                || clave == null || clave.isEmpty()) {
            return null;
        }

        String sql = "SELECT id_usuario, nombre_usuario, password_hash, nombre_completo, nombre_rol, correo "
                   + "FROM usuarios WHERE nombre_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("password_hash");
                    if (BCrypt.checkpw(clave, hash)) {
                        Usuario u = new Usuario();
                        u.setIdUsuario(rs.getInt("id_usuario"));
                        u.setUsuario(rs.getString("nombre_usuario"));
                        u.setNombreCompleto(rs.getString("nombre_completo"));
                        u.setRol(rs.getString("nombre_rol"));
                        u.setCorreo(rs.getString("correo"));
                        return u;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error en autenticación: " + e.getMessage());
        }

        return null;
    }

    public static Usuario buscar(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT id_usuario, nombre_usuario, nombre_completo, nombre_rol, correo "
                   + "FROM usuarios WHERE nombre_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setUsuario(rs.getString("nombre_usuario"));
                    u.setNombreCompleto(rs.getString("nombre_completo"));
                    u.setRol(rs.getString("nombre_rol"));
                    u.setCorreo(rs.getString("correo"));
                    return u;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }

        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO usuarios (nombre_rol, nombre_completo, nombre_usuario, correo, password_hash) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol);
            ps.setString(2, nombreCompleto);
            ps.setString(3, usuario);
            ps.setString(4, correo);
            ps.setString(5, passwordHash);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error insertando usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE usuarios SET nombre_rol=?, nombre_completo=?, nombre_usuario=?, correo=? "
                   + "WHERE id_usuario=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol);
            ps.setString(2, nombreCompleto);
            ps.setString(3, usuario);
            ps.setString(4, correo);
            ps.setInt(5, idUsuario);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al modificar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}
