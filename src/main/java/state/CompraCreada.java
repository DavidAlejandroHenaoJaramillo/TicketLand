package state;

public class CompraCreada implements EstadoCompra {

    /**
     * Metodo para ajustar el estado a "CREADA"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "La compra ha sido creada";
    }

    @Override
    public String toString() {
        return "CREADA";
    }
}