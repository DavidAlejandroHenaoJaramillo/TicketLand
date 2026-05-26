package state;

public class Disponible implements EstadoAsiento {

    @Override
    public void reservar() { /* transición válida — el Asiento cambia su estado */ }

    @Override
    public void bloquear() { /* transición válida */ }

    @Override
    public void vender() { /* transición válida */ }

    @Override
    public void liberar() { /* ya está libre, no hace nada */ }

    @Override
    public String manejarEstado() { return "Asiento disponible"; }

    @Override
    public String toString() { return "DISPONIBLE"; }
}