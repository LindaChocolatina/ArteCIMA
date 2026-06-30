package ArteCIMA.Modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimientoContable {

    private Integer idMovimiento;
    private String tipoMovimiento;
    private String concepto;
    private BigDecimal monto;
    private Date fecha;
    private String fuente;
    private Integer idCorporacion;

    public Integer getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Integer idMovimiento) { this.idMovimiento = idMovimiento; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getFuente() { return fuente; }
    public void setFuente(String fuente) { this.fuente = fuente; }

    public Integer getIdCorporacion() { return idCorporacion; }
    public void setIdCorporacion(Integer idCorporacion) { this.idCorporacion = idCorporacion; }

    public static List<MovimientoContable> listar() {
        String sql = "SELECT * FROM movimiento_contable ORDER BY fecha DESC, id_movimiento ASC";
        List<MovimientoContable> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar movimientos: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static MovimientoContable buscar(String criterio) {
        String sql = "SELECT * FROM movimiento_contable WHERE CAST(id_movimiento AS VARCHAR) = ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("concepto") + " LIKE ?";
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
            System.err.println("Error al buscar movimiento: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO movimiento_contable (tipo_movimiento, concepto, monto, fecha, fuente, id_corporacion) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoMovimiento);
            ps.setString(2, concepto);
            if (monto == null) {
                ps.setNull(3, java.sql.Types.NUMERIC);
            } else {
                ps.setBigDecimal(3, monto);
            }
            ps.setDate(4, fecha);
            ps.setString(5, fuente);
            if (idCorporacion == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, idCorporacion);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar movimiento: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE movimiento_contable SET tipo_movimiento=?, concepto=?, monto=?, fecha=?, "
                   + "fuente=?, id_corporacion=? WHERE id_movimiento=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoMovimiento);
            ps.setString(2, concepto);
            if (monto == null) {
                ps.setNull(3, java.sql.Types.NUMERIC);
            } else {
                ps.setBigDecimal(3, monto);
            }
            ps.setDate(4, fecha);
            ps.setString(5, fuente);
            if (idCorporacion == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, idCorporacion);
            }
            ps.setInt(7, idMovimiento);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar movimiento: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM movimiento_contable WHERE id_movimiento = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMovimiento);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar movimiento: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static MovimientoContable mapResultSet(ResultSet rs) throws SQLException {
        MovimientoContable m = new MovimientoContable();
        m.setIdMovimiento(rs.getInt("id_movimiento"));
        m.setTipoMovimiento(rs.getString("tipo_movimiento"));
        m.setConcepto(rs.getString("concepto"));
        m.setMonto(rs.getBigDecimal("monto"));
        m.setFecha(rs.getDate("fecha"));
        m.setFuente(rs.getString("fuente"));
        int idCorp = rs.getInt("id_corporacion");
        m.setIdCorporacion(rs.wasNull() ? null : idCorp);
        return m;
    }
}
