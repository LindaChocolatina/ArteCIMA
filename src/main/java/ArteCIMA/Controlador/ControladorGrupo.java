package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Grupo;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorGrupo extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.GRUPOS;
    }

    public boolean controlarAccion(ActionEvent evento, Grupo grupo) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = grupo.insertar();
                ultimoMensaje = insertado ? "Grupo registrado exitosamente." : "No se pudo registrar el grupo.";
                return insertado;
            case "Modificar":
                boolean modificado = grupo.modificar();
                ultimoMensaje = modificado ? "Grupo actualizado correctamente." : "No se pudo actualizar el grupo.";
                return modificado;
            case "Eliminar":
                boolean eliminado = grupo.eliminar();
                ultimoMensaje = eliminado ? "Grupo eliminado correctamente." : "No se pudo eliminar el grupo.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Grupo> listar() { return Grupo.listar(); }
    public Grupo buscar(String criterio) { return Grupo.buscar(criterio); }
}
