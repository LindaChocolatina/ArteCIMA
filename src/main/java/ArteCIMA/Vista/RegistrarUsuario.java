package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorUsuario;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;
import ArteCIMA.Modelo.Usuario;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.PermisosUI;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Registro de usuarios — formulario vertical completo y funcional.
 */
public class RegistrarUsuario extends JFrame {

    private static final int ANCHO_VENTANA = 420;
    private static final int ANCHO_CAMPO = 300;

    private final ControladorUsuario controlador = new ControladorUsuario();

    private final JTextField txtNombre = new JTextField();
    private final JTextField txtUsuario = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JPasswordField txtClave1 = new JPasswordField();
    private final JPasswordField txtClave2 = new JPasswordField();
    private final JComboBox<String> comboRol = new JComboBox<>(new String[]{
        "Administrador", "Coordinador", "Instructor", "Administrativo", "Contabilidad", "Auxiliar"
    });
    private final JButton btnRegistrarUsuario = new JButton("Registrar");
    private final JButton btnCancelar = new JButton("Cancelar");

    public RegistrarUsuario() {
        construirInterfaz();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("ArteCIMA - Registrar Usuario");
        TemaCIMA.aplicarIcono(this);
        setLocationRelativeTo(null);

        if (!PermisosUI.verificarAccesoModulo(this, Modulo.REGISTRAR_USUARIO)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }
        btnRegistrarUsuario.setEnabled(SesionUsuario.puedeRegistrarUsuarios());
    }

    private void construirInterfaz() {
        JPanel tarjeta = TemaCIMA.crearTarjetaFlotante();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                tarjeta.getBorder(),
                new EmptyBorder(24, 40, 28, 40)));

        tarjeta.add(crearEncabezado());
        tarjeta.add(Box.createVerticalStrut(12));
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
        header.setMaximumSize(new Dimension(ANCHO_VENTANA, 160));

        ImageIcon logo = TemaCIMA.logoEscalado(76);
        if (logo != null) {
            JLabel lblLogo = new JLabel(logo);
            lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
            header.add(lblLogo);
            header.add(Box.createVerticalStrut(8));
        }

        JLabel lblTitulo = new JLabel("Registro de Usuarios");
        lblTitulo.setFont(TemaCIMA.FUENTE_SUBTITULO);
        lblTitulo.setForeground(TemaCIMA.AZUL_REY);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(lblTitulo);
        header.add(Box.createVerticalStrut(6));

        JLabel franja = new JLabel(" ");
        franja.setAlignmentX(Component.CENTER_ALIGNMENT);
        franja.setMaximumSize(new Dimension(110, 4));
        franja.setPreferredSize(new Dimension(110, 4));
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
                new EmptyBorder(20, 22, 20, 22)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 4, 0);

        TemaCIMA.estilizarCampo(txtNombre);
        TemaCIMA.estilizarCampo(txtUsuario);
        TemaCIMA.estilizarCampo(txtCorreo);
        TemaCIMA.estilizarCampo(txtClave1);
        TemaCIMA.estilizarCampo(txtClave2);
        TemaCIMA.estilizarCombo(comboRol);

        agregarFila(form, gbc, "Nombre completo:", txtNombre);
        agregarFila(form, gbc, "Usuario:", txtUsuario);
        agregarFila(form, gbc, "Correo:", txtCorreo);
        agregarFila(form, gbc, "Contraseña:", txtClave1);
        agregarFila(form, gbc, "Confirmar contraseña:", txtClave2);
        agregarFila(form, gbc, "Rol:", comboRol);

        gbc.gridy++;
        gbc.insets = new Insets(14, 0, 0, 0);

        JPanel filaBotones = new JPanel(new java.awt.GridLayout(1, 2, 12, 0));
        filaBotones.setOpaque(false);
        TemaCIMA.estilizarBotonExito(btnRegistrarUsuario);
        TemaCIMA.estilizarBotonPeligro(btnCancelar);
        btnRegistrarUsuario.addActionListener(this::btnRegistrarUsuarioActionPerformed);
        btnCancelar.addActionListener(e -> dispose());
        filaBotones.add(btnRegistrarUsuario);
        filaBotones.add(btnCancelar);
        form.add(filaBotones, gbc);

        return form;
    }

    private void agregarFila(JPanel form, GridBagConstraints gbc, String etiqueta, JComponent campo) {
        gbc.insets = new Insets(gbc.gridy == 0 ? 0 : 10, 0, 4, 0);
        JLabel lbl = TemaCIMA.crearEtiquetaCampo(etiqueta);
        form.add(lbl, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        campo.setPreferredSize(new Dimension(ANCHO_CAMPO, TemaCIMA.ALTO_CAMPO));
        form.add(campo, gbc);

        gbc.gridy++;
    }

    private void btnRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();
        String clave1 = String.valueOf(txtClave1.getPassword());
        String clave2 = String.valueOf(txtClave2.getPassword());
        String rol = (String) comboRol.getSelectedItem();

        if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() || clave1.isEmpty()) {
            MensajesUI.advertencia(this, "Complete todos los campos.");
            return;
        }
        if (!clave1.equals(clave2)) {
            MensajesUI.advertencia(this, "Las contraseñas no coinciden.");
            return;
        }

        Usuario u = new Usuario();
        u.setNombreCompleto(nombre);
        u.setUsuario(usuario);
        u.setCorreo(correo);
        u.setRol(rol);

        if (controlador.registrar(u, clave1, clave2)) {
            MensajesUI.exito(this, "Usuario registrado correctamente.");
            limpiarCampos();
        } else {
            MensajesUI.error(this, controlador.getUltimoMensaje());
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtUsuario.setText("");
        txtCorreo.setText("");
        txtClave1.setText("");
        txtClave2.setText("");
        comboRol.setSelectedIndex(0);
    }
}
