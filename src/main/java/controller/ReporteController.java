package controller;

import model.GeneradorReporte;
import model.TicketLand;

public class ReporteController {

    private final TicketLand sistema = TicketLand.getInstance();

    public void exportarVentasCSV(String ruta) {
        new GeneradorReporte(sistema).exportarVentasCSV(ruta, null, null);
    }

    public void exportarVentasPDF(String ruta) {
        new GeneradorReporte(sistema).exportarVentasPDF(ruta, null, null);
    }
}