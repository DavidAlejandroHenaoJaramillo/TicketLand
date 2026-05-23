package state;

public class CompraReembolsada implements EstadoCompra {

    /**
     * Metodo para ajustar el estado a "REEMBOLSADA"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "La compra fue reembolsada";
    }

    @Override
    public String toString() {
        return "REEMBOLSADA";
    }
}