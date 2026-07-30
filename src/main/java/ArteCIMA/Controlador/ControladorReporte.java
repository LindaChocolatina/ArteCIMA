package ArteCIMA.Controlador;

import ArteCIMA.Modelo.Modulo;
import ArteCIMA.Modelo.Reporte;
import ArteCIMA.Modelo.SesionUsuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ControladorReporte {

    private String ultimoMensaje = "";
    private Reporte.Resultado ultimoResultado;

    public Reporte.Resultado generar(Reporte.Tipo tipo, Reporte.Filtros filtros) {
        if (!SesionUsuario.puedeAcceder(Modulo.REPORTES)) {
            ultimoMensaje = "Su rol no tiene acceso al módulo de reportes.";
            return null;
        }
        if (tipo == null) {
            ultimoMensaje = "Seleccione un tipo de reporte.";
            return null;
        }
        ultimoResultado = Reporte.generar(tipo, filtros);
        ultimoMensaje = ultimoResultado.estaVacio()
                ? "No se encontraron registros para los filtros indicados."
                : "Reporte generado: " + ultimoResultado.getFilas().size() + " fila(s).";
        return ultimoResultado;
    }

    public boolean exportarCsv(File archivo) {
        if (!SesionUsuario.puedeAcceder(Modulo.REPORTES)) {
            ultimoMensaje = "Su rol no tiene acceso para exportar reportes.";
            return false;
        }
        if (ultimoResultado == null || ultimoResultado.estaVacio()) {
            ultimoMensaje = "Genere un reporte antes de exportar.";
            return false;
        }
        if (archivo == null) {
            ultimoMensaje = "Seleccione un archivo de destino.";
            return false;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(archivo, StandardCharsets.UTF_8))) {
            out.print('\ufeff');
            escribirFilaCsv(out, ultimoResultado.getColumnas());
            for (String[] fila : ultimoResultado.getFilas()) {
                escribirFilaCsv(out, fila);
            }
            ultimoMensaje = "Reporte exportado a " + archivo.getName();
            return true;
        } catch (IOException ex) {
            ultimoMensaje = "No se pudo exportar: " + ex.getMessage();
            return false;
        }
    }

    private void escribirFilaCsv(PrintWriter out, String[] valores) {
        for (int i = 0; i < valores.length; i++) {
            if (i > 0) {
                out.print(',');
            }
            out.print('"');
            String valor = valores[i] == null ? "" : valores[i].replace("\"", "\"\"");
            out.print(valor);
            out.print('"');
        }
        out.println();
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public Reporte.Resultado getUltimoResultado() {
        return ultimoResultado;
    }
}
