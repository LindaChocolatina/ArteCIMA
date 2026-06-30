package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorCorporacion;
import ArteCIMA.Modelo.Corporacion;
import ArteCIMA.Modelo.Modulo;
import java.util.List;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.PermisosUI;
import javax.swing.table.DefaultTableModel;

public class FRMCorporacion extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMCorporacion.class.getName());
    private final ControladorCorporacion controlador = new ControladorCorporacion();

    public FRMCorporacion() {
        initComponents();
        ArteCIMA.Util.UIFormulario.prepararModulo(this, jPanel1, jPanel2, jLabel1, jLabel2, tblCorporaciones);
        btnEditar.setActionCommand("Modificar");

        if (!PermisosUI.verificarAccesoModulo(this, Modulo.CORPORACIONES)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }
        PermisosUI.aplicarPermisosCrud(Modulo.CORPORACIONES, btnInsertar, btnEditar, btnEliminar);

        cargarTabla();
        tblCorporaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarDatosFilaSeleccionada();
            }
        });
    }

    private void cargarTabla() {
        List<Corporacion> lista = controlador.listar();
        DefaultTableModel model = (DefaultTableModel) tblCorporaciones.getModel();
        model.setRowCount(0);
        for (Corporacion c : lista) {
            model.addRow(new Object[]{
                c.getIdCorporacion(), c.getNitCorporacion(), c.getNombre(),
                c.getDireccion(), c.getTipoEntidad()
            });
        }
    }

    private void cargarDatosFilaSeleccionada() {
        int fila = tblCorporaciones.getSelectedRow();
        if (fila == -1) return;
        DefaultTableModel model = (DefaultTableModel) tblCorporaciones.getModel();
        txtNitCorporacion.setText(valor(model, fila, 1));
        txtNombre.setText(valor(model, fila, 2));
        txtDireccion.setText(valor(model, fila, 3));
        comboTipoEntidad.setSelectedItem(model.getValueAt(fila, 4) == null ? "Selecciona" : model.getValueAt(fila, 4).toString());
    }

    private String valor(DefaultTableModel model, int fila, int col) {
        Object v = model.getValueAt(fila, col);
        return v == null ? "" : v.toString();
    }

    private Corporacion obtenerDesdeCampos(Integer id) {
        Corporacion c = new Corporacion();
        if (id != null) c.setIdCorporacion(id);
        c.setNitCorporacion(txtNitCorporacion.getText().trim());
        c.setNombre(txtNombre.getText().trim());
        c.setDireccion(txtDireccion.getText().trim());
        String tipo = comboTipoEntidad.getSelectedItem().toString();
        c.setTipoEntidad("Selecciona".equals(tipo) ? null : tipo);
        return c;
    }

    private boolean validarCampos() {
        if (txtNitCorporacion.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "El NIT es obligatorio.");
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "El nombre es obligatorio.");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNitCorporacion.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        comboTipoEntidad.setSelectedIndex(0);
        txtBuscar.setText("");
        tblCorporaciones.clearSelection();
    }

    private void seleccionarFilaPorId(int id) {
        DefaultTableModel model = (DefaultTableModel) tblCorporaciones.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                if (Integer.parseInt(model.getValueAt(i, 0).toString()) == id) {
                    tblCorporaciones.setRowSelectionInterval(i, i);
                    tblCorporaciones.scrollRectToVisible(tblCorporaciones.getCellRect(i, 0, true));
                    break;
                }
            } catch (NumberFormatException ex) {
                logger.warning("ID inválido en tabla");
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
        txtNitCorporacion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comboTipoEntidad = new javax.swing.JComboBox<>();
        btnInsertar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCorporaciones = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("GESTIÓN DE CORPORACIONES");

        jPanel2.setBackground(new java.awt.Color(218, 224, 229));

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel3.setText("NIT:");

        txtNitCorporacion.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel4.setText("Nombre:");

        txtNombre.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel5.setText("Dirección:");

        txtDireccion.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel6.setText("Tipo entidad:");

        comboTipoEntidad.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboTipoEntidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "ESAL", "Fundación", "Otra" }));

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

        tblCorporaciones.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        tblCorporaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID_corporacion", "NIT", "Nombre", "Dirección", "Tipo_entidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCorporaciones);

        txtBuscar.setBackground(new java.awt.Color(205, 199, 199));
        txtBuscar.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel7.setText("Buscar:");

        btnBuscar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNitCorporacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTipoEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInsertar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNitCorporacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(comboTipoEntidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnInsertar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        if (!validarCampos()) return;
        Corporacion c = obtenerDesdeCampos(null);
        if (controlador.controlarAccion(evt, c)) {
            MensajesUI.exito(this, controlador.getUltimoMensaje());
            cargarTabla(); limpiarCampos();
        } else MensajesUI.exito(this, controlador.getUltimoMensaje());
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (!validarCampos()) return;
        int fila = tblCorporaciones.getSelectedRow();
        if (fila == -1) { MensajesUI.seleccionarEnTabla(this, "registro"); return; }
        Corporacion c = obtenerDesdeCampos(Integer.parseInt(tblCorporaciones.getValueAt(fila, 0).toString()));
        if (controlador.controlarAccion(evt, c)) {
            MensajesUI.exito(this, controlador.getUltimoMensaje());
            cargarTabla(); limpiarCampos();
        } else MensajesUI.exito(this, controlador.getUltimoMensaje());
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblCorporaciones.getSelectedRow();
        if (fila == -1) { MensajesUI.seleccionarEnTabla(this, "registro"); return; }
        if (!MensajesUI.confirmarEliminacion(this, "este registro")) return;
        Corporacion c = new Corporacion();
        c.setIdCorporacion(Integer.parseInt(tblCorporaciones.getValueAt(fila, 0).toString()));
        if (controlador.controlarAccion(evt, c)) {
            MensajesUI.exito(this, controlador.getUltimoMensaje());
            cargarTabla(); limpiarCampos();
        } else MensajesUI.exito(this, controlador.getUltimoMensaje());
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos(); cargarTabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        MensajesUI.volverAlMenu(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) { MensajesUI.criterioBusquedaVacio(this, "ID o criterio de busqueda"); return; }
        Corporacion c = controlador.buscar(criterio);
        limpiarCampos();
        if (c == null) { MensajesUI.sinResultadosBusqueda(this); return; }
        txtNitCorporacion.setText(c.getNitCorporacion() == null ? "" : c.getNitCorporacion());
        txtNombre.setText(c.getNombre() == null ? "" : c.getNombre());
        txtDireccion.setText(c.getDireccion() == null ? "" : c.getDireccion());
        comboTipoEntidad.setSelectedItem(c.getTipoEntidad() == null ? "Selecciona" : c.getTipoEntidad());
        seleccionarFilaPorId(c.getIdCorporacion());
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
        java.awt.EventQueue.invokeLater(() -> new FRMCorporacion().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> comboTipoEntidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCorporaciones;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNitCorporacion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
