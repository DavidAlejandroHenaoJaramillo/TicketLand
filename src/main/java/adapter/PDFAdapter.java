package adapter;

import model.Compra;

import java.util.List;

public class PDFAdapter implements ReporteAdapter {

    /**
     * Metodo implementado para generar reportes en formato PDF
     * @param compras totales
     * @return reporte
     */
    @Override
    public String generarReporte(List<Compra> compras) {
        StringBuilder reporte = new StringBuilder();reporte.append("REPORTE PDF\n");
        for (Compra compra : compras) {
            reporte.append(compra).append("\n");
        }
        reporte.append("\nPDF generado correctamente");
        return reporte.toString();
    }
}