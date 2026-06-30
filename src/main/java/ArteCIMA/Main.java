package ArteCIMA;

import ArteCIMA.Util.TemaCIMA;
import ArteCIMA.Vista.Login;

/**
 * Punto de entrada de la aplicación Arte CIMA.
 */
public class Main {

    public static void main(String[] args) {
        TemaCIMA.inicializar();
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}
