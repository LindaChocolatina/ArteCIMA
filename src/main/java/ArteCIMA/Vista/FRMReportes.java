package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorReporte;
import ArteCIMA.Modelo.Grupo;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.Reporte;
import ArteCIMA.Modelo.Taller;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.PermisosUI;
import ArteCIMA.Util.TemaCIMA;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FRMReportes extends JFrame {

    private final ControladorReporte controlador = new ControladorReporte();

    private final JComboBox<Reporte.Tipo> comboTipo = new JComboBox<>(Reporte.Tipo.values());
    private final JComboBox<ItemCombo> comboGrupo = new JComboBox<>();
    private final JComboBox<ItemCombo> comboTaller = new JComboBox<>();
    private final JTextField txtFechaDesde = new JTextField(12);
    private final JTextField txtFechaHasta = new JTextField(12);
    private final JLabel lblGrupo = new JLabel("Grupo:");
    private final JLabel lblTaller = new JLabel("Taller:");
    private final JLabel lblFechaDesde = new JLabel("Desde (yyyy-MM-dd):");
    private final JLabel lblFechaHasta = new JLabel("Hasta (yyyy-MM-dd):");
    private final JButton btnGenerar = new JButton("Generar reporte");
    private final JButton btnExportar = new JButton("Exportar CSV");
    private final JTable tabla = new JTable();
    private final JLabel lblEstado = new JLabel("Seleccione un reporte y pulse Generar.");

    public FRMReportes() {
        if (!PermisosUI.verificarAccesoModulo(this, Modulo.REPORTES)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }

        construirInterfaz();
        cargarCombos();
        actualizarFiltrosVisibles();
        comboTipo.addActionListener(e -> actualizarFiltrosVisibles());

        btnGenerar.addActionListener(e -> generarReporte());
        btnExportar.addActionListener(e -> exportarCsv());

        TemaCIMA.aplicarIcono(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("ArteCIMA - Reportes");
        setMinimumSize(new Dimension(1100, 700));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel fondo = TemaCIMA.crearFondoArtistico();
        fondo.setLayout(new BorderLayout(0, 16));
        fondo.setBorder(new EmptyBorder(16, 24, 24, 24));

        JLabel titulo = new JLabel("Reportes del sistema");
        titulo.setFont(TemaCIMA.FUENTE_TITULO);
        titulo.setForeground(TemaCIMA.AZUL_REY);
        titulo.setBorder(new EmptyBorder(0, 0, 8, 0));

        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setOpaque(false);
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TemaCIMA.BORDE_SUAVE, 1, true),
                new EmptyBorder(16, 20, 16, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        TemaCIMA.estilizarCombo(comboTipo);
        TemaCIMA.estilizarCombo(comboGrupo);
        TemaCIMA.estilizarCombo(comboTaller);
        TemaCIMA.estilizarCampo(txtFechaDesde);
        TemaCIMA.estilizarCampo(txtFechaHasta);
        TemaCIMA.estilizarBotonPrimario(btnGenerar);
        TemaCIMA.estilizarBotonSecundario(btnExportar);

        int fila = 0;
        agregarFila(panelFiltros, gbc, fila++, new JLabel("Tipo de reporte:"), comboTipo);
        agregarFila(panelFiltros, gbc, fila++, lblGrupo, comboGrupo);
        agregarFila(panelFiltros, gbc, fila++, lblTaller, comboTaller);
        agregarFila(panelFiltros, gbc, fila++, lblFechaDesde, txtFechaDesde);
        agregarFila(panelFiltros, gbc, fila++, lblFechaHasta, txtFechaHasta);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnGenerar);
        panelBotones.add(btnExportar);

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 8, 0, 8);
        panelFiltros.add(panelBotones, gbc);

        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(TemaCIMA.BORDE_SUAVE, 1, true));
        scroll.setPreferredSize(new Dimension(900, 420));

        lblEstado.setFont(TemaCIMA.FUENTE_CAMPO);
        lblEstado.setForeground(TemaCIMA.TEXTO_SUAVE);

        fondo.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setOpaque(false);
        centro.add(panelFiltros, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(lblEstado, BorderLayout.SOUTH);
        fondo.add(centro, BorderLayout.CENTER);

        setContentPane(fondo);
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, JLabel etiqueta, java.awt.Component campo) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        etiqueta.setFont(TemaCIMA.FUENTE_LABEL);
        etiqueta.setForeground(TemaCIMA.TEXTO);
        panel.add(etiqueta, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campo, gbc);
    }

    private void cargarCombos() {
        comboGrupo.addItem(new ItemCombo(null, "Todos los grupos"));
        for (Grupo g : Grupo.listar()) {
            comboGrupo.addItem(new ItemCombo(g.getIdGrupo(), g.getIdGrupo() + " — " + g.getNombre()));
        }

        comboTaller.addItem(new ItemCombo(null, "Todos los talleres"));
        for (Taller t : Taller.listar()) {
            comboTaller.addItem(new ItemCombo(t.getIdTaller(), t.getIdTaller() + " — " + t.getNombre()));
        }
    }

    private void actualizarFiltrosVisibles() {
        Reporte.Tipo tipo = (Reporte.Tipo) comboTipo.getSelectedItem();
        if (tipo == null) {
            return;
        }

        boolean mostrarGrupo = tipo == Reporte.Tipo.ASISTENCIA_GRUPO;
        boolean mostrarTaller = tipo == Reporte.Tipo.ESTUDIANTES_TALLER;
        boolean mostrarFechas = tipo == Reporte.Tipo.ASISTENCIA_GRUPO
                || tipo == Reporte.Tipo.PAGOS_INSTRUCTOR
                || tipo == Reporte.Tipo.MOVIMIENTOS;

        lblGrupo.setVisible(mostrarGrupo);
        comboGrupo.setVisible(mostrarGrupo);
        lblTaller.setVisible(mostrarTaller);
        comboTaller.setVisible(mostrarTaller);
        lblFechaDesde.setVisible(mostrarFechas);
        txtFechaDesde.setVisible(mostrarFechas);
        lblFechaHasta.setVisible(mostrarFechas);
        txtFechaHasta.setVisible(mostrarFechas);
    }

    private void generarReporte() {
        Reporte.Tipo tipo = (Reporte.Tipo) comboTipo.getSelectedItem();
        Reporte.Filtros filtros = new Reporte.Filtros();

        try {
            if (comboGrupo.isVisible()) {
                filtros.setIdGrupo(((ItemCombo) comboGrupo.getSelectedItem()).id);
            }
            if (comboTaller.isVisible()) {
                filtros.setIdTaller(((ItemCombo) comboTaller.getSelectedItem()).id);
            }
            if (txtFechaDesde.isVisible() && !txtFechaDesde.getText().trim().isEmpty()) {
                filtros.setFechaDesde(java.sql.Date.valueOf(txtFechaDesde.getText().trim()));
            }
            if (txtFechaHasta.isVisible() && !txtFechaHasta.getText().trim().isEmpty()) {
                filtros.setFechaHasta(java.sql.Date.valueOf(txtFechaHasta.getText().trim()));
            }
        } catch (IllegalArgumentException ex) {
            MensajesUI.advertencia(this, "Revise el formato de fechas (yyyy-MM-dd).");
            return;
        }

        Reporte.Resultado resultado = controlador.generar(tipo, filtros);
        if (resultado == null) {
            MensajesUI.error(this, controlador.getUltimoMensaje());
            return;
        }

        DefaultTableModel model = new DefaultTableModel(resultado.getColumnas(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String[] fila : resultado.getFilas()) {
            model.addRow(fila);
        }
        tabla.setModel(model);
        lblEstado.setText(controlador.getUltimoMensaje());
    }

    private void exportarCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte CSV");
        chooser.setSelectedFile(new File("reporte_artecima.csv"));
        chooser.setFileFilter(new FileNameExtensionFilter("CSV (*.csv)", "csv"));

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = chooser.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".csv")) {
            archivo = new File(archivo.getAbsolutePath() + ".csv");
        }

        if (controlador.exportarCsv(archivo)) {
            MensajesUI.exito(this, controlador.getUltimoMensaje());
        } else {
            MensajesUI.error(this, controlador.getUltimoMensaje());
        }
    }

    private static final class ItemCombo {
        private final Integer id;
        private final String etiqueta;

        private ItemCombo(Integer id, String etiqueta) {
            this.id = id;
            this.etiqueta = etiqueta;
        }

        @Override
        public String toString() {
            return etiqueta;
        }
    }
}
