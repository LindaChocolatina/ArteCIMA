package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Metodo;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorMetodo extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.METODOS;
    }

    public boolean controlarAccion(ActionEvent evento, Metodo metodo) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = metodo.insertar();
                ultimoMensaje = insertado ? "Método registrado exitosamente." : "No se pudo registrar el método.";
                return insertado;
            case "Modificar":
                boolean modificado = metodo.modificar();
                ultimoMensaje = modificado ? "Método actualizado correctamente." : "No se pudo actualizar el método.";
                return modificado;
            case "Eliminar":
                boolean eliminado = metodo.eliminar();
                ultimoMensaje = eliminado ? "Método eliminado correctamente." : "No se pudo eliminar el método.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Metodo> listar() { return Metodo.listar(); }
    public Metodo buscar(String criterio) { return Metodo.buscar(criterio); }
}
