package decorator;

public class EntradaVIP extends EntradaDecorator {

    /**
     * Constructor de la clase
     */
    public EntradaVIP(EntradaBase entrada) {
        super(entrada);
    }

    /**
     * Metodos implementados de EntradaBase
     * @return
     */
    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Acceso VIP";
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + 100000;
    }
}