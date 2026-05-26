package adapter;

import java.util.List;

public class PDFAdapter implements ReporteAdapter {

    @Override
    public byte[] exportar(List<String> datos, String titulo) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("REPORTE PDF\n");
        reporte.append(titulo).append("\n\n");
        for (String linea : datos) {
            reporte.append(linea).append("\n");
        }
        reporte.append("\nPDF generado correctamente");
        return reporte.toString().getBytes();
    }
}