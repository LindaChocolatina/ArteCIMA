package ArteCIMA.Util;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

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
        boolean puedeInsertar = SesionUsuario.puedeInsertar(modulo);
        boolean puedeModificar = SesionUsuario.puedeModificar(modulo);
        boolean puedeEliminar = SesionUsuario.puedeEliminar(modulo);

        if (btnInsertar != null) {
            btnInsertar.setEnabled(puedeInsertar);
            btnInsertar.setVisible(puedeInsertar);
        }
        if (btnEditar != null) {
            btnEditar.setEnabled(puedeModificar);
            btnEditar.setVisible(puedeModificar);
        }
        if (btnEliminar != null) {
            btnEliminar.setEnabled(puedeEliminar);
            btnEliminar.setVisible(puedeEliminar);
        }
    }

    /**
     * En modo solo lectura bloquea text fields y combos (excepto el campo de búsqueda).
     */
    public static void aplicarModoCampos(Container contenedor, Modulo modulo, JTextField... excluir) {
        if (contenedor == null || modulo == null) {
            return;
        }
        boolean editable = SesionUsuario.puedeInsertar(modulo) || SesionUsuario.puedeModificar(modulo);
        aplicarEditabilidad(contenedor, editable, excluir);
    }

    public static boolean puedeEditarCampos(Modulo modulo) {
        return SesionUsuario.puedeInsertar(modulo) || SesionUsuario.puedeModificar(modulo);
    }

    private static void aplicarEditabilidad(Container contenedor, boolean editable, JTextField... excluir) {
        for (Component c : contenedor.getComponents()) {
            if (c instanceof JTextField) {
                JTextField campo = (JTextField) c;
                if (estaExcluido(campo, excluir) || Boolean.TRUE.equals(campo.getClientProperty("arteCIMA.esBuscar"))) {
                    continue;
                }
                campo.setEditable(editable);
                campo.setEnabled(editable);
            } else if (c instanceof JComboBox) {
                c.setEnabled(editable);
            } else if (c instanceof Container) {
                aplicarEditabilidad((Container) c, editable, excluir);
            }
        }
    }

    private static boolean estaExcluido(JTextField campo, JTextField... excluir) {
        if (excluir == null) {
            return false;
        }
        for (JTextField e : excluir) {
            if (e == campo) {
                return true;
            }
        }
        return false;
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
