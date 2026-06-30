package ArteCIMA.Util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * Identidad visual Arte CIMA — paleta del logo, formas suaves y acentos artísticos.
 */
public final class TemaCIMA {

    public static final Color AZUL_REY = new Color(0, 51, 102);
    public static final Color AZUL_CLARO = new Color(33, 125, 187);
    public static final Color VERDE_VIVO = new Color(46, 204, 113);
    public static final Color VERDE_HOVER = new Color(39, 174, 96);
    public static final Color AMARILLO = new Color(241, 196, 15);
    public static final Color AMARILLO_HOVER = new Color(212, 172, 13);
    public static final Color ROJO_ALEGRE = new Color(231, 76, 60);
    public static final Color ROJO_HOVER = new Color(192, 57, 43);
    public static final Color CREMA = new Color(253, 251, 245);
    public static final Color CREMA_FILA = new Color(254, 249, 236);
    public static final Color BLANCO = Color.WHITE;
    public static final Color TEXTO = new Color(33, 33, 33);
    public static final Color TEXTO_SUAVE = new Color(90, 90, 90);
    public static final Color BORDE_SUAVE = new Color(213, 219, 225);

    public static final int RADIO_BOTON = 18;
    public static final int RADIO_CAMPO = 14;
    public static final int RADIO_TARJETA = 22;
    public static final int ANCHO_TARJETA_VERTICAL = 380;

    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_ESLOGAN = new Font("Pristina", Font.PLAIN, 32);

    private TemaCIMA() {
    }

