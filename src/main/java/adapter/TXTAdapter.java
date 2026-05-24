package adapter;

import model.Compra;

import java.util.List;

public class TXTAdapter implements ReporteAdapter {

    /**
     * Metodo implementado para generar reportes en formato TXT
     * @param compras totales
     * @return reporte
     */
    @Override
    public String generarReporte(List<Compra> compras) {
        StringBuilder txt = new StringBuilder();
        txt.append("HISTORIAL DE COMPRAS\n");
        for (Compra compra : compras) {
            txt.append(compra).append("\n");
        }
        return txt.toString();
    }
}