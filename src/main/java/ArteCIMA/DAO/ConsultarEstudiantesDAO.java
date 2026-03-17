package ArteCIMA.DAO;

import ArteCIMA.Conexion.Conexion;
import ArteCIMA.Modelo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types; 

public class ConsultarEstudiantesDAO {
    
    public List<Estudiante> listarEstudiantes() {
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante ASC";
                
        List<Estudiante> lista = new ArrayList<>();
    
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { 

            while (rs.next()) {
                
                lista.add(mapResultSetToEstudiante(rs)); // Utiliza la función de mapeo para evitar código duplicado
            }
        } catch (Exception ex) {
            System.err.println("Error al listar estudiantes: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public Estudiante buscarEstudiante(String criterio) {
 
    String sql = "SELECT * FROM estudiante WHERE CAST(id_estudiante AS VARCHAR) = ? OR num_documento = ? OR nombre_completo ILIKE ?"; 
    
    Estudiante est = null;
    
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        String busqueda = criterio.trim();
        
        ps.setString(1, busqueda);              
        ps.setString(2, busqueda);              
        ps.setString(3, "%" + busqueda + "%");  
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                est = mapResultSetToEstudiante(rs); 
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error al ejecutar búsqueda en DAO. Verifique si la columna se llama 'nombre_completo'.");
        ex.printStackTrace();
    } catch (Exception ex) {
        System.err.println("Error general del DAO: " + ex.getMessage());
        ex.printStackTrace();
    }
    return est; 
}

    public boolean editarEstudiante(Estudiante e) {
        String sql = "UPDATE estudiante SET "
                     + "tipo_documento=?, num_documento=?, nombre_completo=?, edad=?, telefono=?, "
                     + "correo=?, discapacidad=?, tipo_discapacidad=?, tipo_beneficio=?, "
                     + "id_grupo=?, id_beca=?, id_acudiente=? "
                     + "WHERE id_estudiante=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getTipoDocumento());
            ps.setString(2, e.getNumDocumento());
            ps.setString(3, e.getNombreCompleto());
            ps.setInt(4, e.getEdad());
            ps.setString(5, e.getTelefono());
            ps.setString(6, e.getCorreo());
                        
            ps.setBoolean(7, e.getDiscapacidad().equalsIgnoreCase("Sí")); 
                        
            ps.setString(8, e.getTipoDiscapacidad());
            ps.setString(9, e.getTipoBeneficio());
                      
            if (e.getIdGrupo() == null) {
                ps.setNull(10, java.sql.Types.INTEGER);
            } else {
                ps.setInt(10, e.getIdGrupo());
            }
        
            if (e.getIdBeca() == null) {
                ps.setNull(11, java.sql.Types.INTEGER);
            } else {
                ps.setInt(11, e.getIdBeca());
            }
        
            if (e.getIdAcudiente() == null) {
                ps.setNull(12, java.sql.Types.INTEGER);
            } else {
                ps.setInt(12, e.getIdAcudiente());
            }
        
            ps.setInt(13, e.getIdEstudiante());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception ex) {
            System.err.println("Error al editar estudiante: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarEstudiante(int idEstudiante) {
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (Exception ex) {
            System.err.println("Error al eliminar estudiante: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    
    private Estudiante mapResultSetToEstudiante(ResultSet rs) throws java.sql.SQLException {
        Estudiante e = new Estudiante();
        e.setIdEstudiante(rs.getInt("id_estudiante"));
        e.setTipoDocumento(rs.getString("tipo_documento"));
        e.setNumDocumento(rs.getString("num_documento"));
        e.setNombreCompleto(rs.getString("nombre_completo"));
        e.setEdad(rs.getInt("edad"));
        e.setTelefono(rs.getString("telefono"));
        e.setCorreo(rs.getString("correo"));
      
        boolean discapacidadDB = rs.getBoolean("discapacidad");
        e.setDiscapacidad(discapacidadDB ? "Sí" : "No"); 
        
        e.setTipoDiscapacidad(rs.getString("tipo_discapacidad"));
        e.setTipoBeneficio(rs.getString("tipo_beneficio"));
     
        int idGrupo = rs.getInt("id_grupo");
        e.setIdGrupo(rs.wasNull() ? null : idGrupo);
        
        int idBeca = rs.getInt("id_beca");
        e.setIdBeca(rs.wasNull() ? null : idBeca);
        
        int idAcudiente = rs.getInt("id_acudiente");
        e.setIdAcudiente(rs.wasNull() ? null : idAcudiente);
        
        return e;
    }
}