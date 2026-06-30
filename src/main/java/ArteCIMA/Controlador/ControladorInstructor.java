package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Instructor;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorInstructor extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.INSTRUCTORES;
    }

    public boolean controlarAccion(ActionEvent evento, Instructor instructor) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                ultimoMensaje = instructor.insertarConValidacion();
                return ultimoMensaje.startsWith("Éxito");
            case "Modificar":
                boolean modificado = instructor.modificar();
                ultimoMensaje = modificado
                        ? "Instructor actualizado correctamente."
                        : "No se pudo actualizar el instructor.";
                return modificado;
            case "Eliminar":
                boolean eliminado = instructor.eliminar();
                ultimoMensaje = eliminado
                        ? "Instructor eliminado correctamente."
                        : "No se pudo eliminar el instructor.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Instructor> listar() {
        return Instructor.listar();
    }

    public List<Instructor> listar(String idInstructor, String numDocumento,
                                   String nombreCompleto, Boolean discapacidad) {
        return Instructor.listar(idInstructor, numDocumento, nombreCompleto, discapacidad);
    }

    public Instructor buscar(int id) {
        return Instructor.buscar(id);
    }
}
