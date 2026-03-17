package ArteCIMA.Util;

public class SesionUsuario {
    private static String nombreRol; 
    private static String nombreCompleto; 

        public static boolean puedeModificarInstructores() {
        if (nombreRol == null) return false;
       
        return "Administrador".equalsIgnoreCase(nombreRol) || 
        
        return "Administrador".equalsIgnoreCase(nombreRol) || 
               "Coordinador".equalsIgnoreCase(nombreRol); 
    }
        
    public static void iniciarSesion(String rol, String nombreCompleto) {
        SesionUsuario.nombreRol = rol;
        SesionUsuario.nombreCompleto = nombreCompleto;
    }

       public static void cerrarSesion() {
        SesionUsuario.nombreRol = null;
        SesionUsuario.nombreCompleto = null;
    }

    public static String getNombreRol() {
        return nombreRol;
    }
}