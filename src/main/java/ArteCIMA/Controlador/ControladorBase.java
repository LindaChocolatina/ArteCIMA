package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;

public abstract class ControladorBase {

    protected String ultimoMensaje = "";

    protected abstract Modulo getModulo();

    protected boolean verificarPermiso(String accion) {
        switch (accion) {
            case "Insertar":
                if (!SesionUsuario.puedeInsertar(getModulo())) {
                    ultimoMensaje = "No tiene permiso para registrar en " + getModulo().getEtiqueta() + ".";
                    return false;
                }
                break;
            case "Modificar":
                if (!SesionUsuario.puedeModificar(getModulo())) {
                    ultimoMensaje = "No tiene permiso para modificar en " + getModulo().getEtiqueta() + ".";
                    return false;
                }
                break;
            case "Eliminar":
                if (!SesionUsuario.puedeEliminar(getModulo())) {
                    ultimoMensaje = "No tiene permiso para eliminar en " + getModulo().getEtiqueta() + ".";
                    return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}
