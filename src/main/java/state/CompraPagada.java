package state;

public class CompraPagada implements EstadoCompra {

    /**
     * Metodo para ajustar el estado a "PAGADA"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "La compra fue pagada exitosamente";
    }

    @Override
    public String toString() {
        return "PAGADA";
    }
}