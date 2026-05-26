package decorator;

import model.EstadoEntrada;

public class EntradaPreferencial extends EntradaDecorator {

    private final double precioAdicional;

    public EntradaPreferencial(EntradaBase entrada, double precioAdicional) {
        super(entrada);
        this.precioAdicional = precioAdicional;
    }

    @Override
    public double getCosto() {
        return super.getCosto() + precioAdicional;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Acceso preferencial";
    }

    @Override
    public EstadoEntrada getEstadoEntrada() {
        return super.getEstadoEntrada();
    }
}