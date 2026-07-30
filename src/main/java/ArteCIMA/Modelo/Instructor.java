package ArteCIMA.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Instructor {

    private Integer idInstructor;
    private String tipoDocumento;
    private String numDocumento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private Boolean discapacidad;
    private String tipoDiscapacidad;
    private String especialidadArtistica;
    private Double valorPorClase;

    public Instructor() {
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public String getEspecialidadArtistica() {
        return especialidadArtistica;
    }

    public void setEspecialidadArtistica(String especialidadArtistica) {
        this.especialidadArtistica = especialidadArtistica;
    }

    public Double getValorPorClase() {
        return valorPorClase;
    }

    public void setValorPorClase(Double valorPorClase) {
        this.valorPorClase = valorPorClase;
    }

    public static List<Instructor> listar() {
        return listar(null, null, null, null);
    }

    public static List<Instructor> listar(String idInstructor, String numDocumento,
                                          String nombreCompleto, Boolean discapacidad) {
        List<Instructor> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM instructor WHERE 1=1");
        List<String> parametrosTexto = new ArrayList<>();

        boolean isSearching = (numDocumento != null || nombreCompleto != null);

        if (isSearching) {
            sql.append(" AND (1=0");

            if (numDocumento != null && !numDocumento.isBlank()) {
                sql.append(" OR ").append(ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("num_documento")).append(" LIKE ?");
                parametrosTexto.add(numDocumento);
            }
            if (nombreCompleto != null && !nombreCompleto.isBlank()) {
                sql.append(" OR ").append(ArteCIMA.Util.TextoUtil.expresionSqlSinTildes("nombre_completo")).append(" LIKE ?");
                parametrosTexto.add(nombreCompleto);
            }
            sql.append(")");
        } else if (idInstructor != null && !idInstructor.isBlank()) {
            sql.append(" AND CAST(id_instructor AS TEXT) ILIKE ?");
            parametrosTexto.add(idInstructor);
        }

        if (discapacidad != null) {
            sql.append(" AND discapacidad = ?");
        }

        sql.append(" ORDER BY id_instructor");

        try (Connection conn = Conexion.getConexion()) {
            if (conn == null) {
                return lista;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int index = 1;

                for (String param : parametrosTexto) {
                    ps.setString(index++, ArteCIMA.Util.TextoUtil.patronBusqueda(param));
                }

                if (discapacidad != null) {
                    ps.setBoolean(index++, discapacidad);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar instructores: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public static Instructor buscar(int id) {
        String sql = "SELECT * FROM instructor WHERE id_instructor = ?";

        try (Connection conn = Conexion.getConexion()) {
            if (conn == null) {
                return null;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapResultSet(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar instructor: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Resuelve el id_instructor vinculado a un usuario de login
     * (mismo correo o mismo nombre completo que en la tabla instructor).
     */
    public static Integer resolverIdPorUsuario(String correo, String nombreCompleto) {
        if (correo != null && !correo.isBlank()) {
            String sql = "SELECT id_instructor FROM instructor WHERE lower(trim(correo)) = lower(trim(?))";
            try (Connection con = Conexion.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                if (con == null) {
                    return null;
                }
                ps.setString(1, correo.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_instructor");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al resolver instructor por correo: " + e.getMessage());
            }
        }

        if (nombreCompleto != null && !nombreCompleto.isBlank()) {
            String sql = "SELECT id_instructor FROM instructor WHERE lower(trim(nombre_completo)) = lower(trim(?))";
            try (Connection con = Conexion.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                if (con == null) {
                    return null;
                }
                ps.setString(1, nombreCompleto.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_instructor");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al resolver instructor por nombre: " + e.getMessage());
            }
        }

        return null;
    }

    public String insertarConValidacion() {
        if (numDocumento == null || numDocumento.trim().isEmpty()
                || nombreCompleto == null || nombreCompleto.trim().isEmpty()
                || especialidadArtistica == null || especialidadArtistica.trim().isEmpty()) {
            return "Error de Lógica: Documento, nombre y especialidad son campos obligatorios.";
        }

        if (correo != null && !correo.isEmpty() && !correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
            return "Error de Lógica: Formato de correo electrónico inválido.";
        }

        if (Boolean.TRUE.equals(discapacidad)
                && (tipoDiscapacidad == null || tipoDiscapacidad.trim().isEmpty())) {
            return "Error de Lógica: Si marcó Discapacidad 'Sí', debe especificar el tipo.";
        }

        if (valorPorClase == null || valorPorClase <= 0) {
            return "Error de Lógica: El valor por clase debe ser un número positivo mayor a cero.";
        }

        try {
            if (existeDocumento(numDocumento)) {
                return "Error de Integridad: Ya existe un instructor registrado con el número de documento "
                        + numDocumento + ".";
            }

            if (insertar()) {
                return "Éxito: Instructor guardado exitosamente.";
            }
            return "Error de Persistencia: No se pudo guardar el instructor (0 filas afectadas).";

        } catch (SQLException e) {
            return "Error de Base de Datos: Fallo al intentar conectar o ejecutar la consulta. Detalle: "
                    + e.getMessage();
        }
    }

    public boolean insertar() throws SQLException {
        String sql = "INSERT INTO instructor (tipo_documento, num_documento, nombre_completo, "
                     + "telefono, correo, discapacidad, tipo_discapacidad, "
                     + "especialidad_artistica, valor_por_clase) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipoDocumento);
            ps.setString(2, numDocumento);
            ps.setString(3, nombreCompleto);
            ps.setString(4, telefono);
            ps.setString(5, correo);
            ps.setBoolean(6, Boolean.TRUE.equals(discapacidad));
            ps.setString(7, tipoDiscapacidad);
            ps.setString(8, especialidadArtistica);
            ps.setDouble(9, valorPorClase);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error SQL al insertar instructor: " + e.getMessage());
            throw e;
        }
    }

    public boolean modificar() {
        String sql = "UPDATE instructor SET tipo_documento=?, num_documento=?, nombre_completo=?, "
                     + "telefono=?, correo=?, discapacidad=?, tipo_discapacidad=?, "
                     + "especialidad_artistica=?, valor_por_clase=? WHERE id_instructor=?";

        try (Connection conn = Conexion.getConexion()) {
            if (conn == null) {
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, tipoDocumento);
                ps.setString(2, numDocumento);
                ps.setString(3, nombreCompleto);
                ps.setString(4, telefono);
                ps.setString(5, correo);
                ps.setBoolean(6, Boolean.TRUE.equals(discapacidad));
                ps.setString(7, tipoDiscapacidad);
                ps.setString(8, especialidadArtistica);

                if (valorPorClase == null) {
                    ps.setNull(9, java.sql.Types.DOUBLE);
                } else {
                    ps.setDouble(9, valorPorClase);
                }

                ps.setInt(10, idInstructor);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al modificar instructor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM instructor WHERE id_instructor=?";

        try (Connection conn = Conexion.getConexion()) {
            if (conn == null) {
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idInstructor);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar instructor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean existeDocumento(String numDocumento) throws SQLException {
        String sql = "SELECT COUNT(*) FROM instructor WHERE num_documento = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numDocumento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private static Instructor mapResultSet(ResultSet rs) throws SQLException {
        Instructor i = new Instructor();
        i.setIdInstructor(rs.getInt("id_instructor"));
        i.setTipoDocumento(rs.getString("tipo_documento"));
        i.setNumDocumento(rs.getString("num_documento"));
        i.setNombreCompleto(rs.getString("nombre_completo"));
        i.setTelefono(rs.getString("telefono"));
        i.setCorreo(rs.getString("correo"));
        i.setDiscapacidad(rs.getBoolean("discapacidad"));
        i.setTipoDiscapacidad(rs.getString("tipo_discapacidad"));
        i.setEspecialidadArtistica(rs.getString("especialidad_artistica"));

        double valor = rs.getDouble("valor_por_clase");
        i.setValorPorClase(rs.wasNull() ? null : valor);

        return i;
    }
}
