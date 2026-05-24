package adapter;

import model.Compra;

import java.util.List;

public interface ReporteAdapter {
    String generarReporte(List<Compra> compras);
}