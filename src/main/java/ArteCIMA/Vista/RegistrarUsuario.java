package ArteCIMA.Vista;

import ArteCIMA.DAO.RegistrarUsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.JOptionPane;


public class RegistrarUsuario extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName());

    public RegistrarUsuario() {
        initComponents();
        this.setLocationRelativeTo(null);
        setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        txtNuevoUsuario2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNombre1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtClave2 = new javax.swing.JPasswordField();
        btnRegistrarUsuario = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        txtClave1 = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        comboRol = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel7.setText("Rol de usuario:");

        txtNuevoUsuario2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel10.setText("Nombre completo:");

        txtNombre1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(533, 412));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 136, -1, -1));

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel3.setText("Contraseña:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 304, -1, -1));

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel4.setText("Confirmación contraseña:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 350, -1, -1));

        txtClave2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtClave2.setPreferredSize(new java.awt.Dimension(64, 22));
        jPanel1.add(txtClave2, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 347, 136, -1));

        btnRegistrarUsuario.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnRegistrarUsuario.setText("Registrar usuario");
        btnRegistrarUsuario.addActionListener(this::btnRegistrarUsuarioActionPerformed);
        jPanel1.add(btnRegistrarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 401, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loguito.jpeg"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 28, -1, -1));

        jLabel6.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel6.setText("Rol de usuario:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 143, -1, -1));

        jLabel2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel2.setText("Nombre completo:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 181, -1, -1));

        jLabel8.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel8.setText("Correo:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 263, -1, -1));

        btnCancelar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 401, 136, -1));

        txtClave1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtClave1.setPreferredSize(new java.awt.Dimension(64, 22));
        jPanel1.add(txtClave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 301, 136, -1));

        jLabel9.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel9.setText("Nombre de usuario:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));

        txtCorreo.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtCorreo.setPreferredSize(new java.awt.Dimension(64, 22));
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 133, -1));

        comboRol.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "Administrador", "Coordinador", "Instructor", "Administrativo", "Contabilidad", "Auxiliar", " " }));
        jPanel1.add(comboRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, -1, -1));

        txtNombre.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 133, 22));

        txtUsuario.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtUsuario.setPreferredSize(new java.awt.Dimension(64, 22));
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, 133, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarUsuarioActionPerformed
    String rol = comboRol.getSelectedItem().toString();
    String nombreCompleto = txtNombre.getText();
    String usuario = txtUsuario.getText();
    String correo = txtCorreo.getText();

    String pass1 = String.valueOf(txtClave1.getPassword());
    String pass2 = String.valueOf(txtClave2.getPassword());

    if (rol.equals("Selecciona")) {
        JOptionPane.showMessageDialog(this, "Debes seleccionar un rol");
        return;
    }

    if (nombreCompleto.isEmpty() || usuario.isEmpty() || correo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
        return;
    }

    if (!pass1.equals(pass2)) {
        JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
        return;
    }

    String hash = BCrypt.hashpw(pass1, BCrypt.gensalt(12));
    
    RegistrarUsuarioDAO dao = new RegistrarUsuarioDAO();
    boolean ok = dao.registrarUsuario(rol, nombreCompleto, usuario, correo, hash);

    if (ok) {
        JOptionPane.showMessageDialog(this,
                "Usuario registrado con éxito",
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);

        PagPrincipal pag = new PagPrincipal();
        pag.setVisible(true);
        pag.setLocationRelativeTo(null);

          this.dispose();

    } else {
        JOptionPane.showMessageDialog(this,
                "Error al registrar usuario.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarUsuarioActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
    this.dispose(); 

    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new RegistrarUsuario().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrarUsuario;
    private javax.swing.JComboBox<String> comboRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtClave1;
    private javax.swing.JPasswordField txtClave2;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNuevoUsuario2;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
