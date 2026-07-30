package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorInstructor;
import ArteCIMA.Modelo.Instructor;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.SesionUsuario;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.TextoUtil;
import ArteCIMA.Util.PermisosUI;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class FRMInstructor extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMInstructor.class.getName());

    private final ControladorInstructor controlador = new ControladorInstructor();
    private Integer idInstructorSeleccionado = null;

    public FRMInstructor() {
        initComponents();
        ArteCIMA.Util.UIFormulario.prepararModulo(this, jPanel1, jPanel2, jLabel1, jLabel2, tblInstructores, Modulo.INSTRUCTORES);
        btnEditar.setActionCommand("Modificar");

        if (!PermisosUI.verificarAccesoModulo(this, Modulo.INSTRUCTORES)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }
        PermisosUI.aplicarPermisosCrud(Modulo.INSTRUCTORES, btnInsertar, btnEditar, btnEliminar);
        // Reaplicar tras el layout: el instructor no debe editar datos (en especial valor por clase).
        PermisosUI.aplicarModoCampos(jPanel2, Modulo.INSTRUCTORES, txtBuscar);
        aplicarBloqueoValorPorClase();

        cargarTabla(null, null, null, null);

        tblInstructores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });

        comboDiscapacidad.addActionListener(e -> {
            boolean puedeEditar = PermisosUI.puedeEditarCampos(Modulo.INSTRUCTORES);
            boolean tiene = comboDiscapacidad.getSelectedItem().toString().equalsIgnoreCase("Sí");
            txtTipoDiscapacidad.setEnabled(puedeEditar && tiene);
            if (!tiene) {
                txtTipoDiscapacidad.setText("");
            }
        });
    }

    /** Valor por clase es dato administrativo/financiero: el rol Instructor nunca lo edita. */
    private void aplicarBloqueoValorPorClase() {
        boolean permitir = PermisosUI.puedeEditarCampos(Modulo.INSTRUCTORES)
                && !esRolInstructor();
        txtValor.setEditable(permitir);
        txtValor.setEnabled(permitir);
        txtValor.setFocusable(permitir);
    }

    private static boolean esRolInstructor() {
        String rol = SesionUsuario.getNombreRol();
        return rol != null && rol.equalsIgnoreCase("Instructor");
    }

    private void cargarTabla(String idInstructor, String numDocumento, String nombreCompleto, Boolean discapacidad) {
        DefaultTableModel modelo = (DefaultTableModel) tblInstructores.getModel();
        modelo.setRowCount(0);

        List<Instructor> lista = controlador.listar(idInstructor, numDocumento, nombreCompleto, discapacidad);

        for (Instructor i : lista) {
            Object[] fila = new Object[]{
                i.getIdInstructor(),
                i.getTipoDocumento(),
                i.getNumDocumento(),
                i.getNombreCompleto(),
                i.getEspecialidadArtistica(),
                i.getTelefono(),
                i.getCorreo(),
                Boolean.TRUE.equals(i.getDiscapacidad()) ? "Sí" : "No",
                i.getTipoDiscapacidad(),
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

                Instructor instructor = controlador.buscar(id);
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
                    aplicarBloqueoValorPorClase();
                }

            } catch (NumberFormatException e) {
                logger.log(java.util.logging.Level.WARNING, "Error al cargar datos de fila seleccionada", e);
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
    aplicarBloqueoValorPorClase();
       
    tblInstructores.clearSelection();
    this.idInstructorSeleccionado = null;
}

    private Instructor obtenerInstructor(Integer idInstructor) throws NumberFormatException {
        double valorClase = Double.parseDouble(txtValor.getText().trim());
        boolean tieneDiscapacidad = comboDiscapacidad.getSelectedItem().toString().equalsIgnoreCase("Sí");

        Instructor instructor = new Instructor();
        if (idInstructor != null) {
            instructor.setIdInstructor(idInstructor);
        }
        instructor.setTipoDocumento(comboTipoDoc.getSelectedItem().toString());
        instructor.setNumDocumento(txtNumDoc.getText().trim());
        instructor.setNombreCompleto(txtNombre.getText().trim());
        instructor.setTelefono(txtTelefono.getText().trim());
        instructor.setCorreo(txtCorreo.getText().trim());
        instructor.setDiscapacidad(tieneDiscapacidad);
        instructor.setTipoDiscapacidad(tieneDiscapacidad ? txtTipoDiscapacidad.getText().trim() : null);
        instructor.setEspecialidadArtistica(txtEspecialidad.getText().trim());
        instructor.setValorPorClase(valorClase);
        return instructor;
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
        btnInsertar = new javax.swing.JButton();
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

        jLabel2.setFont(new java.awt.Font("Nunito Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("GESTIÓN DE INSTRUCTORES");

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

        btnInsertar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnInsertar.setText("Insertar");
        btnInsertar.setActionCommand("Insertar");
        btnInsertar.addActionListener(this::btnInsertarActionPerformed);
        jPanel2.add(btnInsertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 90, -1));

        btnEditar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setActionCommand("Modificar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);
        jPanel2.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 150, 90, -1));

        btnVolver.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        jPanel2.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 150, 90, -1));

        btnEliminar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setActionCommand("Eliminar");
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
                .addContainerGap(41, Short.MAX_VALUE)
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

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        try {
            Instructor nuevoInstructor = obtenerInstructor(null);
            if (controlador.controlarAccion(evt, nuevoInstructor)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla(null, null, null, null);
                limpiarCampos();
            } else {
                MensajesUI.error(this, controlador.getUltimoMensaje());
            }
        } catch (NumberFormatException e) {
            MensajesUI.error(this, "El valor por clase debe ser un número válido.");
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al guardar instructor", e);
            MensajesUI.error(this, "Ocurrió un error inesperado: " + e.getMessage());
        }
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
   if (idInstructorSeleccionado == null) {
            MensajesUI.seleccionarEnTabla(this, "instructor");
            return;
        }

        if (comboTipoDoc.getSelectedIndex() == 0) {
            MensajesUI.advertencia(this, "Seleccione el tipo de documento.");
            return;
        }

        if (txtNumDoc.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "El número de documento es obligatorio.");
            return;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "El nombre es obligatorio.");
            return;
        }

        boolean tieneDiscapacidad = comboDiscapacidad.getSelectedItem().toString().equals("Sí");
        if (tieneDiscapacidad && txtTipoDiscapacidad.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "Debe especificar el tipo de discapacidad.");
            return;
        }

        String valorText = txtValor.getText().trim();
        if (!valorText.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorText);
                if (valor < 0) {
                    MensajesUI.advertencia(this, "El valor por clase no puede ser negativo.");
                    return;
                }
            } catch (NumberFormatException e) {
                MensajesUI.error(this, "El valor por clase debe ser numérico.");
                return;
            }
        }

        try {
            Instructor instructor = obtenerInstructor(idInstructorSeleccionado);
            if (controlador.controlarAccion(evt, instructor)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla(null, null, null, null);
                tblInstructores.clearSelection();
                idInstructorSeleccionado = null;
            } else {
                MensajesUI.error(this, controlador.getUltimoMensaje());
            }
        } catch (NumberFormatException e) {
            MensajesUI.error(this, "El valor por clase debe ser un número válido.");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        MensajesUI.volverAlMenu(this);
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      if (idInstructorSeleccionado == null) {
            MensajesUI.seleccionarEnTabla(this, "instructor");
            return;
        }

        if (!MensajesUI.confirmarEliminacion(this, "este instructor")) {
            return;
        }

            Instructor instructor = new Instructor();
            instructor.setIdInstructor(idInstructorSeleccionado);
            if (controlador.controlarAccion(evt, instructor)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla(null, null, null, null);
                limpiarCampos();
                idInstructorSeleccionado = null;
            } else {
                MensajesUI.error(this, controlador.getUltimoMensaje());
            }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            String textoBusqueda = TextoUtil.criterioBusqueda(txtBuscar);

            if (textoBusqueda.isEmpty()) {
                cargarTabla(null, null, null, null);
                return;
            }

            if (textoBusqueda.matches("\\d+")) {
                cargarTabla(textoBusqueda, null, null, null);
            } else {
                cargarTabla(null, textoBusqueda, textoBusqueda, null);
            }

            if (tblInstructores.getRowCount() == 0) {
                MensajesUI.sinResultadosBusqueda(this);
            }

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al buscar instructores: ", e);
            MensajesUI.error(this, "Error al buscar instructores: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        TextoUtil.restaurarPlaceholderBuscar(txtBuscar); 
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
        java.awt.EventQueue.invokeLater(() -> new FRMInstructor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
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
