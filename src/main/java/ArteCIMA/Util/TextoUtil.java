package ArteCIMA.Util;

import java.text.Normalizer;
import java.util.Locale;

public final class TextoUtil {

    private static final String TILDES_ORIGEN = "áéíóúàèìòùäëïöüñÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÑ";
    private static final String TILDES_DESTINO = "aeiouaeiouaeiounaeiouaeiouaeioun";

    private TextoUtil() {
    }

    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase(Locale.ROOT).trim();
    }

    public static String expresionSqlSinTildes(String columna) {
        return "translate(lower(" + columna + "), '"
                + TILDES_ORIGEN + "', '"
                + TILDES_DESTINO + "')";
    }

    public static String patronBusqueda(String criterio) {
        return "%" + normalizar(criterio) + "%";
    }
}
