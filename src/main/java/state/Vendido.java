package state;

public class Vendido implements EstadoAsiento {

    /**
     * Metodo para ajustar el estado a "VENDIDO"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "Asiento vendido";
    }

    @Override
    public String toString() {
        return "VENDIDO";
    }
}