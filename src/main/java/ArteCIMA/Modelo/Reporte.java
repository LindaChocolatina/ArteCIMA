package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reporte {

    public enum Tipo {
        ASISTENCIA_GRUPO("Asistencia por grupo y período"),
        BECAS_ACTIVAS("Becas activas"),
        PAGOS_INSTRUCTOR("Pagos a instructores"),
        MOVIMIENTOS("Movimientos contables"),
        ESTUDIANTES_TALLER("Estudiantes por taller");

        private final String etiqueta;

        Tipo(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }

    public static class Filtros {
        private Integer idGrupo;
        private Integer idTaller;
        private Date fechaDesde;
        private Date fechaHasta;

        public Integer getIdGrupo() { return idGrupo; }
        public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

        public Integer getIdTaller() { return idTaller; }
        public void setIdTaller(Integer idTaller) { this.idTaller = idTaller; }

        public Date getFechaDesde() { return fechaDesde; }
        public void setFechaDesde(Date fechaDesde) { this.fechaDesde = fechaDesde; }

        public Date getFechaHasta() { return fechaHasta; }
        public void setFechaHasta(Date fechaHasta) { this.fechaHasta = fechaHasta; }
    }

    public static class Resultado {
        private final String titulo;
        private final String[] columnas;
        private final List<String[]> filas;

        public Resultado(String titulo, String[] columnas, List<String[]> filas) {
            this.titulo = titulo;
            this.columnas = columnas;
            this.filas = filas;
        }

        public String getTitulo() { return titulo; }
        public String[] getColumnas() { return columnas; }
        public List<String[]> getFilas() { return filas; }

        public boolean estaVacio() {
            return filas == null || filas.isEmpty();
        }
    }

    public static Resultado generar(Tipo tipo, Filtros filtros) {
        if (filtros == null) {
            filtros = new Filtros();
        }
        switch (tipo) {
            case ASISTENCIA_GRUPO:
                return asistenciaPorGrupo(filtros);
            case BECAS_ACTIVAS:
                return becasActivas();
            case PAGOS_INSTRUCTOR:
                return pagosInstructor(filtros);
            case MOVIMIENTOS:
                return movimientosContables(filtros);
            case ESTUDIANTES_TALLER:
                return estudiantesPorTaller(filtros);
            default:
                return new Resultado("Sin datos", new String[0], new ArrayList<>());
        }
    }

    private static Resultado asistenciaPorGrupo(Filtros filtros) {
        Integer idInstructor = idInstructorSiAplica();
        String sql = "SELECT e.nombre_completo, g.nombre AS grupo, a.fecha, "
                + "CASE WHEN a.presente THEN 'Sí' ELSE 'No' END AS presente "
                + "FROM asistencia a "
                + "JOIN estudiante e ON a.id_estudiante = e.id_estudiante "
                + "JOIN grupo g ON a.id_grupo = g.id_grupo "
                + "JOIN taller t ON g.id_taller = t.id_taller "
                + "WHERE (? IS NULL OR a.id_grupo = ?) "
                + "AND (? IS NULL OR a.fecha >= ?) "
                + "AND (? IS NULL OR a.fecha <= ?) "
                + "AND (? IS NULL OR t.id_instructor = ?) "
                + "ORDER BY a.fecha DESC, g.nombre, e.nombre_completo";

        String[] columnas = {"Estudiante", "Grupo", "Fecha", "Presente"};
        List<String[]> filas = new ArrayList<>();

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                return new Resultado("Asistencia por grupo", columnas, filas);
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                bindEnteroOpcional(ps, 1, 2, filtros.getIdGrupo());
                bindFechaOpcional(ps, 3, 4, filtros.getFechaDesde());
                bindFechaOpcional(ps, 5, 6, filtros.getFechaHasta());
                bindEnteroOpcional(ps, 7, 8, idInstructor);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        filas.add(new String[]{
                            rs.getString("nombre_completo"),
                            rs.getString("grupo"),
                            rs.getDate("fecha").toString(),
                            rs.getString("presente")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en reporte de asistencia: " + ex.getMessage());
        }
        return new Resultado("Asistencia por grupo y período", columnas, filas);
    }

    private static Resultado becasActivas() {
        String sql = "SELECT b.id_beca, b.tipo_beca, b.entidad_otorgante, b.vigencia, "
                + "COALESCE(c.nombre, '—') AS convocatoria "
                + "FROM beca b "
                + "LEFT JOIN convocatoria c ON b.id_convocatoria = c.id_convocatoria "
                + "ORDER BY b.id_beca";

        String[] columnas = {"ID", "Tipo", "Entidad otorgante", "Vigencia", "Convocatoria"};
        List<String[]> filas = new ArrayList<>();

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                return new Resultado("Becas activas", columnas, filas);
            }
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    filas.add(new String[]{
                        String.valueOf(rs.getInt("id_beca")),
                        nuloVacio(rs.getString("tipo_beca")),
                        nuloVacio(rs.getString("entidad_otorgante")),
                        nuloVacio(rs.getString("vigencia")),
                        rs.getString("convocatoria")
                    });
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en reporte de becas: " + ex.getMessage());
        }
        return new Resultado("Becas activas", columnas, filas);
    }

    private static Resultado pagosInstructor(Filtros filtros) {
        String sql = "SELECT p.id_pago, i.nombre_completo, p.fecha_pago, p.monto, "
                + "COALESCE(p.concepto, '—') AS concepto "
                + "FROM pago_instructor p "
                + "JOIN instructor i ON p.id_instructor = i.id_instructor "
                + "WHERE (? IS NULL OR p.fecha_pago >= ?) "
                + "AND (? IS NULL OR p.fecha_pago <= ?) "
                + "ORDER BY p.fecha_pago DESC, p.id_pago";

        String[] columnas = {"ID", "Instructor", "Fecha pago", "Monto", "Concepto"};
        List<String[]> filas = new ArrayList<>();

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                return new Resultado("Pagos a instructores", columnas, filas);
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                bindFechaOpcional(ps, 1, 2, filtros.getFechaDesde());
                bindFechaOpcional(ps, 3, 4, filtros.getFechaHasta());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        filas.add(new String[]{
                            String.valueOf(rs.getInt("id_pago")),
                            rs.getString("nombre_completo"),
                            rs.getDate("fecha_pago").toString(),
                            rs.getBigDecimal("monto") != null ? rs.getBigDecimal("monto").toPlainString() : "—",
                            rs.getString("concepto")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en reporte de pagos: " + ex.getMessage());
        }
        return new Resultado("Pagos a instructores", columnas, filas);
    }

    private static Resultado movimientosContables(Filtros filtros) {
        String sql = "SELECT m.id_movimiento, m.tipo_movimiento, m.concepto, m.monto, m.fecha, "
                + "COALESCE(m.fuente, '—') AS fuente, COALESCE(c.nombre, '—') AS corporacion "
                + "FROM movimiento_contable m "
                + "LEFT JOIN corporacion c ON m.id_corporacion = c.id_corporacion "
                + "WHERE (? IS NULL OR m.fecha >= ?) "
                + "AND (? IS NULL OR m.fecha <= ?) "
                + "ORDER BY m.fecha DESC, m.id_movimiento";

        String[] columnas = {"ID", "Tipo", "Concepto", "Monto", "Fecha", "Fuente", "Corporación"};
        List<String[]> filas = new ArrayList<>();

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                return new Resultado("Movimientos contables", columnas, filas);
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                bindFechaOpcional(ps, 1, 2, filtros.getFechaDesde());
                bindFechaOpcional(ps, 3, 4, filtros.getFechaHasta());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        filas.add(new String[]{
                            String.valueOf(rs.getInt("id_movimiento")),
                            rs.getString("tipo_movimiento"),
                            nuloVacio(rs.getString("concepto")),
                            rs.getBigDecimal("monto") != null ? rs.getBigDecimal("monto").toPlainString() : "—",
                            rs.getDate("fecha").toString(),
                            rs.getString("fuente"),
                            rs.getString("corporacion")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en reporte de movimientos: " + ex.getMessage());
        }
        return new Resultado("Movimientos contables", columnas, filas);
    }

    private static Resultado estudiantesPorTaller(Filtros filtros) {
        Integer idInstructor = idInstructorSiAplica();
        String sql = "SELECT t.nombre AS taller, t.tipo_arte, g.nombre AS grupo, "
                + "e.nombre_completo, e.num_documento, COALESCE(e.tipo_beneficio, '—') AS beneficio "
                + "FROM estudiante e "
                + "JOIN grupo g ON e.id_grupo = g.id_grupo "
                + "JOIN taller t ON g.id_taller = t.id_taller "
                + "WHERE (? IS NULL OR t.id_taller = ?) "
                + "AND (? IS NULL OR t.id_instructor = ?) "
                + "ORDER BY t.nombre, g.nombre, e.nombre_completo";

        String[] columnas = {"Taller", "Tipo arte", "Grupo", "Estudiante", "Documento", "Beneficio"};
        List<String[]> filas = new ArrayList<>();

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                return new Resultado("Estudiantes por taller", columnas, filas);
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                bindEnteroOpcional(ps, 1, 2, filtros.getIdTaller());
                bindEnteroOpcional(ps, 3, 4, idInstructor);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        filas.add(new String[]{
                            rs.getString("taller"),
                            nuloVacio(rs.getString("tipo_arte")),
                            rs.getString("grupo"),
                            rs.getString("nombre_completo"),
                            rs.getString("num_documento"),
                            rs.getString("beneficio")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error en reporte de estudiantes: " + ex.getMessage());
        }
        return new Resultado("Estudiantes por taller", columnas, filas);
    }

    /** Si hay instructor en sesión, los reportes académicos quedan limitados a sus talleres. */
    private static Integer idInstructorSiAplica() {
        return SesionUsuario.esInstructor() ? SesionUsuario.getIdInstructor() : null;
    }

    private static void bindEnteroOpcional(PreparedStatement ps, int idxNull, int idxValor, Integer valor)
            throws SQLException {
        if (valor == null) {
            ps.setNull(idxNull, java.sql.Types.INTEGER);
            ps.setNull(idxValor, java.sql.Types.INTEGER);
        } else {
            ps.setInt(idxNull, valor);
            ps.setInt(idxValor, valor);
        }
    }

    private static void bindFechaOpcional(PreparedStatement ps, int idxNull, int idxValor, Date valor)
            throws SQLException {
        if (valor == null) {
            ps.setNull(idxNull, java.sql.Types.DATE);
            ps.setNull(idxValor, java.sql.Types.DATE);
        } else {
            ps.setDate(idxNull, valor);
            ps.setDate(idxValor, valor);
        }
    }

    private static String nuloVacio(String valor) {
        return valor == null || valor.isBlank() ? "—" : valor;
    }
}
