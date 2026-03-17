package ArteCIMA.Util;  // AJUSTA AL PAQUETE DONDE ESTÁS

import ArteCIMA.DAO.LoginDAO;
import ArteCIMA.Modelo.Usuario;

public class TestLogin {
    public static void main(String[] args) {

        String usuario = "admin";       // <-- cámbialo si quieres
        String clave   = "1234";        // <-- tu contraseña sin hash
        String rol     = "Administrador";  // <-- tal cual aparece en tu BD

        LoginDAO dao = new LoginDAO();

        System.out.println("Probando login...");
        Usuario u = dao.autenticar(usuario, clave);

        if (u != null) {
            System.out.println("LOGIN CORRECTO");
            System.out.println("Usuario: " + u.getUsuario());
            System.out.println("Nombre completo: " + u.getNombreCompleto());
            System.out.println("Rol: " + u.getRol());
        } else {
            System.out.println("LOGIN FALLIDO");
        }
    }
}