package ArteCIMA.DAO;

import ArteCIMA.Conexion.Conexion;
import ArteCIMA.Modelo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertarEstudianteDAO {
    
    public boolean insertarEstudiante(String tipoDoc, String numDoc, String nombre,
                                      int edad, String tel, String correo,
                                      boolean discapacidad, String tipoDiscap,
                                      String beneficio, int idGrupo, int idBeca,
                                      Integer idAcudiente) {
        
        String sql = "INSERT INTO estudiante (tipo_documento, num_documento, nombre_completo, edad, telefono, correo, discapacidad, tipo_discapacidad, tipo_beneficio, id_grupo, id_beca, id_acudiente) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipoDoc);
            ps.setString(2, numDoc);
            ps.setString(3, nombre);
            ps.setInt(4, edad);
            ps.setString(5, tel);
            ps.setString(6, correo);
            ps.setBoolean(7, discapacidad);
            ps.setString(8, tipoDiscap);
            ps.setString(9, beneficio);
            ps.setInt(10, idGrupo);
            ps.setInt(11, idBeca);

           
            if (edad >= 18) {
               
                ps.setNull(12, java.sql.Types.INTEGER);
            } else {
               
                if (idAcudiente == null) {
                    throw new SQLException("Un menor de edad requiere ID de acudiente.");
                }
                ps.setInt(12, idAcudiente);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error insertando estudiante: " + e.getMessage());
            return false;
        }
    }
    public Estudiante buscarEstudiante(String criterio) {
    Estudiante est = null;

    try {
        int id = Integer.parseInt(criterio);
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";     // Intentar buscar por id_estudiante 
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    est = mapResultSetToEstudiante(rs);
                    return est;
                }
            }
        }
    } catch (NumberFormatException nfe) {
        // no es un entero → continuar con otras búsquedas
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }

       try {
        String sql = "SELECT * FROM estudiante WHERE num_documento = ?";  // Buscar por número de documento (coincidencia exacta)
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, criterio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    est = mapResultSetToEstudiante(rs);
                    return est;
                }
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }

        try {
        String sql = "SELECT * FROM estudiante WHERE nombre_completo ILIKE ? LIMIT 1"; // Buscar por nombre completo
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + criterio + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    est = mapResultSetToEstudiante(rs);
                    return est;
                }
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }

    return null; // no encontrado
}

    private Estudiante mapResultSetToEstudiante(ResultSet rs) throws java.sql.SQLException { //Helper privado para mapear un ResultSet a un objeto Estudiante (reutilizable)
        Estudiante e = new Estudiante();
        e.setIdEstudiante(rs.getInt("id_estudiante"));
        e.setTipoDocumento(rs.getString("tipo_documento"));
        e.setNumDocumento(rs.getString("num_documento"));
        e.setNombreCompleto(rs.getString("nombre_completo"));
        e.setEdad(rs.getInt("edad"));
        e.setTelefono(rs.getString("telefono"));
        e.setCorreo(rs.getString("correo"));
        e.setDiscapacidad(rs.getString("discapacidad"));
        e.setTipoDiscapacidad(rs.getString("tipo_discapacidad"));
        e.setTipoBeneficio(rs.getString("tipo_beneficio"));
        e.setIdGrupo(rs.getInt("id_grupo"));
        e.setIdBeca(rs.getInt("id_beca"));
        e.setIdAcudiente(rs.getInt("id_acudiente"));
        return e;
}
}

