package state;

public class CompraCancelada implements EstadoCompra {

    /**
     * Metodo para ajustar el estado a "CANCELADA"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "La compra fue cancelada";
    }

    @Override
    public String toString() {
        return "CANCELADA";
    }
}