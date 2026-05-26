package strategy;

public class PagoNequi implements PagoStrategy {

    private String numeroCelular;

    public PagoNequi(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getNumeroCelular() { return numeroCelular; }

    @Override
    public boolean procesarPago(double monto) {
        System.out.println("[Nequi] Procesando $" + monto
                + " desde: " + numeroCelular);
        return monto > 0;
    }

    @Override
    public String toString() { return "Pago por Nequi (" + numeroCelular + ")"; }
}