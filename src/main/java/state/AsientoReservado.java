package state;

public class AsientoReservado implements EstadoAsiento {
    @Override public void reservar() { throw new IllegalStateException("Ya reservado"); }
    @Override public void bloquear() { /* válido */ }
    @Override public void vender()   { /* válido */ }
    @Override public void liberar()  { /* válido: vuelve a disponible */ }
    @Override public String manejarEstado() { return "RESERVADO"; }
}