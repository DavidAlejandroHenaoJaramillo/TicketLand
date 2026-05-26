package state;

public class Bloqueado implements EstadoAsiento {

    @Override
    public void reservar() {
        throw new IllegalStateException("El asiento está bloqueado.");
    }

    @Override
    public void bloquear() { /* ya bloqueado, no hace nada */ }

    @Override
    public void vender() {
        throw new IllegalStateException("El asiento está bloqueado.");
    }

    @Override
    public void liberar() { /* transición válida — admin desbloquea */ }

    @Override
    public String manejarEstado() { return "Asiento bloqueado"; }

    @Override
    public String toString() { return "BLOQUEADO"; }
}