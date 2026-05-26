package decorator;

public class SeguroDecorator extends EntradaDecorator {

    /**
     * Constructor de la clase
     */
    public SeguroDecorator(EntradaBase entrada) {
        super(entrada);
    }

    /**
     * Metodos implementados de EntradaBase
     * @return
     */
    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Seguro";
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + 20000;
    }
}