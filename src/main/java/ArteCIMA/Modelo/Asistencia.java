package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Asistencia {

    private Integer idAsistencia;
    private Integer idEstudiante;
    private Integer idGrupo;
    private Date fecha;
    private Boolean presente;

    public Integer getIdAsistencia() { return idAsistencia; }
    public void setIdAsistencia(Integer idAsistencia) { this.idAsistencia = idAsistencia; }

    public Integer getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

    public Integer getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Boolean getPresente() { return presente; }
    public void setPresente(Boolean presente) { this.presente = presente; }

    public static List<Asistencia> listar() {
        String sql = "SELECT * FROM asistencia ORDER BY fecha DESC, id_asistencia ASC";
        List<Asistencia> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar asistencias: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Asistencia buscar(String criterio) {
        String sql = "SELECT * FROM asistencia WHERE CAST(id_asistencia AS VARCHAR) = ? "
                   + "OR CAST(id_estudiante AS VARCHAR) = ? OR CAST(id_grupo AS VARCHAR) = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String busqueda = criterio.trim();
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar asistencia: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO asistencia (id_estudiante, id_grupo, fecha, presente) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            ps.setInt(2, idGrupo);
            ps.setDate(3, fecha);
            ps.setBoolean(4, Boolean.TRUE.equals(presente));
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar asistencia: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE asistencia SET id_estudiante=?, id_grupo=?, fecha=?, presente=? WHERE id_asistencia=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            ps.setInt(2, idGrupo);
            ps.setDate(3, fecha);
            ps.setBoolean(4, Boolean.TRUE.equals(presente));
            ps.setInt(5, idAsistencia);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar asistencia: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM asistencia WHERE id_asistencia = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAsistencia);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar asistencia: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Asistencia mapResultSet(ResultSet rs) throws SQLException {
        Asistencia a = new Asistencia();
        a.setIdAsistencia(rs.getInt("id_asistencia"));
        a.setIdEstudiante(rs.getInt("id_estudiante"));
        a.setIdGrupo(rs.getInt("id_grupo"));
        a.setFecha(rs.getDate("fecha"));
        a.setPresente(rs.getBoolean("presente"));
        return a;
    }
}
