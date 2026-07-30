package ArteCIMA.Vista;

import ArteCIMA.Controlador.ControladorAsistencia;
import ArteCIMA.Modelo.Asistencia;
import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Util.CalendarioFecha;
import ArteCIMA.Util.MensajesUI;
import ArteCIMA.Util.PermisosUI;
import ArteCIMA.Util.TemaCIMA;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Registro diario de asistencia por nombres (sin IDs): calendario + lista con casilla Presente.
 */
public class FRMAsistencia extends JFrame {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ControladorAsistencia controlador = new ControladorAsistencia();
    private final boolean puedeEditar = PermisosUI.puedeEditarCampos(Modulo.ASISTENCIAS);

    private LocalDate fechaSeleccionada = LocalDate.now();
    private JButton btnFecha;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JLabel lblEstado;
    private JButton btnGuardar;
    private JButton btnTodosSi;
    private JButton btnTodosNo;

    private final List<Asistencia.FilaDia> filas = new ArrayList<>();

    public FRMAsistencia() {
        if (!PermisosUI.verificarAccesoModulo(this, Modulo.ASISTENCIAS)) {
            java.awt.EventQueue.invokeLater(this::dispose);
            return;
        }

        construirInterfaz();
        actualizarTextoFecha();
        cargarLista();

        TemaCIMA.aplicarIcono(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("ArteCIMA - Asistencias");
        setMinimumSize(new Dimension(1100, 700));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel fondo = TemaCIMA.crearFondoArtistico();
        fondo.setLayout(new BorderLayout());

        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(TemaCIMA.AZUL_REY);
        barra.setBorder(new EmptyBorder(12, 28, 12, 28));
        JLabel titulo = new JLabel("GESTIÓN DE ASISTENCIAS");
        titulo.setFont(TemaCIMA.FUENTE_TITULO);
        titulo.setForeground(TemaCIMA.BLANCO);
        if (TemaCIMA.logoEscalado(34) != null) {
            JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            izq.setOpaque(false);
            izq.add(new JLabel(TemaCIMA.logoEscalado(34)));
            izq.add(titulo);
            barra.add(izq, BorderLayout.WEST);
        } else {
            barra.add(titulo, BorderLayout.WEST);
        }

        JPanel franja = new JPanel();
        franja.setBackground(TemaCIMA.AMARILLO);
        franja.setPreferredSize(new Dimension(0, 3));

        JPanel contenido = new JPanel(new BorderLayout(0, 12));
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(16, 28, 24, 28));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        toolbar.setOpaque(false);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TemaCIMA.BORDE_SUAVE, 1, true),
                new EmptyBorder(8, 12, 8, 12)));

        JLabel lblFecha = new JLabel("Fecha de la clase:");
        lblFecha.setFont(TemaCIMA.FUENTE_LABEL);
        lblFecha.setForeground(TemaCIMA.AZUL_REY);

        btnFecha = new JButton();
        btnFecha.setFont(TemaCIMA.FUENTE_CAMPO);
        btnFecha.setPreferredSize(new Dimension(160, TemaCIMA.ALTO_CAMPO));
        TemaCIMA.estilizarBotonSecundario(btnFecha);
        btnFecha.addActionListener(e -> abrirCalendario());

        JButton btnCargar = new JButton("Cargar lista");
        btnTodosSi = new JButton("Todos presentes");
        btnTodosNo = new JButton("Todos ausentes");
        btnGuardar = new JButton("Guardar asistencia");
        JButton btnVolver = new JButton("Volver");

        TemaCIMA.estilizarBotonSecundario(btnCargar);
        TemaCIMA.estilizarBotonSecundario(btnTodosSi);
        TemaCIMA.estilizarBotonSecundario(btnTodosNo);
        TemaCIMA.estilizarBotonExito(btnGuardar);
        TemaCIMA.estilizarBotonSecundario(btnVolver);

        toolbar.add(lblFecha);
        toolbar.add(btnFecha);
        toolbar.add(btnCargar);
        if (puedeEditar) {
            toolbar.add(btnTodosSi);
            toolbar.add(btnTodosNo);
            toolbar.add(btnGuardar);
        }
        toolbar.add(btnVolver);

        modelo = new DefaultTableModel(
                new Object[]{"Estudiante", "Grupo", "Taller", "Presente"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 3 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return puedeEditar && column == 3;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(32);
        tabla.setFont(TemaCIMA.FUENTE_CAMPO);
        tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(TemaCIMA.AZUL_REY);
        tabla.getTableHeader().setForeground(TemaCIMA.BLANCO);
        tabla.setSelectionBackground(new java.awt.Color(232, 244, 253));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(280);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(3).setMaxWidth(110);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(TemaCIMA.BORDE_SUAVE, 1, true));

        lblEstado = new JLabel("Elija la fecha en el calendario y registre la asistencia.");
        lblEstado.setFont(TemaCIMA.FUENTE_CAMPO);
        lblEstado.setForeground(TemaCIMA.TEXTO_SUAVE);
        lblEstado.setBorder(new EmptyBorder(4, 4, 0, 4));

        JLabel ayuda = new JLabel(puedeEditar
                ? "Abra el calendario, marque Presente en cada estudiante y pulse Guardar asistencia."
                : "Consulta de asistencia (solo lectura).");
        ayuda.setFont(TemaCIMA.FUENTE_CAMPO);
        ayuda.setForeground(TemaCIMA.TEXTO_SUAVE);
        ayuda.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel norte = new JPanel(new BorderLayout(0, 8));
        norte.setOpaque(false);
        norte.add(ayuda, BorderLayout.NORTH);
        norte.add(toolbar, BorderLayout.CENTER);

        contenido.add(norte, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);
        contenido.add(lblEstado, BorderLayout.SOUTH);

        JPanel cuerpo = new JPanel(new BorderLayout());
        cuerpo.setOpaque(false);
        cuerpo.add(franja, BorderLayout.NORTH);
        cuerpo.add(contenido, BorderLayout.CENTER);

        fondo.add(barra, BorderLayout.NORTH);
        fondo.add(cuerpo, BorderLayout.CENTER);
        setContentPane(fondo);

        btnCargar.addActionListener(e -> cargarLista());
        btnTodosSi.addActionListener(e -> marcarTodos(true));
        btnTodosNo.addActionListener(e -> marcarTodos(false));
        btnGuardar.addActionListener(e -> guardar());
        btnVolver.addActionListener(e -> MensajesUI.volverAlMenu(this));
    }

    private void abrirCalendario() {
        CalendarioFecha.mostrar(this, fechaSeleccionada, fecha -> {
            fechaSeleccionada = fecha;
            actualizarTextoFecha();
            cargarLista();
        });
    }

    private void actualizarTextoFecha() {
        btnFecha.setText(fechaSeleccionada.format(FORMATO_FECHA));
    }

    private Date fechaSql() {
        return Date.valueOf(fechaSeleccionada);
    }

    private void cargarLista() {
        Date fecha = fechaSql();
        filas.clear();
        filas.addAll(controlador.listarParaFecha(fecha));
        modelo.setRowCount(0);
        for (Asistencia.FilaDia fila : filas) {
            modelo.addRow(new Object[]{
                fila.getNombreEstudiante(),
                fila.getNombreGrupo(),
                fila.getNombreTaller(),
                fila.isPresente()
            });
        }
        if (filas.isEmpty()) {
            lblEstado.setText("No hay estudiantes asignados para registrar en esta fecha.");
        } else {
            long yaGuardados = filas.stream().filter(f -> f.getIdAsistencia() != null).count();
            lblEstado.setText(filas.size() + " estudiante(s) · " + fechaSeleccionada.format(FORMATO_FECHA)
                    + " · " + yaGuardados + " con registro guardado"
                    + (puedeEditar ? " · marque Presente y guarde" : " · solo consulta"));
        }
    }

    private void marcarTodos(boolean presente) {
        detenerEdicionCelda();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.setValueAt(presente, i, 3);
        }
    }

    private void detenerEdicionCelda() {
        if (tabla.isEditing() && tabla.getCellEditor() != null) {
            tabla.getCellEditor().stopCellEditing();
        }
    }

    private void sincronizarFilasDesdeTabla() {
        detenerEdicionCelda();
        for (int i = 0; i < filas.size() && i < modelo.getRowCount(); i++) {
            Object val = modelo.getValueAt(i, 3);
            filas.get(i).setPresente(Boolean.TRUE.equals(val));
        }
    }

    private void guardar() {
        if (!puedeEditar) {
            MensajesUI.advertencia(this, "No tiene permiso para registrar asistencias.");
            return;
        }
        if (filas.isEmpty()) {
            MensajesUI.advertencia(this, "No hay estudiantes en la lista.");
            return;
        }
        sincronizarFilasDesdeTabla();
        Date fecha = fechaSql();
        if (controlador.guardarDia(fecha, filas)) {
            MensajesUI.exito(this, controlador.getUltimoMensaje());
            cargarLista();
        } else {
            MensajesUI.error(this, controlador.getUltimoMensaje());
        }
    }
}
