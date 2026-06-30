package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Corporacion {

    private Integer idCorporacion;
    private String nitCorporacion;
    private String nombre;
    private String direccion;
    private String tipoEntidad;

    public Integer getIdCorporacion() { return idCorporacion; }
    public void setIdCorporacion(Integer idCorporacion) { this.idCorporacion = idCorporacion; }

    public String getNitCorporacion() { return nitCorporacion; }
    public void setNitCorporacion(String nitCorporacion) { this.nitCorporacion = nitCorporacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTipoEntidad() { return tipoEntidad; }
    public void setTipoEntidad(String tipoEntidad) { this.tipoEntidad = tipoEntidad; }

    public static List<Corporacion> listar() {
        String sql = "SELECT * FROM corporacion ORDER BY id_corporacion ASC";
        List<Corporacion> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar corporaciones: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Corporacion buscar(String criterio) {
        String sql = "SELECT * FROM corporacion WHERE CAST(id_corporacion AS VARCHAR) = ? "
                   + "OR nit_corporacion = ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("nombre") + " LIKE ?";
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
            System.err.println("Error al buscar corporación: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO corporacion (nit_corporacion, nombre, direccion, tipo_entidad) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nitCorporacion);
            ps.setString(2, nombre);
            ps.setString(3, direccion);
            ps.setString(4, tipoEntidad);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar corporación: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE corporacion SET nit_corporacion=?, nombre=?, direccion=?, tipo_entidad=? WHERE id_corporacion=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nitCorporacion);
            ps.setString(2, nombre);
            ps.setString(3, direccion);
            ps.setString(4, tipoEntidad);
            ps.setInt(5, idCorporacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar corporación: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM corporacion WHERE id_corporacion = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCorporacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar corporación: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Corporacion mapResultSet(ResultSet rs) throws SQLException {
        Corporacion c = new Corporacion();
        c.setIdCorporacion(rs.getInt("id_corporacion"));
        c.setNitCorporacion(rs.getString("nit_corporacion"));
        c.setNombre(rs.getString("nombre"));
        c.setDireccion(rs.getString("direccion"));
        c.setTipoEntidad(rs.getString("tipo_entidad"));
        return c;
    }
}
