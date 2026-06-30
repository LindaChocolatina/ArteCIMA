package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Alianza;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorAlianza extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.ALIANZAS;
    }

    public boolean controlarAccion(ActionEvent evento, Alianza alianza) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = alianza.insertar();
                ultimoMensaje = insertado ? "Alianza registrada exitosamente." : "No se pudo registrar la alianza.";
                return insertado;
            case "Modificar":
                boolean modificado = alianza.modificar();
                ultimoMensaje = modificado ? "Alianza actualizada correctamente." : "No se pudo actualizar la alianza.";
                return modificado;
            case "Eliminar":
                boolean eliminado = alianza.eliminar();
                ultimoMensaje = eliminado ? "Alianza eliminada correctamente." : "No se pudo eliminar la alianza.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Alianza> listar() { return Alianza.listar(); }
    public Alianza buscar(String criterio) { return Alianza.buscar(criterio); }
}
