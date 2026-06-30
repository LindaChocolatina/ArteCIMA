package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.Taller;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorTaller extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.TALLERES;
    }

    public boolean controlarAccion(ActionEvent evento, Taller taller) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = taller.insertar();
                ultimoMensaje = insertado
                        ? "Taller registrado exitosamente."
                        : "No se pudo registrar el taller. " + taller.getUltimoError();
                return insertado;
            case "Modificar":
                boolean modificado = taller.modificar();
                ultimoMensaje = modificado
                        ? "Taller actualizado correctamente."
                        : "No se pudo actualizar el taller. " + taller.getUltimoError();
                return modificado;
            case "Eliminar":
                boolean eliminado = taller.eliminar();
                ultimoMensaje = eliminado
                        ? "Taller eliminado correctamente."
                        : "No se pudo eliminar el taller. " + taller.getUltimoError();
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Taller> listar() {
        return Taller.listar();
    }

    public Taller buscar(String criterio) {
        return Taller.buscar(criterio);
    }

    public Taller buscar(int id) {
        return Taller.buscar(id);
    }
}
