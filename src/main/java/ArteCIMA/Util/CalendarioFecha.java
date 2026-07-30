package ArteCIMA.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Selector de fecha con calendario mensual (sin dependencias externas).
 */
public final class CalendarioFecha {

    private CalendarioFecha() {
    }

    public static void mostrar(Window owner, LocalDate inicial, Consumer<LocalDate> alSeleccionar) {
        LocalDate base = inicial != null ? inicial : LocalDate.now();
        JDialog dialog = new JDialog(owner, "Seleccionar fecha", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(TemaCIMA.CREMA);

        JLabel lblMes = new JLabel("", SwingConstants.CENTER);
        lblMes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMes.setForeground(TemaCIMA.AZUL_REY);

        JButton btnAnt = new JButton("◀");
        JButton btnSig = new JButton("▶");
        estilizarNav(btnAnt);
        estilizarNav(btnSig);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(btnAnt, BorderLayout.WEST);
        header.add(lblMes, BorderLayout.CENTER);
        header.add(btnSig, BorderLayout.EAST);

        JPanel diasSemana = new JPanel(new GridLayout(1, 7, 2, 2));
        diasSemana.setOpaque(false);
        String[] nombres = {"Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do"};
        for (String n : nombres) {
            JLabel l = new JLabel(n, SwingConstants.CENTER);
            l.setFont(new Font("Segoe UI", Font.BOLD, 11));
            l.setForeground(TemaCIMA.TEXTO_SUAVE);
            diasSemana.add(l);
        }

        JPanel grilla = new JPanel(new GridLayout(6, 7, 2, 2));
        grilla.setOpaque(false);

        final YearMonth[] mes = {YearMonth.from(base)};
        final LocalDate[] seleccion = {base};

        Runnable pintar = () -> {
            grilla.removeAll();
            YearMonth ym = mes[0];
            lblMes.setText(ym.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                    + " " + ym.getYear());
            LocalDate primero = ym.atDay(1);
            int offset = primero.getDayOfWeek().getValue() - 1; // lunes=0
            int diasMes = ym.lengthOfMonth();
            LocalDate hoy = LocalDate.now();

            for (int i = 0; i < 42; i++) {
                int diaNum = i - offset + 1;
                if (diaNum < 1 || diaNum > diasMes) {
                    grilla.add(new JLabel(""));
                    continue;
                }
                LocalDate fecha = ym.atDay(diaNum);
                JButton btn = new JButton(String.valueOf(diaNum));
                btn.setFocusPainted(false);
                btn.setPreferredSize(new Dimension(36, 28));
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                btn.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

                boolean esHoy = fecha.equals(hoy);
                boolean esSel = fecha.equals(seleccion[0]);
                if (esSel) {
                    btn.setBackground(TemaCIMA.AZUL_REY);
                    btn.setForeground(Color.WHITE);
                } else if (esHoy) {
                    btn.setBackground(new Color(232, 244, 253));
                    btn.setForeground(TemaCIMA.AZUL_REY);
                } else {
                    btn.setBackground(Color.WHITE);
                    btn.setForeground(TemaCIMA.TEXTO);
                }
                btn.setBorder(BorderFactory.createLineBorder(TemaCIMA.BORDE_SUAVE));
                btn.addActionListener(e -> {
                    seleccion[0] = fecha;
                    if (alSeleccionar != null) {
                        alSeleccionar.accept(fecha);
                    }
                    dialog.dispose();
                });
                grilla.add(btn);
            }
            grilla.revalidate();
            grilla.repaint();
        };

        btnAnt.addActionListener(e -> {
            mes[0] = mes[0].minusMonths(1);
            pintar.run();
        });
        btnSig.addActionListener(e -> {
            mes[0] = mes[0].plusMonths(1);
            pintar.run();
        });

        JButton btnHoy = new JButton("Hoy");
        TemaCIMA.estilizarBotonSecundario(btnHoy);
        btnHoy.addActionListener(e -> {
            LocalDate hoy = LocalDate.now();
            seleccion[0] = hoy;
            mes[0] = YearMonth.from(hoy);
            if (alSeleccionar != null) {
                alSeleccionar.accept(hoy);
            }
            dialog.dispose();
        });

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnHoy);

        JPanel centro = new JPanel(new BorderLayout(4, 4));
        centro.setOpaque(false);
        centro.add(diasSemana, BorderLayout.NORTH);
        centro.add(grilla, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(centro, BorderLayout.CENTER);
        root.add(sur, BorderLayout.SOUTH);

        pintar.run();
        dialog.setContentPane(root);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    private static void estilizarNav(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(TemaCIMA.BLANCO);
        btn.setForeground(TemaCIMA.AZUL_REY);
        btn.setBorder(BorderFactory.createLineBorder(TemaCIMA.BORDE_SUAVE));
        btn.setPreferredSize(new Dimension(40, 28));
        btn.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }
}
