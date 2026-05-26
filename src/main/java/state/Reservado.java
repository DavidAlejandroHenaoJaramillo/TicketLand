package state;

public class Reservado implements EstadoAsiento {

    @Override
    public void reservar() {
        throw new IllegalStateException("El asiento ya está reservado.");
    }

    @Override
    public void bloquear() { /* transición válida */ }

    @Override
    public void vender() { /* transición válida */ }

    @Override
    public void liberar() { /* transición válida — vuelve a Disponible */ }

    @Override
    public String manejarEstado() { return "Asiento reservado"; }

    @Override
    public String toString() { return "RESERVADO"; }
}