package ArteCIMA.DAO;

import ArteCIMA.Modelo.Instructor;
import ArteCIMA.Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultarInstructorDAO {

    public List<Instructor> listarInstructores(
            String idInstructor,
            String numDocumento,
            String nombreCompleto,
            Boolean discapacidad) throws SQLException {

        List<Instructor> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM instructor WHERE 1=1");
        List<String> parametrosTexto = new ArrayList<>();
        
        boolean isSearching = (numDocumento != null || nombreCompleto != null);

        if (isSearching) {
            sql.append(" AND (1=0");
            
            if (numDocumento != null && !numDocumento.isBlank()) {
                sql.append(" OR num_documento ILIKE ?");
                parametrosTexto.add(numDocumento);
            }
            if (nombreCompleto != null && !nombreCompleto.isBlank()) {
                sql.append(" OR nombre_completo ILIKE ?");
                parametrosTexto.add(nombreCompleto);
            }
            sql.append(")"); 
        } else {
            if (idInstructor != null && !idInstructor.isBlank()) {
                sql.append(" AND CAST(id_instructor AS TEXT) ILIKE ?");
                parametrosTexto.add(idInstructor);
            }
        }
        
        if (discapacidad != null) {
            sql.append(" AND discapacidad = ?");
        }

        sql.append(" ORDER BY id_instructor");

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            int index = 1;
            
            for (String param : parametrosTexto) {
                ps.setString(index++, "%" + param + "%");
            }
            
            if (discapacidad != null) {
                ps.setBoolean(index++, discapacidad);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Instructor i = new Instructor();
                    i.setIdInstructor(rs.getInt("id_instructor"));
                    i.setTipoDocumento(rs.getString("tipo_documento"));
                    i.setNumDocumento(rs.getString("num_documento"));
                    i.setNombreCompleto(rs.getString("nombre_completo"));
                    i.setTelefono(rs.getString("telefono"));
                    i.setCorreo(rs.getString("correo"));
                    i.setDiscapacidad(rs.getBoolean("discapacidad"));
                    i.setTipoDiscapacidad(rs.getString("tipo_discapacidad"));
                    i.setEspecialidadArtistica(rs.getString("especialidad_artistica"));
                    i.setValorPorClase(rs.getDouble("valor_por_clase"));
                    lista.add(i);
                }
            }
        }

        return lista;
    }

    public Instructor obtenerInstructorPorId(int id) throws SQLException {
        String sql = "SELECT * FROM instructor WHERE id_instructor = ?";
        Instructor i = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    i = new Instructor();
                    i.setIdInstructor(rs.getInt("id_instructor"));
                    i.setTipoDocumento(rs.getString("tipo_documento"));
                    i.setNumDocumento(rs.getString("num_documento"));
                    i.setNombreCompleto(rs.getString("nombre_completo"));
                    i.setTelefono(rs.getString("telefono"));
                    i.setCorreo(rs.getString("correo"));
                    i.setDiscapacidad(rs.getBoolean("discapacidad"));
                    i.setTipoDiscapacidad(rs.getString("tipo_discapacidad"));
                    i.setEspecialidadArtistica(rs.getString("especialidad_artistica"));
                    i.setValorPorClase(rs.getDouble("valor_por_clase"));
                }
            }
        }
        return i;
    }

    public boolean actualizarInstructor(Instructor i) throws SQLException {
        String sql = "UPDATE instructor SET tipo_documento=?, num_documento=?, nombre_completo=?, telefono=?, correo=?, discapacidad=?, tipo_discapacidad=?, especialidad_artistica=?, valor_por_clase=? WHERE id_instructor=?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, i.getTipoDocumento());
            ps.setString(2, i.getNumDocumento());
            ps.setString(3, i.getNombreCompleto());
            ps.setString(4, i.getTelefono());
            ps.setString(5, i.getCorreo());
            ps.setObject(6, i.getDiscapacidad());
            ps.setString(7, i.getTipoDiscapacidad());
            ps.setString(8, i.getEspecialidadArtistica());
            ps.setObject(9, i.getValorPorClase());
            ps.setInt(10, i.getIdInstructor());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarInstructor(int id) throws SQLException {
        String sql = "DELETE FROM instructor WHERE id_instructor=?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}