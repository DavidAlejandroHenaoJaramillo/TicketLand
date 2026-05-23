package observer;

import java.util.ArrayList;
import java.util.List;

public class Notificacion {

    /**
     * Atributos de la clase
     */
    private List<Observador> observadores;

    /**
     * Constructor de la clase
     */
    public Notificacion() {
        observadores = new ArrayList<>();
    }

    /**
     * Getters y Setters
     * @return
     */
    public List<Observador> getObservadores() {
        return observadores;
    }

    public void setObservadores(List<Observador> observadores) {
        this.observadores = observadores;
    }

    /**
     * Metodo para agregar observadores
     * @param observador
     */
    public void suscribir(Observador observador) {
        observadores.add(observador);
    }

    /**
     * Metodo para eliminar observadores
     * @param observador
     */
    public void eliminar(Observador observador) {
        observadores.remove(observador);
    }

    /**
     * Metodo para actualizar el mensaje de los observadores
     * @param mensaje
     */
    public void notificar(String mensaje) {
        for (Observador observador : observadores) {
            observador.actualizar(mensaje);
        }
    }
}
