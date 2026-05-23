package state;

public class Reservado implements EstadoAsiento {

    /**
     * Metodo para ajustar el estado a "RESERVADO"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "Asiento reservado";
    }

    @Override
    public String toString() {
        return "RESERVADO";
    }
}