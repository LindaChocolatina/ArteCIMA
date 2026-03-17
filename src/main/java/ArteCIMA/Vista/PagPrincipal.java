package ArteCIMA.Vista;



public class PagPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PagPrincipal.class.getName());

    public PagPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnInsertarEstudiante = new javax.swing.JButton();
        btnInsertarInstructor = new javax.swing.JButton();
        btnInsertarTaller = new javax.swing.JButton();
        btnConsultarEstudiantes = new javax.swing.JButton();
        btnConsultarInstructores = new javax.swing.JButton();
        btnConsultarTalleres = new javax.swing.JButton();
        btnRegistrarUsuario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setPreferredSize(new java.awt.Dimension(1920, 1080));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Pristina", 3, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Arte para todos");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnInsertarEstudiante.setFont(new java.awt.Font("NSimSun", 0, 14)); // NOI18N
        btnInsertarEstudiante.setText("Registrar estudiante");
        btnInsertarEstudiante.addActionListener(this::btnInsertarEstudianteActionPerformed);

        btnInsertarInstructor.setFont(new java.awt.Font("NSimSun", 0, 14)); // NOI18N
        btnInsertarInstructor.setText("Registrar instructor");
        btnInsertarInstructor.addActionListener(this::btnInsertarInstructorActionPerformed);

        btnInsertarTaller.setFont(new java.awt.Font("NSimSun", 0, 14)); // NOI18N
        btnInsertarTaller.setText("Registrar taller");
        btnInsertarTaller.addActionListener(this::btnInsertarTallerActionPerformed);

        btnConsultarEstudiantes.setFont(new java.awt.Font("NSimSun", 0, 14)); // NOI18N
        btnConsultarEstudiantes.setText("Consultar estudiantes");
        btnConsultarEstudiantes.addActionListener(this::btnConsultarEstudiantesActionPerformed);

        btnConsultarInstructores.setFont(new java.awt.Font("NSimSun", 0, 14)); // NOI18N
        btnConsultarInstructores.setText("Consultar instructores");
        btnConsultarInstructores.addActionListener(this::btnConsultarInstructoresActionPerformed);

        btnConsultarTalleres.setFont(new java.awt.Font("NSimSun", 0, 14)); // NOI18N
        btnConsultarTalleres.setText("Consultar talleres");
        btnConsultarTalleres.addActionListener(this::btnConsultarTalleresActionPerformed);

        btnRegistrarUsuario.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnRegistrarUsuario.setText("Registrar nuevo usuario");
        btnRegistrarUsuario.addActionListener(this::btnRegistrarUsuarioActionPerformed);

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLayout.createSequentialGroup()
                .addContainerGap(146, Short.MAX_VALUE)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addComponent(btnInsertarEstudiante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInsertarInstructor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInsertarTaller)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConsultarEstudiantes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConsultarInstructores)
                        .addGap(18, 18, 18)
                        .addComponent(btnConsultarTalleres))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(368, 368, 368)
                        .addComponent(jLabel1))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addComponent(jLabel2)))
                .addGap(148, 148, 148))
            .addGroup(fondoLayout.createSequentialGroup()
                .addGap(615, 615, 615)
                .addComponent(btnRegistrarUsuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(45, 45, 45)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertarEstudiante)
                    .addComponent(btnInsertarInstructor)
                    .addComponent(btnInsertarTaller)
                    .addComponent(btnConsultarEstudiantes)
                    .addComponent(btnConsultarInstructores)
                    .addComponent(btnConsultarTalleres))
                .addGap(56, 56, 56)
                .addComponent(btnRegistrarUsuario)
                .addContainerGap(174, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 1359, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarEstudianteActionPerformed
    InsertarEstudiante ie = new InsertarEstudiante();
    ie.setVisible(true);

    }//GEN-LAST:event_btnInsertarEstudianteActionPerformed

    private void btnInsertarInstructorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarInstructorActionPerformed
    InsertarInstructor ie = new InsertarInstructor();
    ie.setVisible(true);
    }//GEN-LAST:event_btnInsertarInstructorActionPerformed

    private void btnInsertarTallerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarTallerActionPerformed
    InsertarTaller ie = new InsertarTaller();
    ie.setVisible(true);
    }//GEN-LAST:event_btnInsertarTallerActionPerformed

    private void btnConsultarEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEstudiantesActionPerformed
    ConsultarEstudiantes ie = new ConsultarEstudiantes();
    ie.setVisible(true);
    }//GEN-LAST:event_btnConsultarEstudiantesActionPerformed

    private void btnConsultarInstructoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarInstructoresActionPerformed
    ConsultarInstructores ie = new ConsultarInstructores();
    ie.setVisible(true);
    }//GEN-LAST:event_btnConsultarInstructoresActionPerformed

    private void btnConsultarTalleresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarTalleresActionPerformed
    ConsultarTalleres ie = new ConsultarTalleres();
    ie.setVisible(true);
    }//GEN-LAST:event_btnConsultarTalleresActionPerformed

    private void btnRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarUsuarioActionPerformed
    RegistrarUsuario iu = new RegistrarUsuario();
    iu.setVisible(true);

    }//GEN-LAST:event_btnRegistrarUsuarioActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new PagPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultarEstudiantes;
    private javax.swing.JButton btnConsultarInstructores;
    private javax.swing.JButton btnConsultarTalleres;
    private javax.swing.JButton btnInsertarEstudiante;
    private javax.swing.JButton btnInsertarInstructor;
    private javax.swing.JButton btnInsertarTaller;
    private javax.swing.JButton btnRegistrarUsuario;
    private javax.swing.JPanel fondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
