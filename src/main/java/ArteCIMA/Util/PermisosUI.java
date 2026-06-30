package ArteCIMA.Util;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;
import javax.swing.JButton;
import javax.swing.JFrame;

public final class PermisosUI {

    private PermisosUI() {
    }

    public static boolean verificarAccesoModulo(JFrame frame, Modulo modulo) {
        if (!SesionUsuario.puedeAcceder(modulo)) {
            MensajesUI.error(frame, "Su rol no tiene acceso al módulo de " + modulo.getEtiqueta() + ".");
            return false;
        }
        return true;
    }

    public static void aplicarPermisosCrud(Modulo modulo, JButton btnInsertar, JButton btnEditar, JButton btnEliminar) {
        if (btnInsertar != null) {
            btnInsertar.setEnabled(SesionUsuario.puedeInsertar(modulo));
        }
        if (btnEditar != null) {
            btnEditar.setEnabled(SesionUsuario.puedeModificar(modulo));
        }
        if (btnEliminar != null) {
            btnEliminar.setEnabled(SesionUsuario.puedeEliminar(modulo));
        }
    }

    public static void configurarBotonMenu(JButton boton, Modulo modulo) {
        if (boton == null) {
            return;
        }
        boolean visible = SesionUsuario.puedeAcceder(modulo);
        boton.setVisible(visible);
        boton.setEnabled(visible);
    }
}
