package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.PagoInstructor;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControladorPagoInstructor extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.PAGOS_INSTRUCTOR;
    }

    public boolean controlarAccion(ActionEvent evento, PagoInstructor pago) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = pago.insertar();
                ultimoMensaje = insertado ? "Pago registrado exitosamente." : "No se pudo registrar el pago.";
                return insertado;
            case "Modificar":
                boolean modificado = pago.modificar();
                ultimoMensaje = modificado ? "Pago actualizado correctamente." : "No se pudo actualizar el pago.";
                return modificado;
            case "Eliminar":
                boolean eliminado = pago.eliminar();
                ultimoMensaje = eliminado ? "Pago eliminado correctamente." : "No se pudo eliminar el pago.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public List<PagoInstructor> listar() { return PagoInstructor.listar(); }
    public PagoInstructor buscar(String criterio) { return PagoInstructor.buscar(criterio); }
}
