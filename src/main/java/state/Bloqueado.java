package state;

public class Bloqueado implements EstadoAsiento {

    /**
     * Metodo para ajustar el estado a "BLOQUEADO"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "Asiento bloqueado";
    }

    @Override
    public String toString() {
        return "BLOQUEADO";
    }
}