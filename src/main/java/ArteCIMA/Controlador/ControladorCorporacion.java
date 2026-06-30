package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Corporacion;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorCorporacion extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.CORPORACIONES;
    }

    public boolean controlarAccion(ActionEvent evento, Corporacion corporacion) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = corporacion.insertar();
                ultimoMensaje = insertado ? "Corporación registrada exitosamente." : "No se pudo registrar la corporación.";
                return insertado;
            case "Modificar":
                boolean modificado = corporacion.modificar();
                ultimoMensaje = modificado ? "Corporación actualizada correctamente." : "No se pudo actualizar la corporación.";
                return modificado;
            case "Eliminar":
                boolean eliminado = corporacion.eliminar();
                ultimoMensaje = eliminado ? "Corporación eliminada correctamente." : "No se pudo eliminar la corporación.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Corporacion> listar() { return Corporacion.listar(); }
    public Corporacion buscar(String criterio) { return Corporacion.buscar(criterio); }
}
