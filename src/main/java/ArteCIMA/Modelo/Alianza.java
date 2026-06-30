package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Alianza {

    private Integer idAlianza;
    private String nombreFundacion;
    private String tipoAlianza;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;
    private Integer idCorporacion;

    public Integer getIdAlianza() { return idAlianza; }
    public void setIdAlianza(Integer idAlianza) { this.idAlianza = idAlianza; }

    public String getNombreFundacion() { return nombreFundacion; }
    public void setNombreFundacion(String nombreFundacion) { this.nombreFundacion = nombreFundacion; }

    public String getTipoAlianza() { return tipoAlianza; }
    public void setTipoAlianza(String tipoAlianza) { this.tipoAlianza = tipoAlianza; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getIdCorporacion() { return idCorporacion; }
    public void setIdCorporacion(Integer idCorporacion) { this.idCorporacion = idCorporacion; }

    public static List<Alianza> listar() {
        String sql = "SELECT * FROM alianza ORDER BY id_alianza ASC";
        List<Alianza> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar alianzas: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Alianza buscar(String criterio) {
        String sql = "SELECT * FROM alianza WHERE CAST(id_alianza AS VARCHAR) = ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("nombre_fundacion") + " LIKE ?";
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
            System.err.println("Error al buscar alianza: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO alianza (nombre_fundacion, tipo_alianza, fecha_inicio, fecha_fin, descripcion, id_corporacion) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreFundacion);
            ps.setString(2, tipoAlianza);
            ps.setDate(3, fechaInicio);
            ps.setDate(4, fechaFin);
            ps.setString(5, descripcion);
            if (idCorporacion == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, idCorporacion);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar alianza: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE alianza SET nombre_fundacion=?, tipo_alianza=?, fecha_inicio=?, fecha_fin=?, "
                   + "descripcion=?, id_corporacion=? WHERE id_alianza=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreFundacion);
            ps.setString(2, tipoAlianza);
            ps.setDate(3, fechaInicio);
            ps.setDate(4, fechaFin);
            ps.setString(5, descripcion);
            if (idCorporacion == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, idCorporacion);
            }
            ps.setInt(7, idAlianza);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar alianza: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM alianza WHERE id_alianza = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlianza);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar alianza: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Alianza mapResultSet(ResultSet rs) throws SQLException {
        Alianza a = new Alianza();
        a.setIdAlianza(rs.getInt("id_alianza"));
        a.setNombreFundacion(rs.getString("nombre_fundacion"));
        a.setTipoAlianza(rs.getString("tipo_alianza"));
        a.setFechaInicio(rs.getDate("fecha_inicio"));
        a.setFechaFin(rs.getDate("fecha_fin"));
        a.setDescripcion(rs.getString("descripcion"));
        int idCorp = rs.getInt("id_corporacion");
        a.setIdCorporacion(rs.wasNull() ? null : idCorp);
        return a;
    }
}
