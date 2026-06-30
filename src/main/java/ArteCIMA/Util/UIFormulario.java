package ArteCIMA.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 * Presentación unificada para formularios CRUD con identidad artística CIMA.
 */
public final class UIFormulario {

    private static final int ANCHO_VENTANA = 1280;
    private static final int ALTO_VENTANA = 780;
    private static final int MARGEN_H = 32;
    private static final int ALTO_TABLA = 300;

    private UIFormulario() {
    }

    public static void aplicarIcono(JFrame frame) {
        TemaCIMA.aplicarIcono(frame);
    }

    public static void prepararModulo(JFrame frame, JPanel panelFondo, JPanel panelForm,
            JLabel lblLogo, JLabel lblTitulo, JTable tabla) {

        aplicarIcono(frame);

        String tituloTexto = (lblTitulo != null) ? lblTitulo.getText() : "";

        if (tabla != null) {
            estilizarTabla(tabla);
        }

        estilizarBotonesDelPanel(panelForm);
        estilizarComponentes(panelForm);
        armonizarPanel(panelForm, tabla);
        reconstruirEstructura(panelFondo, panelForm, tituloTexto);

        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(TemaCIMA.CREMA);
        frame.getContentPane().add(panelFondo, BorderLayout.CENTER);

        frame.setSize(ANCHO_VENTANA, ALTO_VENTANA);
        frame.setMinimumSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
    }

    private static void reconstruirEstructura(JPanel panelFondo, JPanel panelForm, String tituloTexto) {
        panelFondo.removeAll();
        panelFondo.setLayout(new BorderLayout());
        panelFondo.setBackground(TemaCIMA.CREMA);

        JPanel barraTop = new JPanel(new BorderLayout());
        barraTop.setBackground(TemaCIMA.AZUL_REY);
        barraTop.setBorder(new EmptyBorder(12, 28, 12, 28));

        JPanel izqBarra = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        izqBarra.setOpaque(false);

        ImageIcon logo = TemaCIMA.logoEscalado(34);
        if (logo != null) {
            izqBarra.add(new JLabel(logo));
        }

        JLabel lblTit = new JLabel(tituloTexto);
        lblTit.setFont(TemaCIMA.FUENTE_TITULO);
        lblTit.setForeground(TemaCIMA.BLANCO);
        izqBarra.add(lblTit);

        barraTop.add(izqBarra, BorderLayout.WEST);

        JPanel franjaAmarilla = new JPanel();
        franjaAmarilla.setBackground(TemaCIMA.AMARILLO);
        franjaAmarilla.setPreferredSize(new Dimension(0, 3));

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(TemaCIMA.CREMA);
        contenido.setBorder(new EmptyBorder(16, 28, 24, 28));
        contenido.add(panelForm, BorderLayout.CENTER);

        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setBackground(TemaCIMA.CREMA);
        cuerpo.add(franjaAmarilla, BorderLayout.NORTH);
        cuerpo.add(contenido, BorderLayout.CENTER);

        panelFondo.add(barraTop, BorderLayout.NORTH);
        panelFondo.add(cuerpo, BorderLayout.CENTER);

        panelFondo.revalidate();
        panelFondo.repaint();
    }

    private static void armonizarPanel(JPanel panelForm, JTable tabla) {
        panelForm.setBackground(TemaCIMA.CREMA);
        panelForm.setBorder(new EmptyBorder(8, 0, 8, 0));

        JScrollPane scroll = buscarScrollPane(panelForm);
        if (scroll != null) {
            ajustarScroll(panelForm, scroll);
        }

        if (tabla != null && scroll != null) {
            scroll.setViewportView(tabla);
        }

        panelForm.revalidate();
        panelForm.repaint();
    }

    private static void ajustarScroll(JPanel panelForm, JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createCompoundBorder(
                new TemaCIMA.BordeRedondeado(TemaCIMA.BORDE_SUAVE, TemaCIMA.RADIO_CAMPO),
                new EmptyBorder(2, 2, 2, 2)));