    public static void inicializar() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            UIManager.put("Button.arc", RADIO_BOTON);
            UIManager.put("Component.arc", RADIO_CAMPO);
            UIManager.put("TextComponent.arc", RADIO_CAMPO);
            UIManager.put("Panel.background", CREMA);
            UIManager.put("TextField.background", BLANCO);
            UIManager.put("PasswordField.background", BLANCO);
            UIManager.put("ComboBox.background", BLANCO);
            UIManager.put("Table.alternateRowColor", CREMA_FILA);
            UIManager.put("Component.focusColor", AZUL_REY);
            UIManager.put("Component.borderColor", BORDE_SUAVE);
            UIManager.put("defaultFont", FUENTE_CAMPO);
            UIManager.put("ScrollBar.width", 10);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.trackArc", 999);
        } catch (Exception ex) {
            System.err.println("FlatLaf no disponible: " + ex.getMessage());
        }
    }

    public static void aplicarIcono(JFrame frame) {
        java.net.URL url = TemaCIMA.class.getResource("/imagenes/logo2.png");
        if (url == null) {
            url = TemaCIMA.class.getResource("/imagenes/loguito.jpeg");
        }
        if (url != null) {
            frame.setIconImage(new ImageIcon(url).getImage());
        }
    }

    /** Fondo crema con salpicaduras suaves de pintura (logo). */
    public static JPanel crearFondoArtistico() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CREMA);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(241, 196, 15, 28));
                g2.fillOval(-40, getHeight() / 3, 160, 120);
                g2.setColor(new Color(46, 204, 113, 22));
                g2.fillOval(getWidth() - 120, 40, 140, 100);
                g2.setColor(new Color(231, 76, 60, 20));
                g2.fillOval(getWidth() / 2, getHeight() - 100, 130, 90);
                g2.setColor(new Color(0, 51, 102, 15));
                g2.fillOval(getWidth() / 4, 20, 100, 80);
                g2.dispose();
            }
        };
    }

    /** Tarjeta flotante vertical centrada (login / registro). */
    public static JPanel crearTarjetaFlotante() {
        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(new CompoundBorder(
                new BordeRedondeado(new Color(0, 51, 102, 40), RADIO_TARJETA),
                new EmptyBorder(28, 32, 28, 32)));
        tarjeta.setOpaque(true);
        return tarjeta;
    }

    public static JLabel crearEtiquetaCampo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_LABEL);
        lbl.setForeground(AZUL_REY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    public static void estilizarCampo(JTextField campo) {
        campo.setFont(FUENTE_CAMPO);
        campo.setForeground(TEXTO);
        campo.setBackground(BLANCO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new BordeRedondeado(BORDE_SUAVE, RADIO_CAMPO),
                new EmptyBorder(8, 12, 8, 12)));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static void estilizarCampo(JPasswordField campo) {
        campo.setFont(FUENTE_CAMPO);
        campo.setForeground(TEXTO);
        campo.setBackground(BLANCO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new BordeRedondeado(BORDE_SUAVE, RADIO_CAMPO),
                new EmptyBorder(8, 12, 8, 12)));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    @SuppressWarnings("unchecked")
    public static void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(FUENTE_CAMPO);
        combo.setForeground(TEXTO);
        combo.setBackground(BLANCO);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static void estilizarBotonPrimario(JButton btn) {
        estilizarBoton(btn, AZUL_REY, new Color(0, 40, 82), BLANCO);
    }

    public static void estilizarBotonExito(JButton btn) {
        estilizarBoton(btn, VERDE_VIVO, VERDE_HOVER, BLANCO);
    }

    public static void estilizarBotonPeligro(JButton btn) {
        estilizarBoton(btn, ROJO_ALEGRE, ROJO_HOVER, BLANCO);
    }

    public static void estilizarBotonAdvertencia(JButton btn) {
        estilizarBoton(btn, AMARILLO, AMARILLO_HOVER, TEXTO);
    }

    public static void estilizarBotonSecundario(JButton btn) {
        btn.setFont(FUENTE_BOTON);
        btn.setForeground(AZUL_REY);
        btn.setBackground(BLANCO);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                new BordeRedondeado(AZUL_REY, RADIO_BOTON),
                new EmptyBorder(8, 18, 8, 18)));
        agregarHover(btn, BLANCO, new Color(232, 244, 253));
    }

    private static void estilizarBoton(JButton btn, Color bg, Color hover, Color fg) {
        btn.setFont(FUENTE_BOTON);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 24, 10, 24));
        btn.setContentAreaFilled(true);
        btn.setOpaque(false);
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.putClientProperty("Component.arc", RADIO_BOTON);
        agregarHover(btn, bg, hover);
    }

    private static void agregarHover(JButton btn, Color normal, Color hover) {
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(hover);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(normal);
            }
        });
    }

    public static ImageIcon logoEscalado(int size) {
        java.net.URL url = TemaCIMA.class.getResource("/imagenes/logo2.png");
        if (url == null) {
            url = TemaCIMA.class.getResource("/imagenes/loguito.jpeg");
        }
        if (url == null) {
            return null;
        }
        Image img = new ImageIcon(url).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /** Subrayado grueso tipo trazo de pincel para pestañas activas. */
    public static class TrazoPincelBorder extends AbstractBorder {
        private final Color color;

        public TrazoPincelBorder(Color color) {
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D path = new Path2D.Float();
            int pad = 12;
            path.moveTo(pad, h - 2);
            path.quadTo(w / 4f, h - 8, w / 2f, h - 3);
            path.quadTo(3 * w / 4f, h + 2, w - pad, h - 4);
            g2.draw(path);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 8, 0);
        }
    }

    public static class BordeRedondeado extends AbstractBorder {
        private final Color color;
        private final int radio;

        public BordeRedondeado(Color color, int radio) {
            this.color = color;
            this.radio = radio;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radio, radio);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }
    }

    /** Barra superior azul rey con gradiente sutil. */
    public static JPanel crearBarraSuperior(String tituloIzq, String usuario, String rol) {
        JPanel barra = new JPanel(new java.awt.BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, AZUL_REY, getWidth(), 0, AZUL_CLARO));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        barra.setOpaque(false);
        barra.setBorder(new EmptyBorder(10, 28, 10, 28));

        JPanel izq = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));
        izq.setOpaque(false);
        ImageIcon logo = logoEscalado(42);
        if (logo != null) {
            izq.add(new JLabel(logo));
        }
        JLabel lblApp = new JLabel(tituloIzq);
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblApp.setForeground(BLANCO);
        izq.add(lblApp);

        JPanel der = new JPanel();
        der.setLayout(new javax.swing.BoxLayout(der, javax.swing.BoxLayout.Y_AXIS));
        der.setOpaque(false);
        if (usuario != null) {
            JLabel lblU = new JLabel(usuario);
            lblU.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblU.setForeground(BLANCO);
            lblU.setAlignmentX(Component.RIGHT_ALIGNMENT);
            der.add(lblU);
        }
        if (rol != null) {
            JLabel lblR = new JLabel(rol);
            lblR.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblR.setForeground(new Color(220, 230, 240));
            lblR.setAlignmentX(Component.RIGHT_ALIGNMENT);
            der.add(lblR);
        }

        barra.add(izq, java.awt.BorderLayout.WEST);
        barra.add(der, java.awt.BorderLayout.EAST);
        return barra;
    }

    public static void centrarTarjetaEnVentana(JFrame frame, JComponent tarjeta, int ancho, int alto) {
        JPanel fondo = crearFondoArtistico();
        fondo.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        tarjeta.setPreferredSize(new Dimension(ancho, alto > 0 ? alto : tarjeta.getPreferredSize().height));
        fondo.add(tarjeta, gbc);
        frame.setContentPane(fondo);
        frame.pack();
        frame.setResizable(false);
    }

    /** Centra la tarjeta y deja que la altura se calcule según el contenido. */
    public static void centrarTarjetaAuto(JFrame frame, JComponent tarjeta, int ancho) {
        JPanel fondo = crearFondoArtistico();
        fondo.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        if (ancho > 0) {
            tarjeta.setMaximumSize(new Dimension(ancho, Integer.MAX_VALUE));
        }
        fondo.add(tarjeta, gbc);
        frame.setContentPane(fondo);
        frame.pack();
        frame.setResizable(false);
    }

    /** Panel de formulario con ancho fijo para alinear etiquetas y campos. */
    public static JPanel crearPanelCampos(int anchoCampo) {
        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(anchoCampo, Integer.MAX_VALUE));
        return panel;
    }

    public static void agregarFilaCampo(JPanel panel, String etiqueta, JComponent campo, int anchoCampo) {
        JLabel lbl = crearEtiquetaCampo(etiqueta);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(anchoCampo, 22));
        panel.add(lbl);
        panel.add(javax.swing.Box.createVerticalStrut(5));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setPreferredSize(new Dimension(anchoCampo, 38));
        campo.setMaximumSize(new Dimension(anchoCampo, 38));
        panel.add(campo);
        panel.add(javax.swing.Box.createVerticalStrut(10));
    }
}
