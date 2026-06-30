package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Asistencia;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorAsistencia extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.ASISTENCIAS;
    }

    public boolean controlarAccion(ActionEvent evento, Asistencia asistencia) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = asistencia.insertar();
                ultimoMensaje = insertado ? "Asistencia registrada exitosamente." : "No se pudo registrar la asistencia.";
                return insertado;
            case "Modificar":
                boolean modificado = asistencia.modificar();
                ultimoMensaje = modificado ? "Asistencia actualizada correctamente." : "No se pudo actualizar la asistencia.";
                return modificado;
            case "Eliminar":
                boolean eliminado = asistencia.eliminar();
                ultimoMensaje = eliminado ? "Asistencia eliminada correctamente." : "No se pudo eliminar la asistencia.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Asistencia> listar() { return Asistencia.listar(); }
    public Asistencia buscar(String criterio) { return Asistencia.buscar(criterio); }
}
