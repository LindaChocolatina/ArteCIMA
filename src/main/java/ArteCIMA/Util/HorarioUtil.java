package ArteCIMA.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;

public final class HorarioUtil {

    public static final String[] DIAS = {
        "Selecciona", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes",
        "Sábado", "Domingo", "Lunes y miércoles", "Martes y jueves", "Sábados"
    };

    public static final String[] HORAS = {
        "Selecciona", "08:00", "09:00", "10:00", "11:00", "12:00",
        "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"
    };

    private static final Pattern PATRON_HORARIO = Pattern.compile("(.+?)\\s+(\\d{2}:\\d{2})-(\\d{2}:\\d{2})");

    private HorarioUtil() {
    }

    public static String construir(String dia, String horaInicio, String horaFin) {
        if ("Selecciona".equals(dia) || "Selecciona".equals(horaInicio) || "Selecciona".equals(horaFin)) {
            return "";
        }
        return dia + " " + horaInicio + "-" + horaFin;
    }

    public static void aplicar(String horario, JComboBox<String> comboDia,
            JComboBox<String> comboHoraInicio, JComboBox<String> comboHoraFin) {
        comboDia.setSelectedIndex(0);
        comboHoraInicio.setSelectedIndex(0);
        comboHoraFin.setSelectedIndex(0);

        if (horario == null || horario.isBlank()) {
            return;
        }

        Matcher matcher = PATRON_HORARIO.matcher(horario.trim());
        if (matcher.matches()) {
            seleccionarItem(comboDia, matcher.group(1).trim());
            seleccionarItem(comboHoraInicio, matcher.group(2).trim());
            seleccionarItem(comboHoraFin, matcher.group(3).trim());
        }
    }

    private static void seleccionarItem(JComboBox<String> combo, String valor) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (valor.equalsIgnoreCase(combo.getItemAt(i))) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }
}
