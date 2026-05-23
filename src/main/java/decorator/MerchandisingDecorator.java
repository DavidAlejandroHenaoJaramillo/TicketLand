package decorator;

public class MerchandisingDecorator extends EntradaDecorator {

    /**
     * Constructor de la clase
     */
    public MerchandisingDecorator(EntradaBase entrada) {
        super(entrada);
    }

    /**
     * Metodos implementados de EntradaBase
     * @return
     */
    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Merchandising";
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + 50000;
    }
}