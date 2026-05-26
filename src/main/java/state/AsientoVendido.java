package state;

public class AsientoVendido implements EstadoAsiento {
    @Override public void reservar() { throw new IllegalStateException("Ya vendido"); }
    @Override public void bloquear() { throw new IllegalStateException("Ya vendido"); }
    @Override public void vender()   { throw new IllegalStateException("Ya vendido"); }
    @Override public void liberar()  { /* válido: reembolso libera el asiento */ }
    @Override public String manejarEstado() { return "VENDIDO"; }
}