        panelForm.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int ancho = panelForm.getWidth() - (2 * MARGEN_H);
                if (ancho > 200) {
                    scroll.setPreferredSize(new Dimension(ancho, ALTO_TABLA));
                    scroll.setMinimumSize(new Dimension(ancho, ALTO_TABLA));
                    panelForm.revalidate();
                }
            }
        });

        int anchoInicial = ANCHO_VENTANA - 120;
        scroll.setPreferredSize(new Dimension(anchoInicial, ALTO_TABLA));
        scroll.setMinimumSize(new Dimension(anchoInicial, ALTO_TABLA));

        if (panelForm.getLayout() instanceof AbsoluteLayout) {
            int y = scroll.getY() > 0 ? scroll.getY() : 180;
            int h = scroll.getHeight() > 0 ? scroll.getHeight() : ALTO_TABLA;
            panelForm.remove(scroll);
            panelForm.add(scroll, new AbsoluteConstraints(MARGEN_H, y, anchoInicial, h));
        }
    }

    private static JScrollPane buscarScrollPane(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JScrollPane) {
                return (JScrollPane) c;
            }
        }
        return null;
    }

    private static void estilizarComponentes(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel) {
                c.setFont(TemaCIMA.FUENTE_CAMPO);
                c.setForeground(TemaCIMA.TEXTO);
            } else if (c instanceof JTextField) {
                TemaCIMA.estilizarCampo((JTextField) c);
            } else if (c instanceof JPasswordField) {
                TemaCIMA.estilizarCampo((JPasswordField) c);
            } else if (c instanceof JComboBox) {
                TemaCIMA.estilizarCombo((JComboBox<?>) c);
            }
        }
    }

    private static void estilizarBotonesDelPanel(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (!(c instanceof JButton)) {
                continue;
            }
            JButton btn = (JButton) c;
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            String texto = btn.getText().toLowerCase();
            String comando = btn.getActionCommand() != null ? btn.getActionCommand().toLowerCase() : "";

            if (texto.contains("insertar") || texto.contains("registrar")) {
                TemaCIMA.estilizarBotonExito(btn);
            } else if (texto.contains("editar") || texto.contains("modificar") || comando.contains("modificar")) {
                TemaCIMA.estilizarBotonAdvertencia(btn);
            } else if (texto.contains("elimi")) {
                TemaCIMA.estilizarBotonPeligro(btn);
            } else {
                TemaCIMA.estilizarBotonSecundario(btn);
            }
        }
    }

    private static void estilizarTabla(JTable tabla) {
        tabla.setFont(TemaCIMA.FUENTE_CAMPO);
        tabla.setRowHeight(28);
        tabla.setGridColor(TemaCIMA.BORDE_SUAVE);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setBackground(TemaCIMA.BLANCO);
        tabla.setSelectionBackground(new Color(232, 244, 253));
        tabla.setSelectionForeground(TemaCIMA.TEXTO);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        header.setBackground(TemaCIMA.AZUL_REY);
        header.setForeground(TemaCIMA.BLANCO);
        header.setPreferredSize(new Dimension(header.getWidth(), 34));
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, TemaCIMA.AMARILLO));

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(TemaCIMA.AZUL_REY);
                lbl.setForeground(TemaCIMA.BLANCO);
                lbl.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setBorder(new EmptyBorder(4, 6, 4, 6));
                return lbl;
            }
        });

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? TemaCIMA.BLANCO : TemaCIMA.CREMA_FILA);
                }
                ((JLabel) comp).setBorder(new EmptyBorder(2, 8, 2, 8));
                return comp;
            }
        });
    }

    /** Compatibilidad con referencias antiguas. */
    public static final java.awt.Color AZUL_MARCA = TemaCIMA.AZUL_REY;
}
