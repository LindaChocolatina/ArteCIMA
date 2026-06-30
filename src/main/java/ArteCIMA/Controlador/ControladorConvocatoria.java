package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Convocatoria;
import ArteCIMA.Modelo.Modulo;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorConvocatoria extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.CONVOCATORIAS;
    }

    public boolean controlarAccion(ActionEvent evento, Convocatoria convocatoria) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = convocatoria.insertar();
                ultimoMensaje = insertado ? "Convocatoria registrada exitosamente." : "No se pudo registrar la convocatoria.";
                return insertado;
            case "Modificar":
                boolean modificado = convocatoria.modificar();
                ultimoMensaje = modificado ? "Convocatoria actualizada correctamente." : "No se pudo actualizar la convocatoria.";
                return modificado;
            case "Eliminar":
                boolean eliminado = convocatoria.eliminar();
                ultimoMensaje = eliminado ? "Convocatoria eliminada correctamente." : "No se pudo eliminar la convocatoria.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<Convocatoria> listar() { return Convocatoria.listar(); }
    public Convocatoria buscar(String criterio) { return Convocatoria.buscar(criterio); }
}
