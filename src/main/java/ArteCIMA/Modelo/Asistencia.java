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

    /** Fila de registro diario: estudiante + grupo/taller + presente. */
    public static class FilaDia {
        private Integer idAsistencia;
        private Integer idEstudiante;
        private String nombreEstudiante;
        private Integer idGrupo;
        private String nombreGrupo;
        private String nombreTaller;
        private boolean presente;

        public Integer getIdAsistencia() { return idAsistencia; }
        public void setIdAsistencia(Integer idAsistencia) { this.idAsistencia = idAsistencia; }

        public Integer getIdEstudiante() { return idEstudiante; }
        public void setIdEstudiante(Integer idEstudiante) { this.idEstudiante = idEstudiante; }

        public String getNombreEstudiante() { return nombreEstudiante; }
        public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

        public Integer getIdGrupo() { return idGrupo; }
        public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

        public String getNombreGrupo() { return nombreGrupo; }
        public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }

        public String getNombreTaller() { return nombreTaller; }
        public void setNombreTaller(String nombreTaller) { this.nombreTaller = nombreTaller; }

        public boolean isPresente() { return presente; }
        public void setPresente(boolean presente) { this.presente = presente; }
    }

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

    /**
     * Lista estudiantes (con grupo y taller) y el estado de asistencia de la fecha.
     * Si no hay registro previo, presente queda en true por defecto.
     */
    public static List<FilaDia> listarFilasParaFecha(Date fecha) {
        Integer idInstructor = null;
        if (SesionUsuario.esInstructor()) {
            idInstructor = SesionUsuario.getIdInstructor();
            if (idInstructor == null) {
                return new ArrayList<>();
            }
        }

        String sql = "SELECT e.id_estudiante, e.nombre_completo, "
                   + "g.id_grupo, g.nombre AS nombre_grupo, "
                   + "t.nombre AS nombre_taller, "
                   + "a.id_asistencia, a.presente "
                   + "FROM estudiante e "
                   + "INNER JOIN grupo g ON g.id_grupo = e.id_grupo "
                   + "INNER JOIN taller t ON t.id_taller = g.id_taller "
                   + "LEFT JOIN asistencia a ON a.id_estudiante = e.id_estudiante "
                   + "  AND a.id_grupo = e.id_grupo AND a.fecha = ? ";
        if (idInstructor != null) {
            sql += "WHERE t.id_instructor = ? ";
        }
        sql += "ORDER BY g.nombre ASC, e.nombre_completo ASC";

        List<FilaDia> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, fecha);
            if (idInstructor != null) {
                ps.setInt(2, idInstructor);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FilaDia fila = new FilaDia();
                    fila.setIdEstudiante(rs.getInt("id_estudiante"));
                    fila.setNombreEstudiante(rs.getString("nombre_completo"));
                    fila.setIdGrupo(rs.getInt("id_grupo"));
                    fila.setNombreGrupo(rs.getString("nombre_grupo"));
                    fila.setNombreTaller(rs.getString("nombre_taller"));
                    int idAsist = rs.getInt("id_asistencia");
                    if (rs.wasNull()) {
                        fila.setIdAsistencia(null);
                        fila.setPresente(true);
                    } else {
                        fila.setIdAsistencia(idAsist);
                        fila.setPresente(rs.getBoolean("presente"));
                    }
                    lista.add(fila);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar filas de asistencia: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    /** Inserta o actualiza la asistencia del día (UNIQUE estudiante+grupo+fecha). */
    public static boolean guardarOActualizar(FilaDia fila, Date fecha) {
        return guardarOActualizar(fila, fecha, null);
    }

    public static boolean guardarOActualizar(FilaDia fila, Date fecha, StringBuilder errorOut) {
        if (fila == null || fecha == null || fila.getIdEstudiante() == null || fila.getIdGrupo() == null) {
            if (errorOut != null) {
                errorOut.append("Datos incompletos del estudiante/grupo.");
            }
            return false;
        }
        if (SesionUsuario.esInstructor() && !Grupo.perteneceAlInstructorSesion(fila.getIdGrupo())) {
            if (errorOut != null) {
                errorOut.append("El grupo no pertenece al instructor en sesión.");
            }
            return false;
        }

        Connection con = Conexion.getConexion();
        if (con == null) {
            if (errorOut != null) {
                errorOut.append("Sin conexión a la base de datos.");
            }
            return false;
        }

        String sqlUpsert = "INSERT INTO asistencia (id_estudiante, id_grupo, fecha, presente) "
                + "VALUES (?, ?, ?, ?) "
                + "ON CONFLICT (id_estudiante, id_grupo, fecha) "
                + "DO UPDATE SET presente = EXCLUDED.presente";

        try (con; PreparedStatement ps = con.prepareStatement(sqlUpsert)) {
            ps.setInt(1, fila.getIdEstudiante());
            ps.setInt(2, fila.getIdGrupo());
            ps.setDate(3, fecha);
            ps.setBoolean(4, fila.isPresente());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                return true;
            }
            // Algunos drivers reportan 0 si el UPDATE no cambia el valor; verificar existencia.
            return existeRegistro(con, fila.getIdEstudiante(), fila.getIdGrupo(), fecha);
        } catch (SQLException ex) {
            System.err.println("Error upsert asistencia: " + ex.getMessage());
            // Fallback si la BD no tiene el UNIQUE constraint con ese nombre/columnas.
            boolean ok = guardarConFallback(fila, fecha, errorOut);
            if (!ok && errorOut != null && errorOut.length() == 0) {
                errorOut.append(ex.getMessage());
            }
            return ok;
        }
    }

    private static boolean existeRegistro(Connection con, int idEstudiante, int idGrupo, Date fecha) throws SQLException {
        String sql = "SELECT 1 FROM asistencia WHERE id_estudiante=? AND id_grupo=? AND fecha=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            ps.setInt(2, idGrupo);
            ps.setDate(3, fecha);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static boolean guardarConFallback(FilaDia fila, Date fecha, StringBuilder errorOut) {
        Connection con = Conexion.getConexion();
        if (con == null) {
            if (errorOut != null) {
                errorOut.append("Sin conexión a la base de datos.");
            }
            return false;
        }
        try (con) {
            String sqlSel = "SELECT id_asistencia FROM asistencia WHERE id_estudiante=? AND id_grupo=? AND fecha=?";
            Integer idExistente = null;
            try (PreparedStatement ps = con.prepareStatement(sqlSel)) {
                ps.setInt(1, fila.getIdEstudiante());
                ps.setInt(2, fila.getIdGrupo());
                ps.setDate(3, fecha);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idExistente = rs.getInt("id_asistencia");
                    }
                }
            }
            if (idExistente != null) {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE asistencia SET presente=? WHERE id_asistencia=?")) {
                    ps.setBoolean(1, fila.isPresente());
                    ps.setInt(2, idExistente);
                    return ps.executeUpdate() > 0;
                }
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO asistencia (id_estudiante, id_grupo, fecha, presente) VALUES (?, ?, ?, ?)")) {
                ps.setInt(1, fila.getIdEstudiante());
                ps.setInt(2, fila.getIdGrupo());
                ps.setDate(3, fecha);
                ps.setBoolean(4, fila.isPresente());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException ex) {
            System.err.println("Error fallback asistencia: " + ex.getMessage());
            ex.printStackTrace();
            if (errorOut != null) {
                errorOut.append(ex.getMessage());
            }
            return false;
        }
    }

    public static int guardarLote(List<FilaDia> filas, Date fecha) {
        return guardarLote(filas, fecha, null);
    }

    public static int guardarLote(List<FilaDia> filas, Date fecha, StringBuilder errorOut) {
        if (filas == null || fecha == null) {
            return 0;
        }
        int ok = 0;
        for (FilaDia fila : filas) {
            if (guardarOActualizar(fila, fecha, errorOut)) {
                ok++;
            } else if (errorOut != null && errorOut.length() > 0) {
                break;
            }
        }
        return ok;
    }

    public static List<Asistencia> listar() {
        Integer idInstructor = null;
        if (SesionUsuario.esInstructor()) {
            idInstructor = SesionUsuario.getIdInstructor();
            if (idInstructor == null) {
                return new ArrayList<>();
            }
        }

        String sql;
        if (idInstructor != null) {
            sql = "SELECT a.* FROM asistencia a "
                + "INNER JOIN grupo g ON g.id_grupo = a.id_grupo "
                + "INNER JOIN taller t ON t.id_taller = g.id_taller "
                + "WHERE t.id_instructor = ? "
                + "ORDER BY a.fecha DESC, a.id_asistencia ASC";
        } else {
            sql = "SELECT * FROM asistencia ORDER BY fecha DESC, id_asistencia ASC";
        }

        List<Asistencia> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (idInstructor != null) {
                ps.setInt(1, idInstructor);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar asistencias: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Asistencia buscar(String criterio) {
        Integer idInstructor = null;
        if (SesionUsuario.esInstructor()) {
            idInstructor = SesionUsuario.getIdInstructor();
            if (idInstructor == null) {
                return null;
            }
        }

        String sql = "SELECT a.* FROM asistencia a ";
        if (idInstructor != null) {
            sql += "INNER JOIN grupo g ON g.id_grupo = a.id_grupo "
                 + "INNER JOIN taller t ON t.id_taller = g.id_taller ";
        }
        sql += "WHERE (CAST(a.id_asistencia AS VARCHAR) = ? "
             + "OR CAST(a.id_estudiante AS VARCHAR) = ? OR CAST(a.id_grupo AS VARCHAR) = ?)";
        if (idInstructor != null) {
            sql += " AND t.id_instructor = ?";
        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String busqueda = criterio.trim();
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            if (idInstructor != null) {
                ps.setInt(4, idInstructor);
            }
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
