package decorator;

public class ParqueaderoDecorator extends EntradaDecorator {

    /**
     * Constructor de la clase
     */
    public ParqueaderoDecorator(EntradaBase entrada) {
        super(entrada);
    }

    /**
     * Metodos implementados de EntradaBase
     * @return
     */
    @Override
    public String getDescripcion() {
        return entrada.getDescripcion() + " + Parqueadero";
    }

    @Override
    public double getCosto() {
        return entrada.getCosto() + 30000;
    }
}