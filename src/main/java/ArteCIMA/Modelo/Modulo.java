package ArteCIMA.Modelo;

public enum Modulo {

    ESTUDIANTES("Estudiantes"),
    INSTRUCTORES("Instructores"),
    TALLERES("Talleres"),
    GRUPOS("Grupos"),
    ACUDIENTES("Acudientes"),
    BECAS("Becas"),
    ASISTENCIAS("Asistencias"),
    METODOS("Métodos"),
    CORPORACIONES("Corporaciones"),
    CONVOCATORIAS("Convocatorias"),
    ALIANZAS("Alianzas"),
    MOVIMIENTOS("Movimientos contables"),
    PAGOS_INSTRUCTOR("Pagos a instructores"),
    REGISTRAR_USUARIO("Registro de usuarios"),
    REPORTES("Reportes");

    private final String etiqueta;

    Modulo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
