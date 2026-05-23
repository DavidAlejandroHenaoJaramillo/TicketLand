package decorator;

public abstract class EntradaDecorator implements EntradaBase {

    /**
     * Atributos de la clase
     */
    protected EntradaBase entrada;

    /**
     * Constructor de la clase
     */
    public EntradaDecorator(EntradaBase entrada) {
        this.entrada = entrada;
    }

    /**
     * Metodos implementados de EntradaBase
     * @return
     */
    @Override
    public String getDescripcion() {
        return entrada.getDescripcion();
    }

    @Override
    public double getCosto() {
        return entrada.getCosto();
    }
}