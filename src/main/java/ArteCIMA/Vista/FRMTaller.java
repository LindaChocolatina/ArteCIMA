package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorTaller;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.Taller;
import ArteCIMA.Util.HorarioUtil;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.PermisosUI;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class FRMTaller extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMTaller.class.getName());
    private final ControladorTaller controlador = new ControladorTaller();

    public FRMTaller() {
        initComponents();
        ArteCIMA.Util.UIFormulario.prepararModulo(this, jPanel1, jPanel2, jLabel1, jLabel2, tblTalleres);
        btnEditar.setActionCommand("Modificar");

        if (!PermisosUI.verificarAccesoModulo(this, Modulo.TALLERES)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }
        PermisosUI.aplicarPermisosCrud(Modulo.TALLERES, btnInsertar, btnEditar, btnEliminar);

        comboDia.setModel(new javax.swing.DefaultComboBoxModel<>(HorarioUtil.DIAS));
        comboHoraInicio.setModel(new javax.swing.DefaultComboBoxModel<>(HorarioUtil.HORAS));
        comboHoraFin.setModel(new javax.swing.DefaultComboBoxModel<>(HorarioUtil.HORAS));
        cargarTabla();

        tblTalleres.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarDatosFilaSeleccionada();
            }
        });
    }

    private void cargarTabla() {
        List<Taller> lista = controlador.listar();
        DefaultTableModel model = (DefaultTableModel) tblTalleres.getModel();
        model.setRowCount(0);

        for (Taller t : lista) {
            model.addRow(new Object[]{
                t.getIdTaller(),
                t.getNombre(),
                t.getTipoArte(),
                t.getHorario(),
                t.getIdMetodo(),
                t.getIdInstructor(),
                t.getIdAlianza()
            });
        }
    }

    private void cargarDatosFilaSeleccionada() {
        int fila = tblTalleres.getSelectedRow();
        if (fila == -1) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblTalleres.getModel();
        txtNombre.setText(valorCelda(model, fila, 1));
        comboTipoArte.setSelectedItem(model.getValueAt(fila, 2) == null ? "Selecciona" : model.getValueAt(fila, 2).toString());
        HorarioUtil.aplicar(valorCelda(model, fila, 3), comboDia, comboHoraInicio, comboHoraFin);
        txtIdMetodo.setText(valorCelda(model, fila, 4));
        txtIdInstructor.setText(valorCelda(model, fila, 5));
        txtIdAlianza.setText(valorCelda(model, fila, 6));
    }

    private String valorCelda(DefaultTableModel model, int fila, int col) {
        Object valor = model.getValueAt(fila, col);
        return valor == null ? "" : valor.toString();
    }

    private Taller obtenerTallerDesdeCampos(Integer idTaller) {
        Taller taller = new Taller();
        if (idTaller != null) {
            taller.setIdTaller(idTaller);
        }
        taller.setNombre(txtNombre.getText().trim());
        taller.setTipoArte(comboTipoArte.getSelectedItem().toString());
        taller.setHorario(obtenerHorarioSeleccionado());
        taller.setIdMetodo(txtIdMetodo.getText().trim().isEmpty() ? null : Integer.parseInt(txtIdMetodo.getText().trim()));
        taller.setIdInstructor(txtIdInstructor.getText().trim().isEmpty() ? null : Integer.parseInt(txtIdInstructor.getText().trim()));
        taller.setIdAlianza(txtIdAlianza.getText().trim().isEmpty() ? null : Integer.parseInt(txtIdAlianza.getText().trim()));
        return taller;
    }

    private String obtenerHorarioSeleccionado() {
        return HorarioUtil.construir(
                comboDia.getSelectedItem().toString(),
                comboHoraInicio.getSelectedItem().toString(),
                comboHoraFin.getSelectedItem().toString());
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "El nombre del taller es obligatorio.");
            return false;
        }
        if (comboTipoArte.getSelectedIndex() == 0) {
            MensajesUI.advertencia(this, "Seleccione el tipo de arte (Plástico, Escénico o Circense).");
            return false;
        }
        if (obtenerHorarioSeleccionado().isEmpty()) {
            MensajesUI.advertencia(this, "Seleccione el día y el horario del taller.");
            return false;
        }
        if (comboHoraInicio.getSelectedIndex() > 0 && comboHoraFin.getSelectedIndex() > 0
                && comboHoraInicio.getSelectedIndex() >= comboHoraFin.getSelectedIndex()) {
            MensajesUI.advertencia(this, "La hora de fin debe ser posterior a la hora de inicio.");
            return false;
        }
        try {
            if (!txtIdMetodo.getText().trim().isEmpty()) {
                Integer.parseInt(txtIdMetodo.getText().trim());
            }
            if (!txtIdInstructor.getText().trim().isEmpty()) {
                Integer.parseInt(txtIdInstructor.getText().trim());
            }
            if (!txtIdAlianza.getText().trim().isEmpty()) {
                Integer.parseInt(txtIdAlianza.getText().trim());
            }
        } catch (NumberFormatException e) {
            MensajesUI.error(this, "Los IDs (método, instructor, alianza) deben ser números enteros.");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        comboTipoArte.setSelectedIndex(0);
        comboDia.setSelectedIndex(0);
        comboHoraInicio.setSelectedIndex(0);
        comboHoraFin.setSelectedIndex(0);
        txtIdMetodo.setText("");
        txtIdInstructor.setText("");
        txtIdAlianza.setText("");
        txtBuscar.setText("");
        tblTalleres.clearSelection();
    }

    private void seleccionarFilaPorId(int idBuscado) {
        DefaultTableModel model = (DefaultTableModel) tblTalleres.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                int idFila = Integer.parseInt(model.getValueAt(i, 0).toString());
                if (idFila == idBuscado) {
                    tblTalleres.setRowSelectionInterval(i, i);
                    tblTalleres.scrollRectToVisible(tblTalleres.getCellRect(i, 0, true));
                    break;
                }
            } catch (NumberFormatException ex) {
                logger.warning("ID inválido en tabla de talleres");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboTipoArte = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        comboDia = new javax.swing.JComboBox<>();
        comboHoraInicio = new javax.swing.JComboBox<>();
        comboHoraFin = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtIdMetodo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtIdInstructor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtIdAlianza = new javax.swing.JTextField();
        btnInsertar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTalleres = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1365, 707));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Nunito Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("GESTIÓN DE TALLERES");

        jPanel2.setBackground(new java.awt.Color(218, 224, 229));

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel3.setText("Nombre:");

        txtNombre.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel4.setText("Tipo de arte:");

        comboTipoArte.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboTipoArte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "Plástico", "Escénico", "Circense" }));

        jLabel5.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel5.setText("Horario:");

        comboDia.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        comboHoraInicio.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        comboHoraFin.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel6.setText("ID método:");

        txtIdMetodo.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel7.setText("ID instructor:");

        txtIdInstructor.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel8.setText("ID alianza:");

        txtIdAlianza.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        btnInsertar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnInsertar.setText("Insertar");
        btnInsertar.addActionListener(this::btnInsertarActionPerformed);

        btnEditar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnLimpiar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnCancelar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnCancelar.setText("Volver");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        tblTalleres.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        tblTalleres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_taller", "Nombre", "Tipo_arte", "Horario", "ID_metodo", "ID_instructor", "ID_alianza"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTalleres);
        if (tblTalleres.getColumnModel().getColumnCount() > 0) {
            tblTalleres.getColumnModel().getColumn(0).setResizable(true);
        }

        txtBuscar.setBackground(new java.awt.Color(205, 199, 199));
        txtBuscar.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel9.setText("Buscar:");

        btnBuscar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTipoArte, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdAlianza, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(comboTipoArte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(comboDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIdMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtIdInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtIdAlianza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnInsertar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(466, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1561, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        if (!validarCampos()) {
            return;
        }
        try {
            Taller taller = obtenerTallerDesdeCampos(null);
            if (controlador.controlarAccion(evt, taller)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla();
                limpiarCampos();
            } else {
                MensajesUI.error(this, controlador.getUltimoMensaje());
            }
        } catch (NumberFormatException e) {
            MensajesUI.error(this, "Los IDs deben ser números enteros válidos.");
        }
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (!validarCampos()) {
            return;
        }
        int fila = tblTalleres.getSelectedRow();
        if (fila == -1) {
            MensajesUI.seleccionarEnTabla(this, "taller");
            return;
        }
        try {
            int idTaller = Integer.parseInt(tblTalleres.getValueAt(fila, 0).toString());
            Taller taller = obtenerTallerDesdeCampos(idTaller);
            if (controlador.controlarAccion(evt, taller)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla();
                limpiarCampos();
            } else {
                MensajesUI.error(this, controlador.getUltimoMensaje());
            }
        } catch (NumberFormatException e) {
            MensajesUI.error(this, "Los IDs deben ser números enteros válidos.");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblTalleres.getSelectedRow();
        if (fila == -1) {
            MensajesUI.seleccionarEnTabla(this, "taller");
            return;
        }
        String nombre = tblTalleres.getValueAt(fila, 1).toString();
        if (!MensajesUI.confirmarEliminacion(this, nombre)) {
            return;
        }
        Taller taller = new Taller();
        taller.setIdTaller(Integer.parseInt(tblTalleres.getValueAt(fila, 0).toString()));
        if (controlador.controlarAccion(evt, taller)) {
            MensajesUI.exito(this, controlador.getUltimoMensaje());
            cargarTabla();
            limpiarCampos();
        } else {
            MensajesUI.error(this, controlador.getUltimoMensaje());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        MensajesUI.volverAlMenu(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        cargarTabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            MensajesUI.criterioBusquedaVacio(this, "ID o nombre del taller");
            return;
        }
        Taller taller = controlador.buscar(criterio);
        limpiarCampos();
        if (taller == null) {
            MensajesUI.sinResultadosBusqueda(this);
            return;
        }
        txtNombre.setText(taller.getNombre() == null ? "" : taller.getNombre());
        comboTipoArte.setSelectedItem(taller.getTipoArte() == null ? "Selecciona" : taller.getTipoArte());
        HorarioUtil.aplicar(taller.getHorario(), comboDia, comboHoraInicio, comboHoraFin);
        txtIdMetodo.setText(taller.getIdMetodo() == null ? "" : String.valueOf(taller.getIdMetodo()));
        txtIdInstructor.setText(taller.getIdInstructor() == null ? "" : String.valueOf(taller.getIdInstructor()));
        txtIdAlianza.setText(taller.getIdAlianza() == null ? "" : String.valueOf(taller.getIdAlianza()));
        seleccionarFilaPorId(taller.getIdTaller());
        txtBuscar.setText("");
    }//GEN-LAST:event_btnBuscarActionPerformed

    public static void main(String args[]) {
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
        java.awt.EventQueue.invokeLater(() -> new FRMTaller().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> comboDia;
    private javax.swing.JComboBox<String> comboHoraFin;
    private javax.swing.JComboBox<String> comboHoraInicio;
    private javax.swing.JComboBox<String> comboTipoArte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTalleres;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtIdAlianza;
    private javax.swing.JTextField txtIdInstructor;
    private javax.swing.JTextField txtIdMetodo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
