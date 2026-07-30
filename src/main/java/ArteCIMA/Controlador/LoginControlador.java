package ArteCIMA.Controlador;

import ArteCIMA.Modelo.SesionUsuario;
import ArteCIMA.Modelo.Usuario;
import ArteCIMA.Vista.Login;

public class LoginControlador {

    private final Login vista;
    private final ControladorUsuario controladorUsuario;

    public LoginControlador(Login vista) {
        this.vista = vista;
        this.controladorUsuario = new ControladorUsuario();
    }

    public void iniciarSesion() {
        String user = vista.getUsuario();
        String pass = vista.getClave();

        Usuario u = controladorUsuario.autenticar(user, pass);

        if (u != null) {
            SesionUsuario.iniciarSesion(u.getRol(), u.getNombreCompleto(), u.getCorreo(), resolverIdInstructor(u));
            vista.mostrarBienvenida(u.getNombreCompleto());
            vista.abrirPagPrincipal();
            vista.cerrar();
        } else {
            vista.mostrarError(controladorUsuario.getUltimoMensaje());
        }
    }

    private Integer resolverIdInstructor(Usuario u) {
        if (u == null || u.getRol() == null || !u.getRol().equalsIgnoreCase("Instructor")) {
            return null;
        }
        return ArteCIMA.Modelo.Instructor.resolverIdPorUsuario(u.getCorreo(), u.getNombreCompleto());
    }
}
