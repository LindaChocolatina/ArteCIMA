package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;
import ArteCIMA.Modelo.Usuario;
import java.awt.event.ActionEvent;
import org.mindrot.jbcrypt.BCrypt;

public class ControladorUsuario extends ControladorBase {

    @Override
    protected Modulo getModulo() {
        return Modulo.REGISTRAR_USUARIO;
    }

    public boolean controlarAccion(ActionEvent evento, Usuario usuario) {
        String accion = evento.getActionCommand();
        if (!verificarPermiso(accion)) {
            return false;
        }
        switch (accion) {
            case "Insertar":
                boolean insertado = usuario.insertar();
                ultimoMensaje = insertado
                        ? "Usuario registrado con éxito."
                        : "Error al registrar usuario.";
                return insertado;
            case "Modificar":
                boolean modificado = usuario.modificar();
                ultimoMensaje = modificado
                        ? "Usuario actualizado correctamente."
                        : "No se pudo actualizar el usuario.";
                return modificado;
            case "Eliminar":
                boolean eliminado = usuario.eliminar();
                ultimoMensaje = eliminado
                        ? "Usuario eliminado correctamente."
                        : "No se pudo eliminar el usuario.";
                return eliminado;
            default:
                ultimoMensaje = "Acción no reconocida: " + accion;
                return false;
        }
    }

    public boolean registrar(Usuario usuario, String clave, String claveConfirmacion) {
        if (!SesionUsuario.puedeRegistrarUsuarios()) {
            ultimoMensaje = "No tiene permiso para registrar usuarios.";
            return false;
        }

        if (usuario.getRol() == null || "Selecciona".equals(usuario.getRol())) {
            ultimoMensaje = "Debes seleccionar un rol.";
            return false;
        }

        if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()
                || usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()
                || usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            ultimoMensaje = "Todos los campos son obligatorios.";
            return false;
        }

        if (clave == null || clave.isEmpty()) {
            ultimoMensaje = "La contraseña es obligatoria.";
            return false;
        }

        if (!clave.equals(claveConfirmacion)) {
            ultimoMensaje = "Las contraseñas no coinciden.";
            return false;
        }

        usuario.setPasswordHash(BCrypt.hashpw(clave, BCrypt.gensalt(12)));
        boolean ok = usuario.insertar();
        ultimoMensaje = ok ? "Usuario registrado con éxito." : "Error al registrar usuario.";
        return ok;
    }

    public Usuario autenticar(String nombreUsuario, String clave) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()
                || clave == null || clave.isEmpty()) {
            ultimoMensaje = "Completa todos los campos.";
            return null;
        }

        Usuario u = Usuario.autenticar(nombreUsuario, clave);
        if (u == null) {
            ultimoMensaje = "Credenciales incorrectas.";
        }
        return u;
    }

    public Usuario buscar(String nombreUsuario) {
        return Usuario.buscar(nombreUsuario);
    }
}
