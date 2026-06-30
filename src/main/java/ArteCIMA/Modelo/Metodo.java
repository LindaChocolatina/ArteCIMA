package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Metodo {

    private Integer idMetodo;
    private String nombre;
    private String descripcion;

    public Integer getIdMetodo() { return idMetodo; }
    public void setIdMetodo(Integer idMetodo) { this.idMetodo = idMetodo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public static List<Metodo> listar() {
        String sql = "SELECT * FROM metodo ORDER BY id_metodo ASC";
        List<Metodo> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar métodos: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Metodo buscar(String criterio) {
        String sql = "SELECT * FROM metodo WHERE CAST(id_metodo AS VARCHAR) = ? OR "
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
            System.err.println("Error al buscar método: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO metodo (nombre, descripcion) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar método: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE metodo SET nombre=?, descripcion=? WHERE id_metodo=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, idMetodo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar método: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM metodo WHERE id_metodo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMetodo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar método: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Metodo mapResultSet(ResultSet rs) throws SQLException {
        Metodo m = new Metodo();
        m.setIdMetodo(rs.getInt("id_metodo"));
        m.setNombre(rs.getString("nombre"));
        m.setDescripcion(rs.getString("descripcion"));
        return m;
    }
}
