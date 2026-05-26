package state;

public class Vendido implements EstadoAsiento {

    @Override
    public void reservar() {
        throw new IllegalStateException("El asiento ya fue vendido.");
    }

    @Override
    public void bloquear() {
        throw new IllegalStateException("El asiento ya fue vendido.");
    }

    @Override
    public void vender() {
        throw new IllegalStateException("El asiento ya fue vendido.");
    }

    @Override
    public void liberar() { /* transición válida — reembolso libera el asiento */ }

    @Override
    public String manejarEstado() { return "Asiento vendido"; }

    @Override
    public String toString() { return "VENDIDO"; }
}