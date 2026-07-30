package ArteCIMA.Vista;

import ArteCIMA.Controlador.LoginControlador;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.TemaCIMA;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Login — tarjeta vertical artística CIMA, alineada con el registro de usuarios.
 */
public class Login extends JFrame {

    private static final int ANCHO_CAMPO = 300;

    private final LoginControlador controller;
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Iniciar sesión");

    public Login() {
        controller = new LoginControlador(this);
        construirInterfaz();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ArteCIMA - Iniciar sesión");
        TemaCIMA.aplicarIcono(this);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnIngresar);
    }

    private void construirInterfaz() {
        JPanel tarjeta = TemaCIMA.crearTarjetaFlotante();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                tarjeta.getBorder(),
                new EmptyBorder(28, 40, 32, 40)));

        tarjeta.add(crearEncabezado());
        tarjeta.add(Box.createVerticalStrut(14));
        tarjeta.add(crearFormulario());

        JPanel fondo = TemaCIMA.crearFondoArtistico();
        fondo.setLayout(new GridBagLayout());
        GridBagConstraints gbcFondo = new GridBagConstraints();
        gbcFondo.gridx = 0;
        gbcFondo.gridy = 0;
        fondo.add(tarjeta, gbcFondo);

        setContentPane(fondo);
        pack();
        setResizable(false);
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon logo = TemaCIMA.logoEscalado(100);
        if (logo != null) {
            JLabel lblLogo = new JLabel(logo);
            lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
            header.add(lblLogo);
            header.add(Box.createVerticalStrut(10));
        }

        JLabel lblEslogan = new JLabel("Arte para todos");
        lblEslogan.setFont(TemaCIMA.FUENTE_ESLOGAN);
        lblEslogan.setForeground(TemaCIMA.AZUL_REY);
        lblEslogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(lblEslogan);
        header.add(Box.createVerticalStrut(8));

        JLabel franja = new JLabel(" ");
        franja.setAlignmentX(Component.CENTER_ALIGNMENT);
        franja.setMaximumSize(new Dimension(100, 4));
        franja.setPreferredSize(new Dimension(100, 4));
        franja.setOpaque(true);
        franja.setBackground(TemaCIMA.AMARILLO);
        header.add(franja);

        return header;
    }

    private JPanel crearFormulario() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(true);
        form.setBackground(new java.awt.Color(254, 252, 248));
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                new TemaCIMA.BordeRedondeado(TemaCIMA.BORDE_SUAVE, 16),
                new EmptyBorder(22, 22, 22, 22)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        TemaCIMA.estilizarCampo(txtUsuario);
        TemaCIMA.estilizarCampo(txtClave);

        agregarFila(form, gbc, "Usuario:", txtUsuario);
        agregarFila(form, gbc, "Contraseña:", txtClave);

        gbc.gridy++;
        gbc.insets = new Insets(16, 0, 0, 0);

        btnIngresar.setPreferredSize(new Dimension(ANCHO_CAMPO, 42));
        TemaCIMA.estilizarBotonPrimario(btnIngresar);
        btnIngresar.addActionListener(e -> controller.iniciarSesion());
        form.add(btnIngresar, gbc);

        return form;
    }

    private void agregarFila(JPanel form, GridBagConstraints gbc, String etiqueta, JComponent campo) {
        gbc.insets = new Insets(gbc.gridy == 0 ? 0 : 12, 0, 4, 0);
        JLabel lbl = TemaCIMA.crearEtiquetaCampo(etiqueta);
        form.add(lbl, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        campo.setPreferredSize(new Dimension(ANCHO_CAMPO, TemaCIMA.ALTO_CAMPO));
        form.add(campo, gbc);

        gbc.gridy++;
    }

    public String getUsuario() {
        return txtUsuario.getText();
    }

    public String getClave() {
        return String.valueOf(txtClave.getPassword());
    }

    public void mostrarError(String mensaje) {
        MensajesUI.error(this, mensaje);
    }

    public void mostrarBienvenida(String nombreCompleto) {
        MensajesUI.exito(this, "Bienvenido " + nombreCompleto + "!");
    }

    public void abrirPagPrincipal() {
        PagPrincipal pag = new PagPrincipal();
        pag.setVisible(true);
        pag.setLocationRelativeTo(null);
    }

    public void cerrar() {
        dispose();
    }
}
