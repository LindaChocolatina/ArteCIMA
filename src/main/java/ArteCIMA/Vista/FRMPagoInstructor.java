package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorPagoInstructor;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.PagoInstructor;
import java.math.BigDecimal;
import java.util.List;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.PermisosUI;
import javax.swing.table.DefaultTableModel;

public class FRMPagoInstructor extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMPagoInstructor.class.getName());
    private final ControladorPagoInstructor controlador = new ControladorPagoInstructor();

    public FRMPagoInstructor() {
        initComponents();
        ArteCIMA.Util.UIFormulario.prepararModulo(this, jPanel1, jPanel2, jLabel1, jLabel2, tblPagos);
        btnEditar.setActionCommand("Modificar");

        if (!PermisosUI.verificarAccesoModulo(this, Modulo.PAGOS_INSTRUCTOR)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }
        PermisosUI.aplicarPermisosCrud(Modulo.PAGOS_INSTRUCTOR, btnInsertar, btnEditar, btnEliminar);

        cargarTabla();
        tblPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarDatosFilaSeleccionada();
            }
        });
    }

    private void cargarTabla() {
        List<PagoInstructor> lista = controlador.listar();
        DefaultTableModel model = (DefaultTableModel) tblPagos.getModel();
        model.setRowCount(0);
        for (PagoInstructor p : lista) {
            model.addRow(new Object[]{
                p.getIdPago(), p.getIdInstructor(), p.getFechaPago(),
                p.getMonto(), p.getConcepto(), p.getIdMovimiento()
            });
        }
    }

    private void cargarDatosFilaSeleccionada() {
        int fila = tblPagos.getSelectedRow();
        if (fila == -1) return;
        DefaultTableModel model = (DefaultTableModel) tblPagos.getModel();
        txtIdInstructor.setText(valor(model, fila, 1));
        txtFechaPago.setText(valor(model, fila, 2));
        txtMonto.setText(valor(model, fila, 3));
        txtConcepto.setText(valor(model, fila, 4));
        txtIdMovimiento.setText(valor(model, fila, 5));
    }

    private String valor(DefaultTableModel model, int fila, int col) {
        Object v = model.getValueAt(fila, col);
        return v == null ? "" : v.toString();
    }

    private PagoInstructor obtenerDesdeCampos(Integer id) {
        PagoInstructor p = new PagoInstructor();
        if (id != null) p.setIdPago(id);
        p.setIdInstructor(Integer.parseInt(txtIdInstructor.getText().trim()));
        p.setFechaPago(java.sql.Date.valueOf(txtFechaPago.getText().trim()));
        p.setMonto(txtMonto.getText().trim().isEmpty() ? null : new BigDecimal(txtMonto.getText().trim()));
        p.setConcepto(txtConcepto.getText().trim());
        p.setIdMovimiento(txtIdMovimiento.getText().trim().isEmpty() ? null : Integer.parseInt(txtIdMovimiento.getText().trim()));
        return p;
    }

    private boolean validarCampos() {
        if (txtIdInstructor.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "ID instructor es obligatorio.");
            return false;
        }
        if (txtFechaPago.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "La fecha de pago es obligatoria (yyyy-MM-dd).");
            return false;
        }
        try {
            Integer.parseInt(txtIdInstructor.getText().trim());
            java.sql.Date.valueOf(txtFechaPago.getText().trim());
            if (!txtMonto.getText().trim().isEmpty()) new BigDecimal(txtMonto.getText().trim());
            if (!txtIdMovimiento.getText().trim().isEmpty()) Integer.parseInt(txtIdMovimiento.getText().trim());
        } catch (IllegalArgumentException e) {
            MensajesUI.advertencia(this, "Revise fecha, monto e IDs numéricos.");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtIdInstructor.setText("");
        txtFechaPago.setText("");
        txtMonto.setText("");
        txtConcepto.setText("");
        txtIdMovimiento.setText("");
        txtBuscar.setText("");
        tblPagos.clearSelection();
    }

    private void seleccionarFilaPorId(int id) {
        DefaultTableModel model = (DefaultTableModel) tblPagos.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                if (Integer.parseInt(model.getValueAt(i, 0).toString()) == id) {
                    tblPagos.setRowSelectionInterval(i, i);
                    tblPagos.scrollRectToVisible(tblPagos.getCellRect(i, 0, true));
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
        txtIdInstructor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFechaPago = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtConcepto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtIdMovimiento = new javax.swing.JTextField();
        btnInsertar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagos = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("GESTIÓN DE PAGOS A INSTRUCTORES");

        jPanel2.setBackground(new java.awt.Color(218, 224, 229));

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel3.setText("ID instructor:");

        txtIdInstructor.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel4.setText("Fecha pago:");

        txtFechaPago.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel5.setText("Monto:");

        txtMonto.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel6.setText("Concepto:");

        txtConcepto.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel8.setText("ID movimiento:");

        txtIdMovimiento.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

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

        tblPagos.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        tblPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID_pago", "ID_instructor", "Fecha_pago", "Monto", "Concepto", "ID_movimiento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPagos);

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
                        .addComponent(txtIdInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(txtIdInstructor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtIdMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        try {
            PagoInstructor p = obtenerDesdeCampos(null);
            if (controlador.controlarAccion(evt, p)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla(); limpiarCampos();
            } else MensajesUI.exito(this, controlador.getUltimoMensaje());
        } catch (IllegalArgumentException e) {
            MensajesUI.advertencia(this, "Revise fecha, monto e IDs.");
        }
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (!validarCampos()) return;
        int fila = tblPagos.getSelectedRow();
        if (fila == -1) { MensajesUI.seleccionarEnTabla(this, "registro"); return; }
        try {
            PagoInstructor p = obtenerDesdeCampos(Integer.parseInt(tblPagos.getValueAt(fila, 0).toString()));
            if (controlador.controlarAccion(evt, p)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla(); limpiarCampos();
            } else MensajesUI.exito(this, controlador.getUltimoMensaje());
        } catch (IllegalArgumentException e) {
            MensajesUI.advertencia(this, "Revise fecha, monto e IDs.");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblPagos.getSelectedRow();
        if (fila == -1) { MensajesUI.seleccionarEnTabla(this, "registro"); return; }
        if (!MensajesUI.confirmarEliminacion(this, "este registro")) return;
        PagoInstructor p = new PagoInstructor();
        p.setIdPago(Integer.parseInt(tblPagos.getValueAt(fila, 0).toString()));
        if (controlador.controlarAccion(evt, p)) {
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
        PagoInstructor p = controlador.buscar(criterio);
        limpiarCampos();
        if (p == null) { MensajesUI.sinResultadosBusqueda(this); return; }
        txtIdInstructor.setText(String.valueOf(p.getIdInstructor()));
        txtFechaPago.setText(p.getFechaPago() == null ? "" : p.getFechaPago().toString());
        txtMonto.setText(p.getMonto() == null ? "" : p.getMonto().toString());
        txtConcepto.setText(p.getConcepto() == null ? "" : p.getConcepto());
        txtIdMovimiento.setText(p.getIdMovimiento() == null ? "" : String.valueOf(p.getIdMovimiento()));
        seleccionarFilaPorId(p.getIdPago());
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
        java.awt.EventQueue.invokeLater(() -> new FRMPagoInstructor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPagos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtFechaPago;
    private javax.swing.JTextField txtIdInstructor;
    private javax.swing.JTextField txtIdMovimiento;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
