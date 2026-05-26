package service;

import adapter.CSVAdapter;
import adapter.PDFAdapter;
import builder.Compra;
import model.FormatoReporte;
import model.TipoReporte;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// RF-046: Generador de reportes operativos
public class ReporteService {

    private final CSVAdapter csvAdapter;
    private final PDFAdapter pdfAdapter;
    private final List<Compra> compras;

    public ReporteService(List<Compra> compras,
                          CSVAdapter csvAdapter,
                          PDFAdapter pdfAdapter) {
        this.compras = compras;
        this.csvAdapter = csvAdapter;
        this.pdfAdapter = pdfAdapter;
    }

    public byte[] generarReporte(TipoReporte tipo, FormatoReporte formato,
                                 LocalDate inicio, LocalDate fin) {
        List<String> datos = recopilarDatos(tipo, inicio, fin);
        String titulo = tipo.name() + " | " + inicio + " a " + fin;
        return switch (formato) {
            case CSV -> csvAdapter.exportar(datos, titulo);
            case PDF -> pdfAdapter.exportar(datos, titulo);
        };
    }

    private List<String> recopilarDatos(TipoReporte tipo,
                                        LocalDate inicio, LocalDate fin) {
        List<String> datos = new ArrayList<>();
        for (Compra c : compras) {
            if ((inicio == null || !c.getFechaCompra().isBefore(inicio))
                    && (fin == null || !c.getFechaCompra().isAfter(fin))) {
                datos.add(c.toString());
            }
        }
        return datos;
    }
}