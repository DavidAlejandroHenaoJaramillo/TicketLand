package strategy;

public class PagoPSE implements PagoStrategy {

    /**
     * Atributos de la clase
     */
    private String banco;

    /**
     * Constructor de la clase
     */
    public PagoPSE(String banco) {
        this.banco = banco;
    }

    /**
     * Getters y Setters
     * @return
     */
    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * Metodo para procesar el pago por PSE
     * @param monto a pagar
     * @return
     */
    @Override
    public boolean procesarPago(double monto) {
        return monto > 0;
    }

    @Override
    public String toString() {
        return "Pago por PSE";
    }
}