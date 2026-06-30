package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Acudiente {

    private Integer idAcudiente;
    private String tipoDocumento;
    private String numDocumento;
    private String nombreCompleto;
    private String parentesco;
    private String telefono;
    private String correo;

    public Integer getIdAcudiente() { return idAcudiente; }
    public void setIdAcudiente(Integer idAcudiente) { this.idAcudiente = idAcudiente; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumDocumento() { return numDocumento; }
    public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public static List<Acudiente> listar() {
        String sql = "SELECT * FROM acudiente ORDER BY id_acudiente ASC";
        List<Acudiente> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar acudientes: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Acudiente buscar(String criterio) {
        String sql = "SELECT * FROM acudiente WHERE CAST(id_acudiente AS VARCHAR) = ? "
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
            System.err.println("Error al buscar acudiente: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO acudiente (tipo_documento, num_documento, nombre_completo, parentesco, telefono, correo) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoDocumento);
            ps.setString(2, numDocumento);
            ps.setString(3, nombreCompleto);
            ps.setString(4, parentesco);
            ps.setString(5, telefono);
            ps.setString(6, correo);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar acudiente: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE acudiente SET tipo_documento=?, num_documento=?, nombre_completo=?, "
                   + "parentesco=?, telefono=?, correo=? WHERE id_acudiente=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoDocumento);
            ps.setString(2, numDocumento);
            ps.setString(3, nombreCompleto);
            ps.setString(4, parentesco);
            ps.setString(5, telefono);
            ps.setString(6, correo);
            ps.setInt(7, idAcudiente);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar acudiente: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM acudiente WHERE id_acudiente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAcudiente);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar acudiente: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Acudiente mapResultSet(ResultSet rs) throws SQLException {
        Acudiente a = new Acudiente();
        a.setIdAcudiente(rs.getInt("id_acudiente"));
        a.setTipoDocumento(rs.getString("tipo_documento"));
        a.setNumDocumento(rs.getString("num_documento"));
        a.setNombreCompleto(rs.getString("nombre_completo"));
        a.setParentesco(rs.getString("parentesco"));
        a.setTelefono(rs.getString("telefono"));
        a.setCorreo(rs.getString("correo"));
        return a;
    }
}
