package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Grupo {

    private Integer idGrupo;
    private String nombre;
    private String horario;
    private Integer numMaxEstudiantes;
    private Integer idTaller;

    public Integer getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public Integer getNumMaxEstudiantes() { return numMaxEstudiantes; }
    public void setNumMaxEstudiantes(Integer numMaxEstudiantes) { this.numMaxEstudiantes = numMaxEstudiantes; }

    public Integer getIdTaller() { return idTaller; }
    public void setIdTaller(Integer idTaller) { this.idTaller = idTaller; }

    public static List<Grupo> listar() {
        if (SesionUsuario.esInstructor()) {
            Integer idInstructor = SesionUsuario.getIdInstructor();
            if (idInstructor == null) {
                return new ArrayList<>();
            }
            return listarPorInstructor(idInstructor);
        }
        String sql = "SELECT * FROM grupo ORDER BY id_grupo ASC";
        List<Grupo> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar grupos: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static List<Grupo> listarPorInstructor(int idInstructor) {
        String sql = "SELECT g.* FROM grupo g "
                   + "INNER JOIN taller t ON t.id_taller = g.id_taller "
                   + "WHERE t.id_instructor = ? "
                   + "ORDER BY g.id_grupo ASC";
        List<Grupo> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInstructor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar grupos del instructor: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Grupo buscar(String criterio) {
        Integer idInstructor = null;
        if (SesionUsuario.esInstructor()) {
            idInstructor = SesionUsuario.getIdInstructor();
            if (idInstructor == null) {
                return null;
            }
        }
        String sql = "SELECT g.* FROM grupo g ";
        if (idInstructor != null) {
            sql += "INNER JOIN taller t ON t.id_taller = g.id_taller ";
        }
        sql += "WHERE (CAST(g.id_grupo AS VARCHAR) = ? OR "
             + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("g.nombre") + " LIKE ?)";
        if (idInstructor != null) {
            sql += " AND t.id_instructor = ?";
        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String busqueda = criterio.trim();
            ps.setString(1, busqueda);
            ps.setString(2, ArteCIMA.Util.TextoUtil.patronBusqueda(busqueda));
            if (idInstructor != null) {
                ps.setInt(3, idInstructor);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar grupo: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean perteneceAlInstructorSesion(Integer idGrupo) {
        if (!SesionUsuario.esInstructor()) {
            return true;
        }
        Integer idInstructor = SesionUsuario.getIdInstructor();
        if (idInstructor == null || idGrupo == null) {
            return false;
        }
        String sql = "SELECT 1 FROM grupo g "
                   + "INNER JOIN taller t ON t.id_taller = g.id_taller "
                   + "WHERE g.id_grupo = ? AND t.id_instructor = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idInstructor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.err.println("Error al validar grupo del instructor: " + ex.getMessage());
            return false;
        }
    }

    public boolean insertar() {
        String sql = "INSERT INTO grupo (nombre, horario, num_max_estudiantes, id_taller) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, horario);
            if (numMaxEstudiantes == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, numMaxEstudiantes);
            }
            if (idTaller == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, idTaller);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar grupo: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE grupo SET nombre=?, horario=?, num_max_estudiantes=?, id_taller=? WHERE id_grupo=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, horario);
            if (numMaxEstudiantes == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, numMaxEstudiantes);
            }
            if (idTaller == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, idTaller);
            }
            ps.setInt(5, idGrupo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar grupo: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM grupo WHERE id_grupo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar grupo: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Grupo mapResultSet(ResultSet rs) throws SQLException {
        Grupo g = new Grupo();
        g.setIdGrupo(rs.getInt("id_grupo"));
        g.setNombre(rs.getString("nombre"));
        g.setHorario(rs.getString("horario"));
        int max = rs.getInt("num_max_estudiantes");
        g.setNumMaxEstudiantes(rs.wasNull() ? null : max);
        int idTaller = rs.getInt("id_taller");
        g.setIdTaller(rs.wasNull() ? null : idTaller);
        return g;
    }
}
