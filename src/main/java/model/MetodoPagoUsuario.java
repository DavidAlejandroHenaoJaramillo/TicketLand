package model;

public class MetodoPagoUsuario {

    private String id;
    private TipoMetodoPago tipoMetodo;
    private String numeroReferencia;
    private double saldo;
    private boolean activo;

    public MetodoPagoUsuario(String id, TipoMetodoPago tipoMetodo,
                             String numeroReferencia, double saldo, boolean activo) {
        this.id = id;
        this.tipoMetodo = tipoMetodo;
        this.numeroReferencia = numeroReferencia;
        this.saldo = saldo;
        this.activo = activo;
    }

    public String getId() { return id; }
    public TipoMetodoPago getTipoMetodo() { return tipoMetodo; }
    public String getNumeroReferencia() { return numeroReferencia; }
    public double getSaldo() { return saldo; }
    public boolean isActivo() { return activo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return tipoMetodo + " - " + numeroReferencia + " (Saldo: $" + saldo + ")";
    }
}