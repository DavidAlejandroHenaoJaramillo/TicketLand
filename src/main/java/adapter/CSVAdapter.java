package adapter;

import model.Compra;

import java.util.List;

public class CSVAdapter implements ReporteAdapter {

    /**
     * Metodo implementado para generar reportes en formato CSV
     * @param compras totales
     * @return reporte
     */
    @Override
    public String generarReporte(List<Compra> compras) {
        StringBuilder csv = new StringBuilder();
        csv.append("fecha,total\n");
        for (Compra compra : compras) {
            csv.append(compra.getFechaCompra()).append(",").append(compra.calcularTotal()).append("\n");
        }
        return csv.toString();
    }
}