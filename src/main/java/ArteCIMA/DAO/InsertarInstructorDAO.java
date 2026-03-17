package ArteCIMA.DAO;

import ArteCIMA.Conexion.Conexion; // Asegúrate que tu clase de Conexión se llama así y funciona.
import ArteCIMA.Modelo.Instructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertarInstructorDAO {
      
    public boolean insertarInstructor(Instructor inst) throws SQLException {
       
        String sql = "INSERT INTO instructor (tipo_documento, num_documento, nombre_completo, " +
                     "telefono, correo, discapacidad, tipo_discapacidad, " +
                     "especialidad_artistica, valor_por_clase) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Usamos try-with-resources para asegurar que la conexión y el statement se cierren automáticamente
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, inst.getTipoDocumento());
            ps.setString(2, inst.getNumDocumento());
            ps.setString(3, inst.getNombreCompleto());
            ps.setString(4, inst.getTelefono());
            ps.setString(5, inst.getCorreo());
                       
            ps.setBoolean(6, inst.getDiscapacidad());
            
            ps.setString(7, inst.getTipoDiscapacidad());
            ps.setString(8, inst.getEspecialidadArtistica());
            
            ps.setDouble(9, inst.getValorPorClase());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error SQL al insertar instructor: " + e.getMessage());
            throw e; 
        }
    }
        
    public boolean existeDocumento(String numDocumento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM instructor WHERE num_documento = ?";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, numDocumento);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {                   
                    return rs.getInt(1) > 0;  // Si el conteo es mayor que 0, el documento existe
                }
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error SQL al insertar instructor: " + e.getMessage());
            throw e; 
        }
    }
}