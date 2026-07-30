package ArteCIMA.Util;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Normalizer;
import java.util.Locale;
import javax.swing.JTextField;

public final class TextoUtil {

    public static final String PLACEHOLDER_BUSCAR = "Buscar";

    private static final String KEY_PLACEHOLDER_ACTIVO = "arteCIMA.placeholderBuscarActivo";
    private static final String KEY_COLOR_NORMAL = "arteCIMA.colorBuscarNormal";
    private static final String KEY_PLACEHOLDER_APLICADO = "arteCIMA.placeholderBuscarAplicado";

    private static final String TILDES_ORIGEN = "áéíóúàèìòùäëïöüñÁÉÍÓÚÀÈÌÒÙÄËÏÖÜÑ";
    private static final String TILDES_DESTINO = "aeiouaeiouaeiounaeiouaeiouaeioun";

    private TextoUtil() {
    }

    /** Placeholder "Buscar" con limpieza al hacer clic o al enfocar. */
    public static void aplicarPlaceholderBuscar(JTextField campo) {
        if (campo == null) {
            return;
        }
        campo.putClientProperty(KEY_COLOR_NORMAL, TemaCIMA.TEXTO);

        if (!Boolean.TRUE.equals(campo.getClientProperty(KEY_PLACEHOLDER_APLICADO))) {
            campo.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    activarEdicionBuscar(campo);
                }
            });
            campo.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    activarEdicionBuscar(campo);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (campo.getText() == null || campo.getText().trim().isEmpty()) {
                        restaurarPlaceholderBuscar(campo);
                    }
                }
            });
            campo.putClientProperty(KEY_PLACEHOLDER_APLICADO, true);
        }
        restaurarPlaceholderBuscar(campo);
    }

    public static void activarEdicionBuscar(JTextField campo) {
        if (!Boolean.TRUE.equals(campo.getClientProperty(KEY_PLACEHOLDER_ACTIVO))) {
            return;
        }
        campo.setText("");
        Color normal = (Color) campo.getClientProperty(KEY_COLOR_NORMAL);
        campo.setForeground(normal != null ? normal : TemaCIMA.TEXTO);
        campo.putClientProperty(KEY_PLACEHOLDER_ACTIVO, false);
    }

    public static void restaurarPlaceholderBuscar(JTextField campo) {
        if (campo == null) {
            return;
        }
        campo.setText(PLACEHOLDER_BUSCAR);
        campo.setForeground(TemaCIMA.TEXTO_SUAVE);
        campo.putClientProperty(KEY_PLACEHOLDER_ACTIVO, true);
    }

    public static String criterioBusqueda(JTextField campo) {
        if (campo == null || Boolean.TRUE.equals(campo.getClientProperty(KEY_PLACEHOLDER_ACTIVO))) {
            return "";
        }
        String texto = campo.getText();
        if (texto == null || texto.trim().isEmpty()
                || PLACEHOLDER_BUSCAR.equalsIgnoreCase(texto.trim())) {
            return "";
        }
        return texto.trim();
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
