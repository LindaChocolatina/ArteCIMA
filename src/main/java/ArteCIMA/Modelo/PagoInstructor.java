package ArteCIMA.Modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagoInstructor {

    private Integer idPago;
    private Integer idInstructor;
    private Date fechaPago;
    private BigDecimal monto;
    private String concepto;
    private Integer idMovimiento;

    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }

    public Integer getIdInstructor() { return idInstructor; }
    public void setIdInstructor(Integer idInstructor) { this.idInstructor = idInstructor; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public Integer getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(Integer idMovimiento) { this.idMovimiento = idMovimiento; }

    public static List<PagoInstructor> listar() {
        String sql = "SELECT * FROM pago_instructor ORDER BY fecha_pago DESC, id_pago ASC";
        List<PagoInstructor> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar pagos: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }

    public static PagoInstructor buscar(String criterio) {
        String sql = "SELECT * FROM pago_instructor WHERE CAST(id_pago AS VARCHAR) = ? "
                   + "OR CAST(id_instructor AS VARCHAR) = ? OR "
                   + ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("concepto") + " LIKE ?";
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
            System.err.println("Error al buscar pago: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar() {
        String sql = "INSERT INTO pago_instructor (id_instructor, fecha_pago, monto, concepto, id_movimiento) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInstructor);
            ps.setDate(2, fechaPago);
            if (monto == null) {
                ps.setNull(3, java.sql.Types.NUMERIC);
            } else {
                ps.setBigDecimal(3, monto);
            }
            ps.setString(4, concepto);
            if (idMovimiento == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, idMovimiento);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar pago: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE pago_instructor SET id_instructor=?, fecha_pago=?, monto=?, concepto=?, "
                   + "id_movimiento=? WHERE id_pago=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInstructor);
            ps.setDate(2, fechaPago);
            if (monto == null) {
                ps.setNull(3, java.sql.Types.NUMERIC);
            } else {
                ps.setBigDecimal(3, monto);
            }
            ps.setString(4, concepto);
            if (idMovimiento == null) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, idMovimiento);
            }
            ps.setInt(6, idPago);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al modificar pago: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM pago_instructor WHERE id_pago = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPago);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar pago: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private static PagoInstructor mapResultSet(ResultSet rs) throws SQLException {
        PagoInstructor p = new PagoInstructor();
        p.setIdPago(rs.getInt("id_pago"));
        p.setIdInstructor(rs.getInt("id_instructor"));
        p.setFechaPago(rs.getDate("fecha_pago"));
        p.setMonto(rs.getBigDecimal("monto"));
        p.setConcepto(rs.getString("concepto"));
        int idMov = rs.getInt("id_movimiento");
        p.setIdMovimiento(rs.wasNull() ? null : idMov);
        return p;
    }
}
