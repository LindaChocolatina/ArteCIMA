package ArteCIMA.Vista;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.PermisosUI;
import ArteCIMA.Util.TemaCIMA;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Menú principal con pestañas artísticas y tarjetas de navegación CIMA.
 */
public class PagPrincipal extends JFrame {

    private static final Color[] ACENTOS_TARJETA = {
        TemaCIMA.VERDE_VIVO, TemaCIMA.AMARILLO, TemaCIMA.AZUL_CLARO, TemaCIMA.ROJO_ALEGRE,
        TemaCIMA.VERDE_VIVO, TemaCIMA.AMARILLO, TemaCIMA.AZUL_CLARO, TemaCIMA.ROJO_ALEGRE
    };

    private JPanel panelTarjetas;
    private CardLayout cardLayout;

    private JButton tabAcademica;
    private JButton tabAdministrativa;
    private JButton tabFinanciera;

    private JButton btnEstudiantes, btnInstructores, btnTalleres, btnGrupos;
    private JButton btnAcudientes, btnBecas, btnAsistencias, btnMetodos;
    private JButton btnCorporaciones, btnConvocatorias, btnAlianzas, btnRegistrarUsuario;
    private JButton btnMovimientos, btnPagosInstructor, btnReportes;

    private int indiceAcento = 0;

    public PagPrincipal() {
        initComponentsCustom();
        TemaCIMA.aplicarIcono(this);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        aplicarPermisosMenu();
        seleccionarTab(tabAcademica, "Academica", TemaCIMA.VERDE_VIVO);
    }

    private void initComponentsCustom() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ArteCIMA - Menú Principal");
        setMinimumSize(new Dimension(1000, 700));

        JPanel mainPanel = TemaCIMA.crearFondoArtistico();
        mainPanel.setLayout(new BorderLayout());

        String usuario = SesionUsuario.haySesionActiva() ? SesionUsuario.getNombreCompleto() : "";
        String rol = SesionUsuario.haySesionActiva() ? SesionUsuario.getNombreRol() : "";
        JPanel headerPanel = TemaCIMA.crearBarraSuperior("ArteCIMA", usuario, rol);

