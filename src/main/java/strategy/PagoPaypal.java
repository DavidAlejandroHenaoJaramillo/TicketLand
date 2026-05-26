package strategy;

public class PagoPaypal implements PagoStrategy {

    private String cuentaPaypal;

    public PagoPaypal(String cuentaPaypal) {
        this.cuentaPaypal = cuentaPaypal;
    }

    public String getCuentaPaypal() { return cuentaPaypal; }

    @Override
    public boolean procesarPago(double monto) {
        System.out.println("[PayPal] Procesando $" + monto
                + " desde: " + cuentaPaypal);
        return monto > 0;
    }

    @Override
    public String toString() { return "Pago por PayPal (" + cuentaPaypal + ")"; }
}