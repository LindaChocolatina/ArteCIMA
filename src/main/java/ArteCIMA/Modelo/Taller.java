package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Taller {

    private String ultimoError = "";

    private Integer idTaller;
    private String nombre;
    private String tipoArte;
    private String horario;
    private Integer idMetodo;
    private Integer idInstructor;
    private String nombreInstructor;
    private Integer idAlianza;

    public Taller() {
    }

    public Integer getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoArte() {
        return tipoArte;
    }

    public void setTipoArte(String tipoArte) {
        this.tipoArte = tipoArte;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(Integer idMetodo) {
        this.idMetodo = idMetodo;
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdAlianza() {
        return idAlianza;
    }

    public void setIdAlianza(Integer idAlianza) {
        this.idAlianza = idAlianza;
    }

    private static final String SQL_SELECT = "SELECT t.id_taller, t.nombre, t.tipo_arte, t.horario, "
            + "t.id_metodo, t.id_instructor, t.id_alianza, i.nombre_completo AS nombre_instructor "
            + "FROM taller t LEFT JOIN instructor i ON i.id_instructor = t.id_instructor";

    public static List<Taller> listar() {
        String sql = SQL_SELECT + " ORDER BY t.id_taller ASC";
        List<Taller> lista = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar talleres: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public String getUltimoError() {
        return ultimoError;
    }

    public static Taller buscar(String criterio) {
        String sql = SQL_SELECT + " WHERE CAST(t.id_taller AS VARCHAR) = ? "
                   + "OR " + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("t.nombre") + " LIKE ? "
                   + "OR " + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("t.tipo_arte") + " LIKE ? "
                   + "OR " + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("i.nombre_completo") + " LIKE ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String busqueda = criterio.trim();
            String patron = ArteCIMA.Util.TextoUtil.patronBusqueda(busqueda);
            ps.setString(1, busqueda);
            ps.setString(2, patron);
            ps.setString(3, patron);
            ps.setString(4, patron);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar taller: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public static Taller buscar(int id) {
        String sql = SQL_SELECT + " WHERE t.id_taller = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar taller por id: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        ultimoError = "";
        String sql = "INSERT INTO taller (nombre, tipo_arte, horario, id_metodo, id_instructor, id_alianza) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                ultimoError = "No hay conexión con la base de datos.";
                return false;
            }

            ps.setString(1, nombre);
            ps.setString(2, tipoArte);
            ps.setString(3, horario);

            if (idMetodo == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, idMetodo);
            }

            if (idInstructor == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, idInstructor);
            }

            if (idAlianza == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, idAlianza);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ultimoError = interpretarErrorSql(ex);
            System.err.println("Error al insertar taller: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        ultimoError = "";
        String sql = "UPDATE taller SET nombre=?, tipo_arte=?, horario=?, "
                   + "id_metodo=?, id_instructor=?, id_alianza=? WHERE id_taller=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, tipoArte);
            ps.setString(3, horario);

            if (idMetodo == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, idMetodo);
            }

            if (idInstructor == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, idInstructor);
            }

            if (idAlianza == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, idAlianza);
            }

            ps.setInt(7, idTaller);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ultimoError = interpretarErrorSql(ex);
            System.err.println("Error al modificar taller: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        ultimoError = "";
        String sql = "DELETE FROM taller WHERE id_taller = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTaller);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ultimoError = interpretarErrorSql(ex);
            System.err.println("Error al eliminar taller: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static String interpretarErrorSql(SQLException ex) {
        String mensaje = ex.getMessage();
        if (mensaje == null) {
            return "Error desconocido al procesar el taller.";
        }
        String mensajeMinusculas = mensaje.toLowerCase();
        if (mensajeMinusculas.contains("id_metodo")) {
            return "El ID de método no existe.";
        }
        if (mensajeMinusculas.contains("id_instructor")) {
            return "El ID de instructor no existe.";
        }
        if (mensajeMinusculas.contains("id_alianza")) {
            return "El ID de alianza no existe.";
        }
        if (mensajeMinusculas.contains("foreign key") || mensajeMinusculas.contains("violates")) {
            return "Uno de los IDs relacionados no existe en la base de datos.";
        }
        if (mensajeMinusculas.contains("unique") || mensajeMinusculas.contains("duplicate")) {
            return "Ya existe un taller con esos datos.";
        }
        if (mensajeMinusculas.contains("not-null") || mensajeMinusculas.contains("null value")) {
            return "Faltan datos obligatorios para registrar el taller.";
        }
        return mensaje;
    }

    private static Taller mapResultSet(ResultSet rs) throws SQLException {
        Taller t = new Taller();
        t.setIdTaller(rs.getInt("id_taller"));
        t.setNombre(rs.getString("nombre"));
        t.setTipoArte(rs.getString("tipo_arte"));
        t.setHorario(rs.getString("horario"));

        int idMetodo = rs.getInt("id_metodo");
        t.setIdMetodo(rs.wasNull() ? null : idMetodo);

        int idInstructor = rs.getInt("id_instructor");
        t.setIdInstructor(rs.wasNull() ? null : idInstructor);
        t.setNombreInstructor(rs.getString("nombre_instructor"));

        int idAlianza = rs.getInt("id_alianza");
        t.setIdAlianza(rs.wasNull() ? null : idAlianza);

        return t;
    }
}
