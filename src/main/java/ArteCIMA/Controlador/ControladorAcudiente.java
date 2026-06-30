package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Acudiente;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorAcudiente extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.ACUDIENTES;
    }

    public boolean controlarAccion(ActionEvent evento, Acudiente acudiente) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = acudiente.insertar();
                ultimoMensaje = insertado ? "Acudiente registrado exitosamente." : "No se pudo registrar el acudiente.";
                return insertado;
            case "Modificar":
                boolean modificado = acudiente.modificar();
                ultimoMensaje = modificado ? "Acudiente actualizado correctamente." : "No se pudo actualizar el acudiente.";
                return modificado;
            case "Eliminar":
                boolean eliminado = acudiente.eliminar();
                ultimoMensaje = eliminado ? "Acudiente eliminado correctamente." : "No se pudo eliminar el acudiente.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Acudiente> listar() { return Acudiente.listar(); }
    public Acudiente buscar(String criterio) { return Acudiente.buscar(criterio); }
}
