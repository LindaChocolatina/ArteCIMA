package ArteCIMA.Modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Convocatoria {

    private Integer idConvocatoria;
    private String nombre;
    private String entidadOtorgante;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal montoAprobado;

    public Integer getIdConvocatoria() { return idConvocatoria; }
    public void setIdConvocatoria(Integer idConvocatoria) { this.idConvocatoria = idConvocatoria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEntidadOtorgante() { return entidadOtorgante; }
    public void setEntidadOtorgante(String entidadOtorgante) { this.entidadOtorgante = entidadOtorgante; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public BigDecimal getMontoAprobado() { return montoAprobado; }
    public void setMontoAprobado(BigDecimal montoAprobado) { this.montoAprobado = montoAprobado; }

    public static List<Convocatoria> listar() {
        String sql = "SELECT * FROM convocatoria ORDER BY id_convocatoria ASC";
        List<Convocatoria> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar convocatorias: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Convocatoria buscar(String criterio) {
        String sql = "SELECT * FROM convocatoria WHERE CAST(id_convocatoria AS VARCHAR) = ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("nombre") + " LIKE ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String busqueda = criterio.trim();
            ps.setString(1, busqueda);
            ps.setString(2, ArteCIMA.Util.TextoUtil.patronBusqueda(busqueda));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar convocatoria: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO convocatoria (nombre, entidad_otorgante, descripcion, fecha_inicio, fecha_fin, monto_aprobado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, entidadOtorgante);
            ps.setString(3, descripcion);
            ps.setDate(4, fechaInicio);
            ps.setDate(5, fechaFin);
            if (montoAprobado == null) {
                ps.setNull(6, java.sql.Types.NUMERIC);
            } else {
                ps.setBigDecimal(6, montoAprobado);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar convocatoria: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE convocatoria SET nombre=?, entidad_otorgante=?, descripcion=?, "
                   + "fecha_inicio=?, fecha_fin=?, monto_aprobado=? WHERE id_convocatoria=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, entidadOtorgante);
            ps.setString(3, descripcion);
            ps.setDate(4, fechaInicio);
            ps.setDate(5, fechaFin);
            if (montoAprobado == null) {
                ps.setNull(6, java.sql.Types.NUMERIC);
            } else {
                ps.setBigDecimal(6, montoAprobado);
            }
            ps.setInt(7, idConvocatoria);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar convocatoria: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM convocatoria WHERE id_convocatoria = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idConvocatoria);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar convocatoria: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Convocatoria mapResultSet(ResultSet rs) throws SQLException {
        Convocatoria c = new Convocatoria();
        c.setIdConvocatoria(rs.getInt("id_convocatoria"));
        c.setNombre(rs.getString("nombre"));
        c.setEntidadOtorgante(rs.getString("entidad_otorgante"));
        c.setDescripcion(rs.getString("descripcion"));
        c.setFechaInicio(rs.getDate("fecha_inicio"));
        c.setFechaFin(rs.getDate("fecha_fin"));
        c.setMontoAprobado(rs.getBigDecimal("monto_aprobado"));
        return c;
    }
}
