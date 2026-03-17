
package ArteCIMA.Util;

import org.mindrot.jbcrypt.BCrypt;

public class VerificarPassword {
    public static void main(String[] args) {
        String passReal = "1234";  // La contraseña que tú estás digitando
        String hashDB = "$2a$10$4rX7/4pJ3psGfxGEy7QNcupZkvrgrzyoDLEnp0nnZGEC6jpDNe2Me";

        boolean ok = BCrypt.checkpw(passReal, hashDB);
        System.out.println("¿Coincide?: " + ok);
    }

    
}