        JPanel tabBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 48, 0));
        tabBar.setBackground(TemaCIMA.BLANCO);
        tabBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TemaCIMA.BORDE_SUAVE));

        tabAcademica = crearBotonTab("Académica");
        tabAdministrativa = crearBotonTab("Administrativa");
        tabFinanciera = crearBotonTab("Financiera");

        tabAcademica.addActionListener(e -> seleccionarTab(tabAcademica, "Academica", TemaCIMA.VERDE_VIVO));
        tabAdministrativa.addActionListener(e -> seleccionarTab(tabAdministrativa, "Administrativa", TemaCIMA.AMARILLO));
        tabFinanciera.addActionListener(e -> seleccionarTab(tabFinanciera, "Financiera", TemaCIMA.AZUL_CLARO));

        tabBar.add(tabAcademica);
        tabBar.add(tabAdministrativa);
        tabBar.add(tabFinanciera);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        panelNorte.add(headerPanel, BorderLayout.NORTH);
        panelNorte.add(tabBar, BorderLayout.CENTER);

        cardLayout = new CardLayout();
        panelTarjetas = new JPanel(cardLayout);
        panelTarjetas.setOpaque(false);
        panelTarjetas.setBorder(new EmptyBorder(24, 48, 24, 48));

        inicializarBotonesModulos();

        indiceAcento = 0;
        panelTarjetas.add(envolverGridCentrado(crearPanelGrid(
                btnEstudiantes, btnInstructores, btnTalleres, btnGrupos,
                btnAcudientes, btnBecas, btnAsistencias, btnMetodos)), "Academica");

        indiceAcento = 0;
        panelTarjetas.add(envolverGridCentrado(crearPanelGrid(
                btnCorporaciones, btnConvocatorias, btnAlianzas, btnRegistrarUsuario, btnReportes)), "Administrativa");

        indiceAcento = 0;
        panelTarjetas.add(envolverGridCentrado(crearPanelGrid(
                btnMovimientos, btnPagosInstructor)), "Financiera");

        mainPanel.add(panelNorte, BorderLayout.NORTH);
        mainPanel.add(panelTarjetas, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private void inicializarBotonesModulos() {
        btnEstudiantes = crearTarjetaModulo("Estudiantes", "Registrar y consultar estudiantes");
        btnInstructores = crearTarjetaModulo("Instructores", "Registrar y consultar instructores");
        btnTalleres = crearTarjetaModulo("Talleres", "Gestionar talleres artísticos");
        btnGrupos = crearTarjetaModulo("Grupos", "Administrar grupos de taller");
        btnAcudientes = crearTarjetaModulo("Acudientes", "Gestionar acudientes");
        btnBecas = crearTarjetaModulo("Becas", "Administrar becas");
        btnAsistencias = crearTarjetaModulo("Asistencias", "Registrar asistencia");
        btnMetodos = crearTarjetaModulo("Métodos", "Configurar métodos de pago");

        btnCorporaciones = crearTarjetaModulo("Corporaciones", "Gestionar corporaciones aliadas");
        btnConvocatorias = crearTarjetaModulo("Convocatorias", "Gestionar convocatorias");
        btnAlianzas = crearTarjetaModulo("Alianzas", "Gestionar alianzas institucionales");
        btnRegistrarUsuario = crearTarjetaModulo("Usuarios", "Crear nuevos usuarios");
        btnReportes = crearTarjetaModulo("Reportes", "Generar informes del sistema");

        btnMovimientos = crearTarjetaModulo("Movimientos", "Registrar movimientos contables");
        btnPagosInstructor = crearTarjetaModulo("Pagos Instructor", "Gestionar pagos a instructores");

        btnEstudiantes.addActionListener(e -> abrirModulo(Modulo.ESTUDIANTES, () -> new FRMEstudiante().setVisible(true)));
        btnInstructores.addActionListener(e -> abrirModulo(Modulo.INSTRUCTORES, () -> new FRMInstructor().setVisible(true)));
        btnTalleres.addActionListener(e -> abrirModulo(Modulo.TALLERES, () -> new FRMTaller().setVisible(true)));
        btnGrupos.addActionListener(e -> abrirModulo(Modulo.GRUPOS, () -> new FRMGrupo().setVisible(true)));
        btnAcudientes.addActionListener(e -> abrirModulo(Modulo.ACUDIENTES, () -> new FRMAcudiente().setVisible(true)));
        btnBecas.addActionListener(e -> abrirModulo(Modulo.BECAS, () -> new FRMBeca().setVisible(true)));
        btnAsistencias.addActionListener(e -> abrirModulo(Modulo.ASISTENCIAS, () -> new FRMAsistencia().setVisible(true)));
        btnMetodos.addActionListener(e -> abrirModulo(Modulo.METODOS, () -> new FRMMetodo().setVisible(true)));

        btnCorporaciones.addActionListener(e -> abrirModulo(Modulo.CORPORACIONES, () -> new FRMCorporacion().setVisible(true)));
        btnConvocatorias.addActionListener(e -> abrirModulo(Modulo.CONVOCATORIAS, () -> new FRMConvocatoria().setVisible(true)));
        btnAlianzas.addActionListener(e -> abrirModulo(Modulo.ALIANZAS, () -> new FRMAlianza().setVisible(true)));
        btnRegistrarUsuario.addActionListener(e -> abrirModulo(Modulo.REGISTRAR_USUARIO, () -> new RegistrarUsuario().setVisible(true)));
        btnReportes.addActionListener(e -> abrirModulo(Modulo.REPORTES, () -> new FRMReportes().setVisible(true)));

        btnMovimientos.addActionListener(e -> abrirModulo(Modulo.MOVIMIENTOS, () -> new FRMMovimientoContable().setVisible(true)));
        btnPagosInstructor.addActionListener(e -> abrirModulo(Modulo.PAGOS_INSTRUCTOR, () -> new FRMPagoInstructor().setVisible(true)));
    }

    private JButton crearBotonTab(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(TemaCIMA.FUENTE_BOTON);
        btn.setForeground(TemaCIMA.TEXTO_SUAVE);
        btn.setBackground(TemaCIMA.BLANCO);
        btn.setBorder(new EmptyBorder(14, 24, 14, 24));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!btn.getFont().equals(new Font("Segoe UI", Font.BOLD, 13))) {
                    btn.setBackground(new Color(254, 252, 248));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!btn.getFont().equals(new Font("Segoe UI", Font.BOLD, 13))) {
                    btn.setBackground(TemaCIMA.BLANCO);
                }
            }
        });
        return btn;
    }

    private void seleccionarTab(JButton tabActivo, String nombrePanel, Color colorTrazo) {
        resetTab(tabAcademica);
        resetTab(tabAdministrativa);
        resetTab(tabFinanciera);

        tabActivo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabActivo.setForeground(TemaCIMA.AZUL_REY);
        tabActivo.setBorder(BorderFactory.createCompoundBorder(
                new TemaCIMA.TrazoPincelBorder(colorTrazo),
                new EmptyBorder(14, 24, 6, 24)));

        cardLayout.show(panelTarjetas, nombrePanel);
    }

    private void resetTab(JButton tab) {
        tab.setFont(TemaCIMA.FUENTE_BOTON);
        tab.setForeground(TemaCIMA.TEXTO_SUAVE);
        tab.setBorder(new EmptyBorder(14, 24, 14, 24));
        tab.setBackground(TemaCIMA.BLANCO);
    }

    private JPanel crearPanelGrid(JButton... botones) {
        JPanel grid = new JPanel(new FlowLayout(FlowLayout.CENTER, 28, 28));
        grid.setOpaque(false);
        for (JButton btn : botones) {
            grid.add(btn);
        }
        return grid;
    }

    /** Centra el bloque de tarjetas en el área disponible. */
    private JPanel envolverGridCentrado(JPanel grid) {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);
        contenedor.add(Box.createVerticalGlue());
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(grid);
        contenedor.add(Box.createVerticalGlue());
        return contenedor;
    }

    private JButton crearTarjetaModulo(String texto, String tooltip) {
        Color acento = ACENTOS_TARJETA[indiceAcento % ACENTOS_TARJETA.length];
        indiceAcento++;
        final boolean[] hover = {false};

        JButton btn = new JButton("<html><center>" + texto + "</center></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), TemaCIMA.RADIO_TARJETA, TemaCIMA.RADIO_TARJETA);

                g2.setColor(acento);
                g2.fillRoundRect(0, 8, 6, getHeight() - 16, 8, 8);

                if (hover[0]) {
                    g2.setColor(new Color(acento.getRed(), acento.getGreen(), acento.getBlue(), 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), TemaCIMA.RADIO_TARJETA, TemaCIMA.RADIO_TARJETA);
                }
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover[0] ? acento : TemaCIMA.BORDE_SUAVE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, TemaCIMA.RADIO_TARJETA, TemaCIMA.RADIO_TARJETA);
                g2.dispose();
            }
        };

        btn.setToolTipText(tooltip);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(TemaCIMA.TEXTO);
        btn.setBackground(TemaCIMA.BLANCO);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 115));
        btn.setBorder(new EmptyBorder(12, 16, 12, 16));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) {
                    hover[0] = true;
                    btn.setForeground(TemaCIMA.AZUL_REY);
                    btn.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover[0] = false;
                btn.setForeground(TemaCIMA.TEXTO);
                btn.repaint();
            }
        });

        return btn;
    }

    private void aplicarPermisosMenu() {
        PermisosUI.configurarBotonMenu(btnEstudiantes, Modulo.ESTUDIANTES);
        PermisosUI.configurarBotonMenu(btnInstructores, Modulo.INSTRUCTORES);
        PermisosUI.configurarBotonMenu(btnTalleres, Modulo.TALLERES);
        PermisosUI.configurarBotonMenu(btnGrupos, Modulo.GRUPOS);
        PermisosUI.configurarBotonMenu(btnAcudientes, Modulo.ACUDIENTES);
        PermisosUI.configurarBotonMenu(btnBecas, Modulo.BECAS);
        PermisosUI.configurarBotonMenu(btnAsistencias, Modulo.ASISTENCIAS);
        PermisosUI.configurarBotonMenu(btnMetodos, Modulo.METODOS);
        PermisosUI.configurarBotonMenu(btnCorporaciones, Modulo.CORPORACIONES);
        PermisosUI.configurarBotonMenu(btnConvocatorias, Modulo.CONVOCATORIAS);
        PermisosUI.configurarBotonMenu(btnAlianzas, Modulo.ALIANZAS);
        PermisosUI.configurarBotonMenu(btnMovimientos, Modulo.MOVIMIENTOS);
        PermisosUI.configurarBotonMenu(btnPagosInstructor, Modulo.PAGOS_INSTRUCTOR);
        PermisosUI.configurarBotonMenu(btnRegistrarUsuario, Modulo.REGISTRAR_USUARIO);
        PermisosUI.configurarBotonMenu(btnReportes, Modulo.REPORTES);
    }

    private void abrirModulo(Modulo modulo, Runnable abrir) {
        if (!SesionUsuario.puedeAcceder(modulo)) {
            MensajesUI.error(this, "Su rol no tiene acceso al módulo de " + modulo.getEtiqueta() + ".");
            return;
        }
        abrir.run();
    }
}
