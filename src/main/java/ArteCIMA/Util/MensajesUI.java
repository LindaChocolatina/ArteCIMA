package ArteCIMA.Util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class MensajesUI {

    private MensajesUI() {
    }

    public static void informacion(JFrame parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void advertencia(JFrame parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public static void error(JFrame parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void exito(JFrame parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void resultadoOperacion(JFrame parent, boolean operacionExitosa, String mensaje) {
        if (operacionExitosa) {
            exito(parent, mensaje);
        } else {
            error(parent, mensaje);
        }
    }

    public static boolean confirmarEliminacion(JFrame parent, String descripcion) {
        return JOptionPane.showConfirmDialog(
                parent,
                "¿Está seguro de eliminar:\n" + descripcion + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static void criterioBusquedaVacio(JFrame parent, String ejemplos) {
        informacion(parent, "Ingrese un criterio de búsqueda (" + ejemplos + ").");
    }

    public static void sinResultadosBusqueda(JFrame parent) {
        advertencia(parent, "No se encontraron registros con el criterio ingresado.");
    }

    public static void seleccionarEnTabla(JFrame parent, String entidad) {
        advertencia(parent, "Seleccione un registro de " + entidad + " en la tabla.");
    }

    public static void volverAlMenu(JFrame frame) {
        frame.dispose();
    }
}
