package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Beca;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorBeca extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.BECAS;
    }

    public boolean controlarAccion(ActionEvent evento, Beca beca) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = beca.insertar();
                ultimoMensaje = insertado ? "Beca registrada exitosamente." : "No se pudo registrar la beca.";
                return insertado;
            case "Modificar":
                boolean modificado = beca.modificar();
                ultimoMensaje = modificado ? "Beca actualizada correctamente." : "No se pudo actualizar la beca.";
                return modificado;
            case "Eliminar":
                boolean eliminado = beca.eliminar();
                ultimoMensaje = eliminado ? "Beca eliminada correctamente." : "No se pudo eliminar la beca.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Beca> listar() { return Beca.listar(); }
    public Beca buscar(String criterio) { return Beca.buscar(criterio); }
}
