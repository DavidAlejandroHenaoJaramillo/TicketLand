package model;

public class PoliticaCancelacion {

    private boolean permiteCancelacion;
    private int horasLimite;

    public PoliticaCancelacion(boolean permiteCancelacion, int horasLimite) {
        this.permiteCancelacion = permiteCancelacion;
        this.horasLimite = horasLimite;
    }

    public boolean getPermiteCancelacion() { return permiteCancelacion; }
    public int getHorasLimite() { return horasLimite; }

    public boolean puedeCancelar(long horasRestantes) {
        return permiteCancelacion && horasRestantes >= horasLimite;
    }

    public double calcularReembolso(double montoTotal) {
        if (!permiteCancelacion) return 0;
        return montoTotal;
    }

    @Override
    public String toString() {
        return permiteCancelacion
                ? "Cancelación permitida hasta " + horasLimite + "h antes"
                : "No reembolsable";
    }
}