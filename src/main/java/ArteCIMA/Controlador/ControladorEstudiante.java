package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Estudiante;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorEstudiante extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.ESTUDIANTES;
    }

    public boolean controlarAccion(ActionEvent evento, Estudiante estudiante) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = estudiante.insertar();
                ultimoMensaje = insertado
                        ? "Estudiante registrado exitosamente."
                        : "No se pudo registrar el estudiante.";
                return insertado;
            case "Modificar":
                boolean modificado = estudiante.modificar();
                ultimoMensaje = modificado
                        ? "Estudiante actualizado correctamente."
                        : "No se pudo actualizar el estudiante.";
                return modificado;
            case "Eliminar":
                boolean eliminado = estudiante.eliminar();
                ultimoMensaje = eliminado
                        ? "Estudiante eliminado correctamente."
                        : "No se pudo eliminar el estudiante.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Estudiante> listar() {
        return Estudiante.listar();
    }

    public Estudiante buscar(String criterio) {
        return Estudiante.buscar(criterio);
    }
}
