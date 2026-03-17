package ArteCIMA.Vista;

import ArteCIMA.Modelo.Instructor;
import ArteCIMA.Service.ConsultarInstructorService;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ConsultarInstructores extends javax.swing.JFrame {

    private ConsultarInstructorService instructorService;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ConsultarInstructores.class.getName());

    private Integer idInstructorSeleccionado = null;

    public ConsultarInstructores() {
        initComponents();
        this.setLocationRelativeTo(null);
        setResizable(false);

        this.instructorService = new ConsultarInstructorService();

        cargarTabla(null, null, null, null);

        tblInstructores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });

        comboDiscapacidad.addActionListener(e -> {
            boolean tiene = comboDiscapacidad.getSelectedItem().toString().equalsIgnoreCase("Sí");
            txtTipoDiscapacidad.setEnabled(tiene);
            if (!tiene) {
                txtTipoDiscapacidad.setText("");
            }
        });
    }

    private void cargarTabla(String idInstructor, String numDocumento, String nombreCompleto, Boolean discapacidad) {
        DefaultTableModel modelo = (DefaultTableModel) tblInstructores.getModel();
        modelo.setRowCount(0);

        List<Instructor> lista = instructorService.listarInstructores(idInstructor, numDocumento, nombreCompleto, discapacidad);

        for (Instructor i : lista) {
            Object[] fila = new Object[]{
                i.getIdInstructor(),
                i.getTipoDocumento(),
                i.getNumDocumento(),
                i.getNombreCompleto(),
                i.getTelefono(),
                i.getCorreo(),
                i.getDiscapacidad() ? "Sí" : "No",
                i.getTipoDiscapacidad(),
                i.getEspecialidadArtistica(),
                i.getValorPorClase()
            };
            modelo.addRow(fila);
        }

        tblInstructores.clearSelection();
        this.idInstructorSeleccionado = null;
    }

    private void cargarDatosSeleccionados() {
        int fila = tblInstructores.getSelectedRow();
        if (fila >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) tblInstructores.getModel();
            Object idObj = modelo.getValueAt(fila, 0);

            try {
                int id = Integer.parseInt(idObj.toString());
                this.idInstructorSeleccionado = id;

                Instructor instructor = this.instructorService.obtenerInstructorPorId(id);
                if (instructor != null) {
                    comboTipoDoc.setSelectedItem(instructor.getTipoDocumento());
                    txtNumDoc.setText(instructor.getNumDocumento());
                    txtNombre.setText(instructor.getNombreCompleto());
                    txtTelefono.setText(instructor.getTelefono());
                    txtCorreo.setText(instructor.getCorreo());

                    String discapacidadStr = (instructor.getDiscapacidad() != null && instructor.getDiscapacidad()) ? "Sí" : "No";
                    comboDiscapacidad.setSelectedItem(discapacidadStr);

                    txtTipoDiscapacidad.setText(instructor.getTipoDiscapacidad() != null ? instructor.getTipoDiscapacidad() : "");
                    txtEspecialidad.setText(instructor.getEspecialidadArtistica() != null ? instructor.getEspecialidadArtistica() : "");
                    txtValor.setText(instructor.getValorPorClase() != null ? instructor.getValorPorClase().toString() : "");
                }

            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Error al cargar datos de fila seleccionada", e);
            }
        }
    }

    private void limpiarCampos() {
  
    comboTipoDoc.setSelectedIndex(0);
    txtNumDoc.setText("");
    txtNombre.setText("");
    txtTelefono.setText("");
    txtCorreo.setText("");
       
    comboDiscapacidad.setSelectedIndex(0); 
    txtTipoDiscapacidad.setText("");
    txtTipoDiscapacidad.setEnabled(false); 
    
   
    txtEspecialidad.setText("");
    txtValor.setText("");
       
    tblInstructores.clearSelection();
    this.idInstructorSeleccionado = null;
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        comboTipoDoc = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        comboDiscapacidad = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtTipoDiscapacidad = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInstructores = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtCorreo = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1365, 707));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("CONSULTA DE INSTRUCTORES");

        jPanel2.setBackground(new java.awt.Color(218, 224, 229));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel3.setText("Tipo de documento:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 26, -1, -1));

        comboTipoDoc.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "TI", "CC", "CE", "" }));
        jPanel2.add(comboTipoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel4.setText("Número de documento:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(314, 26, -1, -1));

        txtNumDoc.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jPanel2.add(txtNumDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 149, -1));

        jLabel5.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel5.setText("Nombre completo:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        txtNombre.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 390, -1));

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel7.setText("Teléfono:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, -1, -1));

        txtTelefono.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jPanel2.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 152, -1));

        jLabel8.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel8.setText("Correo:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 70, -1, -1));

        jLabel9.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel9.setText("Discapacidad:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 26, -1, -1));

        comboDiscapacidad.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboDiscapacidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "Sí", "No" }));
        jPanel2.add(comboDiscapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 20, 128, -1));

        jLabel10.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel10.setText("Tipo de discapacidad:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 30, -1, -1));

        txtTipoDiscapacidad.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jPanel2.add(txtTipoDiscapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 30, 140, -1));

        jLabel13.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel13.setText("Especialidad artística:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        txtEspecialidad.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jPanel2.add(txtEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 320, -1));

        jLabel14.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel14.setText("Valor por clase:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, -1, -1));

        txtValor.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jPanel2.add(txtValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, 150, -1));

        btnEditar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);
        jPanel2.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 90, -1));

        btnVolver.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        jPanel2.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 150, 90, -1));

        btnEliminar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, 90, -1));

        tblInstructores.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        tblInstructores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_instructor", "Tipo_documento", "Num_documento", "Nombre_completo", "Especialidad artística", "Teléfono", "Correo", "Discapacidad", "Tipo_discapacidad", "Valor por clase"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblInstructores);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 187, 1171, 230));

        txtBuscar.setBackground(new java.awt.Color(205, 199, 199));
        txtBuscar.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(51, 51, 51));
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 106, 270, -1));

        jLabel15.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel15.setText("Buscar:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 110, -1, -1));

        btnBuscar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 90, -1));

        txtCorreo.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jPanel2.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 70, 270, -1));

        btnLimpiar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        jPanel2.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 150, 90, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(277, 277, 277)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
   if (idInstructorSeleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un instructor de la tabla.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (comboTipoDoc.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona el tipo de documento.");
            return;
        }

        if (txtNumDoc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El número de documento es obligatorio.");
            return;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        Instructor instructor = new Instructor();
        instructor.setIdInstructor(idInstructorSeleccionado);
        instructor.setTipoDocumento(comboTipoDoc.getSelectedItem().toString());
        instructor.setNumDocumento(txtNumDoc.getText().trim());
        instructor.setNombreCompleto(txtNombre.getText().trim());
        instructor.setTelefono(txtTelefono.getText().trim());
        instructor.setCorreo(txtCorreo.getText().trim());
        instructor.setEspecialidadArtistica(txtEspecialidad.getText().trim());

        String discapacidadSeleccionada = comboDiscapacidad.getSelectedItem().toString();
        boolean tieneDiscapacidad = discapacidadSeleccionada.equals("Sí");
        instructor.setDiscapacidad(tieneDiscapacidad);

        if (tieneDiscapacidad) {
            if (txtTipoDiscapacidad.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debes especificar el tipo de discapacidad.");
                return;
            }
            instructor.setTipoDiscapacidad(txtTipoDiscapacidad.getText().trim());
        } else {
            instructor.setTipoDiscapacidad(null);
        }

        String valorText = txtValor.getText().trim();
        if (!valorText.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorText);
                if (valor < 0) {
                    JOptionPane.showMessageDialog(this, "El valor por clase no puede ser negativo.");
                    return;
                }
                instructor.setValorPorClase(valor);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El valor por clase debe ser numérico.");
                return;
            }
        } else {
            instructor.setValorPorClase(null);
        }

        boolean actualizado = instructorService.actualizarInstructor(instructor);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Instructor actualizado correctamente.");
            cargarTabla(null, null, null, null);
            tblInstructores.clearSelection();
            idInstructorSeleccionado = null;
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el instructor.");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        PagPrincipal p = new PagPrincipal();  
        p.setVisible(true);                    
        this.dispose();                                   
  
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      if (idInstructorSeleccionado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un instructor de la tabla para poder eliminarlo.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este instructor?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            boolean eliminado = instructorService.eliminarInstructor(idInstructorSeleccionado);

            if (eliminado) {
                JOptionPane.showMessageDialog(
                        this,
                        "Instructor eliminado correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarTabla(null, null, null, null);
                limpiarCampos();
                idInstructorSeleccionado = null;
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar el instructor.\nPuede estar relacionado con otras tablas.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
     try {
            String textoBusqueda = txtBuscar.getText().trim();

            if (textoBusqueda.isEmpty()) {
                cargarTabla(null, null, null, null); // Cargar todos si el campo de búsqueda está vacío
                return;
            }
            
            String filtro = textoBusqueda;
            
            // Llamar a cargar tabla pasando el mismo filtro para NumDocumento y Nombre
            cargarTabla(null, // ID no se usa con este campo
                        filtro, // Criterio 1: Número de documento (se busca si contiene)
                        filtro, // Criterio 2: Nombre Completo (se busca si contiene)
                        null); // Discapacidad (no se filtra aquí)

            if (tblInstructores.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron instructores con esos criterios.",
                        "Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar instructores: ", e);
            JOptionPane.showMessageDialog(this,
                    "Error al buscar instructores: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        txtBuscar.setText(""); 
        cargarTabla(null, null, null, null);
    }//GEN-LAST:event_btnLimpiarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ConsultarInstructores().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboDiscapacidad;
    private javax.swing.JComboBox<String> comboTipoDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInstructores;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoDiscapacidad;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
