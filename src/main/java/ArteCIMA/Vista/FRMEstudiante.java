package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorEstudiante;
import ArteCIMA.Modelo.Estudiante;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.TextoUtil;
import ArteCIMA.Util.PermisosUI;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class FRMEstudiante extends javax.swing.JFrame {
  
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMEstudiante.class.getName());
    private final ControladorEstudiante controlador = new ControladorEstudiante();

    public FRMEstudiante() {
        initComponents();
        ArteCIMA.Util.UIFormulario.prepararModulo(this, jPanel1, jPanel2, jLabel1, jLabel2, tblEstudiantes, Modulo.ESTUDIANTES);
        btnEditar.setActionCommand("Modificar");

        if (!PermisosUI.verificarAccesoModulo(this, Modulo.ESTUDIANTES)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }
        PermisosUI.aplicarPermisosCrud(Modulo.ESTUDIANTES, btnInsertar, btnEditar, btnEliminar);
        PermisosUI.aplicarModoCampos(jPanel2, Modulo.ESTUDIANTES, txtBuscar);

        comboDiscapacidad.addActionListener(e -> {
            boolean puedeEditar = PermisosUI.puedeEditarCampos(Modulo.ESTUDIANTES);
            boolean tiene = comboDiscapacidad.getSelectedItem().toString().equalsIgnoreCase("Sí");
            txtTipoDiscapacidad.setEnabled(puedeEditar && tiene);
            if(!tiene) txtTipoDiscapacidad.setText("");
        });
                
        cargarTabla();
              
        tblEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarDatosFilaSeleccionada();
            }
        });
    }
    
    
    public void cargarTabla() {
     
        List<Estudiante> lista = controlador.listar();

        DefaultTableModel model = (DefaultTableModel) tblEstudiantes.getModel(); 
        model.setRowCount(0);  
    
        for (Estudiante est : lista) {
            model.addRow(new Object[]{
                est.getIdEstudiante(),
                est.getTipoDocumento(),
                est.getNumDocumento(),
                est.getNombreCompleto(),
                est.getEdad(),
                est.getTelefono(),
                est.getCorreo(),
                Boolean.TRUE.equals(est.getDiscapacidad()) ? "Sí" : "No",
                est.getTipoDiscapacidad(),
                est.getTipoBeneficio(),               
                est.getIdGrupo(),
                est.getIdBeca(),
                est.getIdAcudiente()
            });
        }
    }
    
    private void cargarDatosFilaSeleccionada() {
        int fila = tblEstudiantes.getSelectedRow();

        if (fila == -1) return;

        DefaultTableModel model = (DefaultTableModel) tblEstudiantes.getModel();
      
        comboTipoDoc.setSelectedItem(model.getValueAt(fila, 1) == null ? "Selecciona" : model.getValueAt(fila, 1).toString());
        comboDiscapacidad.setSelectedItem(model.getValueAt(fila, 7) == null ? "Selecciona" : model.getValueAt(fila, 7).toString());
        comboBeneficio.setSelectedItem(model.getValueAt(fila, 9) == null ? "Selecciona" : model.getValueAt(fila, 9).toString());
      
        txtNumDoc.setText(model.getValueAt(fila, 2) == null ? "" : model.getValueAt(fila, 2).toString());
        txtNombre.setText(model.getValueAt(fila, 3) == null ? "" : model.getValueAt(fila, 3).toString());
        txtEdad.setText(model.getValueAt(fila, 4) == null ? "" : model.getValueAt(fila, 4).toString());
        txtTelefono.setText(model.getValueAt(fila, 5) == null ? "" : model.getValueAt(fila, 5).toString());
        txtCorreo.setText(model.getValueAt(fila, 6) == null ? "" : model.getValueAt(fila, 6).toString());
        txtTipoDiscapacidad.setText(model.getValueAt(fila, 8) == null ? "" : model.getValueAt(fila, 8).toString());
        txtIdGrupo.setText(model.getValueAt(fila, 10) == null ? "" : model.getValueAt(fila, 10).toString()); 
        txtIdBeca.setText(model.getValueAt(fila, 11) == null ? "" : model.getValueAt(fila, 11).toString());
        txtIdAcudiente.setText(model.getValueAt(fila, 12) == null ? "" : model.getValueAt(fila, 12).toString());
        
        boolean tiene = comboDiscapacidad.getSelectedItem().toString().equalsIgnoreCase("Sí");
        txtTipoDiscapacidad.setEnabled(PermisosUI.puedeEditarCampos(Modulo.ESTUDIANTES) && tiene);
    }
    
    private void limpiarCampos() {
    
        txtNombre.setText("");
        txtNumDoc.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtTipoDiscapacidad.setText("");
        txtIdGrupo.setText("");
        txtIdBeca.setText("");
        txtIdAcudiente.setText("");

        comboTipoDoc.setSelectedIndex(0);
        comboDiscapacidad.setSelectedIndex(0);
        comboBeneficio.setSelectedIndex(0);

        TextoUtil.restaurarPlaceholderBuscar(txtBuscar);
        tblEstudiantes.clearSelection();
        txtTipoDiscapacidad.setEnabled(false);
    }
  
   private boolean validarCampos() {
                
        if (txtNumDoc.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtEdad.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this, "Documento, nombre y edad son obligatorios.");
            return false;
        }
        
        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad <= 0) {
                MensajesUI.advertencia(this, "La edad debe ser mayor a 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            MensajesUI.error(this, "La edad debe ser un número entero válido.");
            return false;
        }

        String correo = txtCorreo.getText().trim();
        if (!correo.isEmpty() && (!correo.contains("@") || !correo.contains("."))) {
            MensajesUI.error(this, "Correo inválido. Formato esperado: ejemplo@dominio.com");
            return false;
        }
       
        if (edad < 18 && txtIdAcudiente.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this,
                "El estudiante es menor de edad (" + edad + " años).\n" +
                "Debe ingresar un ID de acudiente.");
            return false;
        }

        return true;
    }

    private Estudiante obtenerEstudiante(Integer idEstudiante) {
        String tipoDoc = comboTipoDoc.getSelectedItem().toString();
        String numDoc = txtNumDoc.getText().trim();
        String nombre = txtNombre.getText().trim();
        int edad = Integer.parseInt(txtEdad.getText().trim());
        String tel = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        boolean discapacidad = comboDiscapacidad.getSelectedItem().toString()
                .equalsIgnoreCase("Sí");
        String tipoDisc = txtTipoDiscapacidad.getText().trim();
        String beneficio = comboBeneficio.getSelectedItem().toString();

        Estudiante estudiante = new Estudiante();
        if (idEstudiante != null) {
            estudiante.setIdEstudiante(idEstudiante);
        }
        estudiante.setTipoDocumento(tipoDoc);
        estudiante.setNumDocumento(numDoc);
        estudiante.setNombreCompleto(nombre);
        estudiante.setEdad(edad);
        estudiante.setTelefono(tel);
        estudiante.setCorreo(correo);
        estudiante.setDiscapacidad(discapacidad);
        estudiante.setTipoDiscapacidad(discapacidad ? tipoDisc : null);
        estudiante.setTipoBeneficio(beneficio);

        if (idEstudiante != null) {
            estudiante.setIdGrupo(txtIdGrupo.getText().trim().isEmpty() ? null : Integer.parseInt(txtIdGrupo.getText().trim()));
            estudiante.setIdBeca(txtIdBeca.getText().trim().isEmpty() ? null : Integer.parseInt(txtIdBeca.getText().trim()));
            estudiante.setIdAcudiente(txtIdAcudiente.getText().trim().isEmpty() ? null : Integer.parseInt(txtIdAcudiente.getText().trim()));
        } else {
            estudiante.setIdGrupo(Integer.parseInt(txtIdGrupo.getText().trim()));
            estudiante.setIdBeca(Integer.parseInt(txtIdBeca.getText().trim()));
            if (edad < 18) {
                estudiante.setIdAcudiente(Integer.parseInt(txtIdAcudiente.getText().trim()));
            }
        }

        return estudiante;
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
        jLabel6 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comboDiscapacidad = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtTipoDiscapacidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        comboBeneficio = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtIdGrupo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtIdBeca = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtIdAcudiente = new javax.swing.JTextField();
        btnInsertar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEstudiantes = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1365, 707));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("GESTIÓN DE ESTUDIANTES");

        jPanel2.setBackground(new java.awt.Color(218, 224, 229));

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel3.setText("Tipo de documento:");

        comboTipoDoc.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "TI", "CC", "CE", "" }));

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel4.setText("Número de documento:");

        txtNumDoc.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel5.setText("Nombre completo:");

        txtNombre.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel6.setText("Edad:");

        txtEdad.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel7.setText("Teléfono:");

        txtTelefono.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel8.setText("Correo:");

        txtCorreo.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel9.setText("Discapacidad:");

        comboDiscapacidad.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboDiscapacidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "Sí", "No" }));

        jLabel10.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel10.setText("Tipo de discapacidad:");

        txtTipoDiscapacidad.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel11.setText("Tipo de beneficio:");

        comboBeneficio.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        comboBeneficio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "Gratuidad", "Pago parcial", "Ninguno" }));

        jLabel12.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel12.setText("ID grupo:");

        txtIdGrupo.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel13.setText("ID beca:");

        txtIdBeca.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel14.setText("ID acudiente:");

        txtIdAcudiente.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N

        btnInsertar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnInsertar.setText("Insertar");
        btnInsertar.addActionListener(this::btnInsertarActionPerformed);

        btnEditar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnLimpiar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnCancelar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnCancelar.setText("Volver");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnEliminar.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        tblEstudiantes.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        tblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID_estudiante", "Tipo_documento", "Num_documento", "Nombre_completo", "Edad", "Teléfono", "Correo", "Discapacidad", "Tipo_discapacidad", "Tipo_beneficio", "ID_grupo", "ID_beca", "ID_acudiente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblEstudiantes);
        if (tblEstudiantes.getColumnModel().getColumnCount() > 0) {
            tblEstudiantes.getColumnModel().getColumn(0).setResizable(true);
        }

        txtBuscar.setBackground(new java.awt.Color(205, 199, 199));
        txtBuscar.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(51, 51, 51));

        jLabel15.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel15.setText("Buscar:");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(21, 21, 21)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 536, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(659, 659, 659)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCorreo))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(14, 14, 14)
                                .addComponent(comboTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboDiscapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTipoDiscapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBeneficio, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdBeca, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdAcudiente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInsertar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtNumDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDiscapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtTipoDiscapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(comboBeneficio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtIdGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtIdBeca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtIdAcudiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnEliminar)
                    .addComponent(btnEditar)
                    .addComponent(btnInsertar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(139, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1417, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        int edad = Integer.parseInt(txtEdad.getText().trim());
        if (edad < 18 && txtIdAcudiente.getText().trim().isEmpty()) {
            MensajesUI.advertencia(this,
                    "Debe ingresar un acudiente porque el estudiante es menor de edad.");
            return;
        }

        try {
            Estudiante estudiante = obtenerEstudiante(null);
            if (controlador.controlarAccion(evt, estudiante)) {
                MensajesUI.exito(this, controlador.getUltimoMensaje());
                cargarTabla();
                limpiarCampos();
            } else {
                MensajesUI.error(this, controlador.getUltimoMensaje());
            }
        } catch (NumberFormatException e) {
            MensajesUI.error(this, "Los IDs de grupo y beca deben ser números enteros válidos.");
        }
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
 
        if (!validarCampos()) {
            return;
        }
        
        int fila = tblEstudiantes.getSelectedRow();
        if (fila == -1) {
            MensajesUI.seleccionarEnTabla(this, "estudiante");
            return;
        }
       
        int idEst = Integer.parseInt(tblEstudiantes.getValueAt(fila, 0).toString());

        String discapacidadSeleccion = comboDiscapacidad.getSelectedItem().toString();
        if ("Selecciona".equals(discapacidadSeleccion)) {
            MensajesUI.advertencia(this, "Seleccione si tiene discapacidad (Sí/No).");
            return;
        }

        try {
            Estudiante est = obtenerEstudiante(idEst);
            if (controlador.controlarAccion(evt, est)) {
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

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        MensajesUI.volverAlMenu(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        cargarTabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
     int fila = tblEstudiantes.getSelectedRow();
    
    if (fila == -1) {
        MensajesUI.seleccionarEnTabla(this, "estudiante");
        return;
    }
      
    int idEst = Integer.parseInt(tblEstudiantes.getValueAt(fila, 0).toString());
    String nombre = tblEstudiantes.getValueAt(fila, 3).toString();
    
    if (!MensajesUI.confirmarEliminacion(this, nombre)) {
        return;  
    }
        
    Estudiante estudiante = new Estudiante();
    estudiante.setIdEstudiante(idEst);
    if (controlador.controlarAccion(evt, estudiante)) {
        MensajesUI.exito(this, controlador.getUltimoMensaje());
        cargarTabla();
        limpiarCampos();
    } else {
        MensajesUI.error(this, controlador.getUltimoMensaje());
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
 
    String criterio = TextoUtil.criterioBusqueda(txtBuscar);
    
    // 1. Validar si el campo de búsqueda está vacío
    if (criterio.isEmpty()) {
        MensajesUI.criterioBusquedaVacio(this, "ID, número de documento o nombre");
        limpiarCampos(); 
        return;
    }
    
    Estudiante est = controlador.buscar(criterio);
    
    // Limpiamos los campos antes de cargar nuevos datos.
    limpiarCampos(); 
    
    // 3. Evaluar el resultado de la búsqueda
    if (est == null) {
        MensajesUI.sinResultadosBusqueda(this);
        return;
    }

    // --- 4. Cargar datos en los campos del formulario ---
    
    // Asignación a JComboBox y JTextfields. Asegúrate de que tus nombres de componentes coincidan.
    comboTipoDoc.setSelectedItem(est.getTipoDocumento() == null || est.getTipoDocumento().isEmpty() ? "Selecciona" : est.getTipoDocumento());
    comboDiscapacidad.setSelectedItem(est.getDiscapacidad() == null ? "Selecciona"
            : (Boolean.TRUE.equals(est.getDiscapacidad()) ? "Sí" : "No"));
    comboBeneficio.setSelectedItem(est.getTipoBeneficio() == null || est.getTipoBeneficio().isEmpty() ? "Selecciona" : est.getTipoBeneficio());

    txtNumDoc.setText(est.getNumDocumento() == null ? "" : est.getNumDocumento());
    txtNombre.setText(est.getNombreCompleto() == null ? "" : est.getNombreCompleto());
    txtEdad.setText(String.valueOf(est.getEdad()));
    txtTelefono.setText(est.getTelefono() == null ? "" : est.getTelefono());
    txtCorreo.setText(est.getCorreo() == null ? "" : est.getCorreo());
    txtTipoDiscapacidad.setText(est.getTipoDiscapacidad() == null ? "" : est.getTipoDiscapacidad());
    
    // Manejo de IDs (asumiendo que 0 o null significan vacío)
    txtIdGrupo.setText(est.getIdGrupo() == null || est.getIdGrupo() == 0 ? "" : String.valueOf(est.getIdGrupo()));
    txtIdBeca.setText(est.getIdBeca() == null || est.getIdBeca() == 0 ? "" : String.valueOf(est.getIdBeca()));
    txtIdAcudiente.setText(est.getIdAcudiente() == null || est.getIdAcudiente() == 0 ? "" : String.valueOf(est.getIdAcudiente()));
    
    boolean tiene = comboDiscapacidad.getSelectedItem().toString().equalsIgnoreCase("Sí");
    txtTipoDiscapacidad.setEnabled(PermisosUI.puedeEditarCampos(Modulo.ESTUDIANTES) && tiene);
    
    // --- 5. SELECCIONAR Y RESALTAR LA FILA EN LA TABLA ---
    
    DefaultTableModel model = (DefaultTableModel) tblEstudiantes.getModel();
    int idBuscado = est.getIdEstudiante(); // Obtenemos el ID del estudiante encontrado
    
    // Recorremos el modelo de la tabla para encontrar la fila que contiene el ID
    for (int i = 0; i < model.getRowCount(); i++) {
        // La columna 0 DEBE contener el ID del estudiante
        try {
            int idFila = Integer.parseInt(model.getValueAt(i, 0).toString()); 
            
            if (idFila == idBuscado) {
                // Selecciona la fila encontrada (Resalta en azul)
                tblEstudiantes.setRowSelectionInterval(i, i);
                
                // Asegura que la tabla se desplace para mostrar la fila seleccionada
                tblEstudiantes.scrollRectToVisible(tblEstudiantes.getCellRect(i, 0, true)); 
                
                break; // Detenemos el ciclo, ya encontramos la fila
            }
        } catch (NumberFormatException ex) {
            // Maneja el caso en que la columna 0 no sea un número válido (ID)
            System.err.println("Error al leer ID en la tabla: La columna 0 no es un número.");
            continue; 
        }
    }
    
    // 6. Limpiar el campo de búsqueda para la siguiente búsqueda
    TextoUtil.restaurarPlaceholderBuscar(txtBuscar); 

    }//GEN-LAST:event_btnBuscarActionPerformed
   

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
        java.awt.EventQueue.invokeLater(() -> new FRMEstudiante().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> comboBeneficio;
    private javax.swing.JComboBox<String> comboDiscapacidad;
    private javax.swing.JComboBox<String> comboTipoDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTable tblEstudiantes;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtIdAcudiente;
    private javax.swing.JTextField txtIdBeca;
    private javax.swing.JTextField txtIdGrupo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoDiscapacidad;
    // End of variables declaration//GEN-END:variables

  
}
