package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.MovimientoContable;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorMovimientoContable extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.MOVIMIENTOS;
    }

    public boolean controlarAccion(ActionEvent evento, MovimientoContable movimiento) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = movimiento.insertar();
                ultimoMensaje = insertado ? "Movimiento registrado exitosamente." : "No se pudo registrar el movimiento.";
                return insertado;
            case "Modificar":
                boolean modificado = movimiento.modificar();
                ultimoMensaje = modificado ? "Movimiento actualizado correctamente." : "No se pudo actualizar el movimiento.";
                return modificado;
            case "Eliminar":
                boolean eliminado = movimiento.eliminar();
                ultimoMensaje = eliminado ? "Movimiento eliminado correctamente." : "No se pudo eliminar el movimiento.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<MovimientoContable> listar() { return MovimientoContable.listar(); }
    public MovimientoContable buscar(String criterio) { return MovimientoContable.buscar(criterio); }
}
