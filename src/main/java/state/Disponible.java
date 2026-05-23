package state;

public class Disponible implements EstadoAsiento {

    /**
     * Metodo para ajustar el estado a "DISPONIBLE"
     * @return
     */
    @Override
    public String manejarEstado() {
        return "Asiento disponible";
    }

    @Override
    public String toString() {
        return "DISPONIBLE";
    }
}