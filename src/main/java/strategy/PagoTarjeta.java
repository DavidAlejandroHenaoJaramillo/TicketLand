package strategy;

public class PagoTarjeta implements PagoStrategy {

    /**
     * Atributos de la clase
     */
    private String numeroTarjeta;
    private String titular;

    /**
     * Constructor de la clase
     */
    public PagoTarjeta(String numeroTarjeta, String titular) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
    }

    /**
     * Getters y Setters
     * @return
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * Metodo para procesar el pago por Tarjeta
     * @param monto a pagar
     * @return
     */
    @Override
    public boolean procesarPago(double monto) {
        return monto > 0;
    }

    @Override
    public String toString() {
        return "Pago con Tarjeta";
    }
}