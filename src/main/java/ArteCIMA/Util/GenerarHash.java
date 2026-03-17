package ArteCIMA.Util;

import org.mindrot.jbcrypt.BCrypt;

public class GenerarHash {
    public static void main(String[] args) {

        String contraseña = "1234"; 
        String hash = BCrypt.hashpw(contraseña, BCrypt.gensalt());

        System.out.println("Hash generado: " + hash);
       
    }
}


