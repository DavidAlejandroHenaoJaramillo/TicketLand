package strategy;

public class PagoEfectivo implements PagoStrategy {

    /**
     * Metodo para procesar el pago por Efectivo
     * @param monto a pagar
     * @return
     */
    @Override
    public boolean procesarPago(double monto) {
        return monto > 0;
    }

    @Override
    public String toString() {
        return "Pago en Efectivo";
    }
}