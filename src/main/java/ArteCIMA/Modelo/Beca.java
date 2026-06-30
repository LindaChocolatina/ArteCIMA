package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Beca {

    private Integer idBeca;
    private String tipoBeca;
    private String entidadOtorgante;
    private String vigencia;
    private Integer idConvocatoria;

    public Integer getIdBeca() { return idBeca; }
    public void setIdBeca(Integer idBeca) { this.idBeca = idBeca; }

    public String getTipoBeca() { return tipoBeca; }
    public void setTipoBeca(String tipoBeca) { this.tipoBeca = tipoBeca; }

    public String getEntidadOtorgante() { return entidadOtorgante; }
    public void setEntidadOtorgante(String entidadOtorgante) { this.entidadOtorgante = entidadOtorgante; }

    public String getVigencia() { return vigencia; }
    public void setVigencia(String vigencia) { this.vigencia = vigencia; }

    public Integer getIdConvocatoria() { return idConvocatoria; }
    public void setIdConvocatoria(Integer idConvocatoria) { this.idConvocatoria = idConvocatoria; }

    public static List<Beca> listar() {
        String sql = "SELECT * FROM beca ORDER BY id_beca ASC";
        List<Beca> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar becas: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static Beca buscar(String criterio) {
        String sql = "SELECT * FROM beca WHERE CAST(id_beca AS VARCHAR) = ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("tipo_beca") + " LIKE ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("entidad_otorgante") + " LIKE ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String busqueda = criterio.trim();
            String patron = ArteCIMA.Util.TextoUtil.patronBusqueda(busqueda);
            ps.setString(1, busqueda);
            ps.setString(2, patron);
            ps.setString(3, patron);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar beca: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO beca (tipo_beca, entidad_otorgante, vigencia, id_convocatoria) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoBeca);
            ps.setString(2, entidadOtorgante);
            ps.setString(3, vigencia);
            if (idConvocatoria == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, idConvocatoria);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar beca: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE beca SET tipo_beca=?, entidad_otorgante=?, vigencia=?, id_convocatoria=? WHERE id_beca=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoBeca);
            ps.setString(2, entidadOtorgante);
            ps.setString(3, vigencia);
            if (idConvocatoria == null) {
                ps.setNull(4, java.sql.Types.INTEGER);
            } else {
                ps.setInt(4, idConvocatoria);
            }
            ps.setInt(5, idBeca);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar beca: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM beca WHERE id_beca = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idBeca);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar beca: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static Beca mapResultSet(ResultSet rs) throws SQLException {
        Beca b = new Beca();
        b.setIdBeca(rs.getInt("id_beca"));
        b.setTipoBeca(rs.getString("tipo_beca"));
        b.setEntidadOtorgante(rs.getString("entidad_otorgante"));
        b.setVigencia(rs.getString("vigencia"));
        int idConv = rs.getInt("id_convocatoria");
        b.setIdConvocatoria(rs.wasNull() ? null : idConv);
        return b;
    }
}
