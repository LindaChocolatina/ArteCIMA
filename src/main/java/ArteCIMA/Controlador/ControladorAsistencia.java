package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Asistencia;
import ArteCIMA.Modelo.Grupo;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;
import java.sql.Date;
import java.util.List;

public class ControladorAsistencia extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.ASISTENCIAS;
    }

    public List<Asistencia.FilaDia> listarParaFecha(Date fecha) {
        if (!SesionUsuario.puedeAcceder(getModulo())) {
            ultimoMensaje = "No tiene acceso al módulo de asistencias.";
            return List.of();
        }
        return Asistencia.listarFilasParaFecha(fecha);
    }

    public boolean guardarDia(Date fecha, List<Asistencia.FilaDia> filas) {
        if (!SesionUsuario.puedeInsertar(getModulo()) && !SesionUsuario.puedeModificar(getModulo())) {
            ultimoMensaje = "No tiene permiso para registrar asistencias.";
            return false;
        }
        if (fecha == null) {
            ultimoMensaje = "Debe seleccionar una fecha.";
            return false;
        }
        if (filas == null || filas.isEmpty()) {
            ultimoMensaje = "No hay estudiantes para registrar en esta fecha.";
            return false;
        }

        for (Asistencia.FilaDia fila : filas) {
            if (!Grupo.perteneceAlInstructorSesion(fila.getIdGrupo())) {
                ultimoMensaje = "Solo puede registrar asistencia de sus propios grupos.";
                return false;
            }
        }

        StringBuilder error = new StringBuilder();
        int guardados = Asistencia.guardarLote(filas, fecha, error);
        if (guardados == filas.size()) {
            // Verificar que quedaron persistidos
            List<Asistencia.FilaDia> verificacion = Asistencia.listarFilasParaFecha(fecha);
            long conRegistro = verificacion.stream().filter(f -> f.getIdAsistencia() != null).count();
            if (conRegistro < filas.size()) {
                ultimoMensaje = "Se intentó guardar, pero solo " + conRegistro
                        + " de " + filas.size() + " quedaron registrados en la base de datos.";
                return false;
            }
            ultimoMensaje = "Asistencia del " + fecha + " guardada correctamente ("
                    + guardados + " estudiante(s)).";
            return true;
        }
        if (guardados > 0) {
            ultimoMensaje = "Se guardaron " + guardados + " de " + filas.size() + " registros."
                    + (error.length() > 0 ? " Detalle: " + error : "");
            return false;
        }
        ultimoMensaje = "No se pudo guardar la asistencia."
                + (error.length() > 0 ? " " + error : " Revise la conexión a la base de datos.");
        return false;
    }

    public List<Asistencia> listar() {
        return Asistencia.listar();
    }

    public Asistencia buscar(String criterio) {
        return Asistencia.buscar(criterio);
    }
}
