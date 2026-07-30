package ArteCIMA.Modelo;

public class SesionUsuario {

    private enum NivelPermiso {
        NINGUNO, LECTURA, ESCRITURA
    }

    private static String nombreRol;
    private static String nombreCompleto;
    private static String correo;
    private static Integer idInstructor;

    public static void iniciarSesion(String rol, String nombreCompleto) {
        iniciarSesion(rol, nombreCompleto, null, null);
    }

    public static void iniciarSesion(String rol, String nombreCompleto, String correo, Integer idInstructor) {
        SesionUsuario.nombreRol = rol;
        SesionUsuario.nombreCompleto = nombreCompleto;
        SesionUsuario.correo = correo;
        SesionUsuario.idInstructor = idInstructor;
    }

    public static void cerrarSesion() {
        SesionUsuario.nombreRol = null;
        SesionUsuario.nombreCompleto = null;
        SesionUsuario.correo = null;
        SesionUsuario.idInstructor = null;
    }

    public static String getNombreRol() {
        return nombreRol;
    }

    public static String getNombreCompleto() {
        return nombreCompleto;
    }

    public static String getCorreo() {
        return correo;
    }

    public static Integer getIdInstructor() {
        return idInstructor;
    }

    public static boolean esInstructor() {
        return esRol("Instructor");
    }

    public static boolean haySesionActiva() {
        return nombreRol != null && !nombreRol.isBlank();
    }

    public static boolean puedeAcceder(Modulo modulo) {
        return nivelPermiso(modulo) != NivelPermiso.NINGUNO;
    }

    public static boolean puedeInsertar(Modulo modulo) {
        return nivelPermiso(modulo) == NivelPermiso.ESCRITURA;
    }

    public static boolean puedeModificar(Modulo modulo) {
        return nivelPermiso(modulo) == NivelPermiso.ESCRITURA;
    }

    public static boolean puedeEliminar(Modulo modulo) {
        return nivelPermiso(modulo) == NivelPermiso.ESCRITURA;
    }

    public static boolean puedeRegistrarUsuarios() {
        return esRol("Administrador");
    }

    /** @deprecated Usar {@link #puedeModificar(Modulo#INSTRUCTORES)} */
    @Deprecated
    public static boolean puedeModificarInstructores() {
        return puedeModificar(Modulo.INSTRUCTORES);
    }

    private static NivelPermiso nivelPermiso(Modulo modulo) {
        if (!haySesionActiva()) {
            return NivelPermiso.NINGUNO;
        }

        if (esRol("Administrador")) {
            return NivelPermiso.ESCRITURA;
        }

        if (esRol("Coordinador")) {
            return nivelCoordinador(modulo);
        }

        if (esRol("Instructor")) {
            return nivelInstructor(modulo);
        }

        if (esRol("Administrativo")) {
            return nivelAdministrativo(modulo);
        }

        if (esRol("Contabilidad")) {
            return nivelContabilidad(modulo);
        }

        if (esRol("Auxiliar")) {
            return nivelAuxiliar(modulo);
        }

        return NivelPermiso.NINGUNO;
    }

    private static NivelPermiso nivelCoordinador(Modulo modulo) {
        switch (modulo) {
            case MOVIMIENTOS:
            case PAGOS_INSTRUCTOR:
                return NivelPermiso.LECTURA;
            case REGISTRAR_USUARIO:
                return NivelPermiso.NINGUNO;
            default:
                return NivelPermiso.ESCRITURA;
        }
    }

    private static NivelPermiso nivelInstructor(Modulo modulo) {
        switch (modulo) {
            case ASISTENCIAS:
                return NivelPermiso.ESCRITURA;
            case GRUPOS:
            case ESTUDIANTES:
            case TALLERES:
            case ACUDIENTES:
            case INSTRUCTORES:
            case REPORTES:
                return NivelPermiso.LECTURA;
            default:
                return NivelPermiso.NINGUNO;
        }
    }

    private static NivelPermiso nivelAdministrativo(Modulo modulo) {
        switch (modulo) {
            case ESTUDIANTES:
            case ACUDIENTES:
            case BECAS:
            case TALLERES:
            case GRUPOS:
            case CORPORACIONES:
            case CONVOCATORIAS:
            case METODOS:
            case ALIANZAS:
            case REPORTES:
                return NivelPermiso.ESCRITURA;
            case INSTRUCTORES:
            case ASISTENCIAS:
                return NivelPermiso.LECTURA;
            default:
                return NivelPermiso.NINGUNO;
        }
    }

    private static NivelPermiso nivelContabilidad(Modulo modulo) {
        switch (modulo) {
            case MOVIMIENTOS:
            case PAGOS_INSTRUCTOR:
            case REPORTES:
                return NivelPermiso.ESCRITURA;
            case ESTUDIANTES:
            case INSTRUCTORES:
            case TALLERES:
            case GRUPOS:
            case BECAS:
            case CORPORACIONES:
            case ACUDIENTES:
            case ASISTENCIAS:
                return NivelPermiso.LECTURA;
            default:
                return NivelPermiso.NINGUNO;
        }
    }

    private static NivelPermiso nivelAuxiliar(Modulo modulo) {
        switch (modulo) {
            case ESTUDIANTES:
            case ACUDIENTES:
            case TALLERES:
            case GRUPOS:
            case BECAS:
            case ASISTENCIAS:
            case INSTRUCTORES:
            case REPORTES:
                return NivelPermiso.LECTURA;
            default:
                return NivelPermiso.NINGUNO;
        }
    }

    private static boolean esRol(String rol) {
        return rol != null && rol.equalsIgnoreCase(nombreRol);
    }
}
