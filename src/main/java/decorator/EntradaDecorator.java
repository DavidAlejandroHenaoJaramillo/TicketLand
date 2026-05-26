package decorator;

import model.EstadoEntrada;

public abstract class EntradaDecorator implements IEntrada {

    protected EntradaBase entrada;

    public EntradaDecorator(EntradaBase entrada) {
        this.entrada = entrada;
    }

    @Override
    public String getDescripcion() {
        return entrada.getDescripcion();
    }

    @Override
    public double getCosto() {
        return entrada.getCosto();
    }

    @Override
    public EstadoEntrada getEstadoEntrada() {
        if (entrada instanceof IEntrada ie) return ie.getEstadoEntrada();
        return null;
    }
}