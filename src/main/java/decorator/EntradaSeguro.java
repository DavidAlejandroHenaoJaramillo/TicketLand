package decorator;

import model.EstadoEntrada;

public class EntradaSeguro extends EntradaDecorator {

    private final double precioAdicional;

    public EntradaSeguro(EntradaBase entrada, double precioAdicional) {
        super(entrada);
        this.precioAdicional = precioAdicional;
    }

    @Override
    public double getCosto() {          // era obtenerPrecio()
        return super.getCosto() + precioAdicional;
    }

    @Override
    public String getDescripcion() {    // era obtenerDescripcion()
        return super.getDescripcion() + " + Seguro de cancelación";
    }

    @Override
    public EstadoEntrada getEstadoEntrada() {
        return super.getEstadoEntrada();
    }
}