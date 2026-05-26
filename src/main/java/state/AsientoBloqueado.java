package state;

public class AsientoBloqueado implements EstadoAsiento {
    @Override public void reservar() { throw new IllegalStateException("Bloqueado"); }
    @Override public void bloquear() { /* no-op */ }
    @Override public void vender()   { throw new IllegalStateException("Bloqueado"); }
    @Override public void liberar()  { /* válido */ }
    @Override public String manejarEstado() { return "BLOQUEADO"; }
}