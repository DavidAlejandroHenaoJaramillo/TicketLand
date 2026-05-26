package state;

// RF-032 (State): comportamiento cuando el asiento está disponible
public class AsientoDisponible implements EstadoAsiento {
    @Override public void reservar() { /* válido */ }
    @Override public void bloquear() { /* válido */ }
    @Override public void vender()   { /* válido */ }
    @Override public void liberar()  { /* ya libre, no-op */ }
    @Override public String manejarEstado() { return "DISPONIBLE"; }
}