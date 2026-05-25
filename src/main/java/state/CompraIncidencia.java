package state;

/**
 * RF-008: Estado INCIDENCIA de una compra.
 * Se activa cuando ocurre un problema operativo asociado a la compra
 * (ej: doble cobro, error de pago no resuelto, asiento inválido).
 * Patrón State: cada estado encapsula su comportamiento y representación.
 */
public class CompraIncidencia implements EstadoCompra {

    @Override
    public String manejarEstado() {
        return "La compra tiene una incidencia registrada pendiente de resolución.";
    }

    @Override
    public String toString() {
        return "INCIDENCIA";
    }
}