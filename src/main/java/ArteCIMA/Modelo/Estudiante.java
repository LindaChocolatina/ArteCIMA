package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Estudiante {

    private int idEstudiante;
    private String tipoDocumento;
    private String numDocumento;
    private String nombreCompleto;
    private int edad;
    private String telefono;
    private String correo;
    private Boolean discapacidad;
    private String tipoDiscapacidad;
    private String tipoBeneficio;

    private Integer idGrupo;
    private Integer idBeca;
    private Integer idAcudiente;

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumDocumento() { return numDocumento; }
    public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Boolean getDiscapacidad() { return discapacidad; }
    public void setDiscapacidad(Boolean discapacidad) { this.discapacidad = discapacidad; }

    public String getTipoDiscapacidad() { return tipoDiscapacidad; }
    public void setTipoDiscapacidad(String tipoDiscapacidad) { this.tipoDiscapacidad = tipoDiscapacidad; }

    public String getTipoBeneficio() { return tipoBeneficio; }
    public void setTipoBeneficio(String tipoBeneficio) { this.tipoBeneficio = tipoBeneficio; }

    public Integer getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

    public Integer getIdBeca() { return idBeca; }
    public void setIdBeca(Integer idBeca) { this.idBeca = idBeca; }

    public Integer getIdAcudiente() { return idAcudiente; }
    public void setIdAcudiente(Integer idAcudiente) { this.idAcudiente = idAcudiente; }

    public static List<Estudiante> listar() {
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante ASC";
        List<Estudiante> lista = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (Exception ex) {
            System.err.println("Error al listar estudiantes: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Estudiante buscar(String criterio) {
        String sql = "SELECT * FROM estudiante WHERE CAST(id_estudiante AS VARCHAR) = ? "
                   + "OR num_documento = ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("nombre_completo") + " LIKE ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String busqueda = criterio.trim();
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, ArteCIMA.Util.TextoUtil.patronBusqueda(busqueda));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar estudiante: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO estudiante (tipo_documento, num_documento, nombre_completo, edad, "
                   + "telefono, correo, discapacidad, tipo_discapacidad, tipo_beneficio, "
                   + "id_grupo, id_beca, id_acudiente) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, tipoDocumento);
                ps.setString(2, numDocumento);
                ps.setString(3, nombreCompleto);
                ps.setInt(4, edad);
                ps.setString(5, telefono);
                ps.setString(6, correo);
                ps.setBoolean(7, Boolean.TRUE.equals(discapacidad));
                ps.setString(8, tipoDiscapacidad);
                ps.setString(9, tipoBeneficio);

                if (idGrupo == null) {
                    ps.setNull(10, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(10, idGrupo);
                }

                if (idBeca == null) {
                    ps.setNull(11, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(11, idBeca);
                }

                if (edad >= 18) {
                    ps.setNull(12, java.sql.Types.INTEGER);
                } else {
                    if (idAcudiente == null) {
                        throw new SQLException("Un menor de edad requiere ID de acudiente.");
                    }
                    ps.setInt(12, idAcudiente);
                }

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error insertando estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE estudiante SET "
                     + "tipo_documento=?, num_documento=?, nombre_completo=?, edad=?, telefono=?, "
                     + "correo=?, discapacidad=?, tipo_discapacidad=?, tipo_beneficio=?, "
                     + "id_grupo=?, id_beca=?, id_acudiente=? "
                     + "WHERE id_estudiante=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipoDocumento);
            ps.setString(2, numDocumento);
            ps.setString(3, nombreCompleto);
            ps.setInt(4, edad);
            ps.setString(5, telefono);
            ps.setString(6, correo);
            ps.setBoolean(7, Boolean.TRUE.equals(discapacidad));
            ps.setString(8, tipoDiscapacidad);
            ps.setString(9, tipoBeneficio);

            if (idGrupo == null) {
                ps.setNull(10, java.sql.Types.INTEGER);
            } else {
                ps.setInt(10, idGrupo);
            }

            if (idBeca == null) {
                ps.setNull(11, java.sql.Types.INTEGER);
            } else {
                ps.setInt(11, idBeca);
            }

            if (idAcudiente == null) {
                ps.setNull(12, java.sql.Types.INTEGER);
            } else {
                ps.setInt(12, idAcudiente);
            }

            ps.setInt(13, idEstudiante);
            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            System.err.println("Error al modificar estudiante: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEstudiante);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            System.err.println("Error al eliminar estudiante: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Estudiante mapResultSet(ResultSet rs) throws SQLException {
        Estudiante e = new Estudiante();
        e.setIdEstudiante(rs.getInt("id_estudiante"));
        e.setTipoDocumento(rs.getString("tipo_documento"));
        e.setNumDocumento(rs.getString("num_documento"));
        e.setNombreCompleto(rs.getString("nombre_completo"));
        e.setEdad(rs.getInt("edad"));
        e.setTelefono(rs.getString("telefono"));
        e.setCorreo(rs.getString("correo"));

        e.setDiscapacidad(rs.getBoolean("discapacidad"));

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
