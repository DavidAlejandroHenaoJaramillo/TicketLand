package model;

public class PoliticaReembolso {

    private boolean permiteReembolso;
    private double porcentajeReembolso;

    public PoliticaReembolso(boolean permiteReembolso, double porcentajeReembolso) {
        this.permiteReembolso = permiteReembolso;
        this.porcentajeReembolso = porcentajeReembolso;
    }

    public boolean getPermiteReembolso() { return permiteReembolso; }
    public double getPorcentajeReembolso() { return porcentajeReembolso; }

    public double calcularMontoReembolso(double montoTotal) {
        if (!permiteReembolso) return 0;
        return montoTotal * porcentajeReembolso;
    }

    @Override
    public String toString() {
        return permiteReembolso
                ? "Reembolso del " + (int)(porcentajeReembolso * 100) + "%"
                : "Sin reembolso";
    }
}