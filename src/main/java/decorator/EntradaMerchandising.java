package decorator;

import model.EstadoEntrada;

public class EntradaMerchandising extends EntradaDecorator {

    private final double precioAdicional;

    public EntradaMerchandising(EntradaBase entrada, double precioAdicional) {
        super(entrada);
        this.precioAdicional = precioAdicional;
    }

    @Override
    public double getCosto() {
        return super.getCosto() + precioAdicional;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Merchandising";
    }

    @Override
    public EstadoEntrada getEstadoEntrada() {
        return super.getEstadoEntrada();
    }
}