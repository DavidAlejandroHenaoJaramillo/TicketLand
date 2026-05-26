package observer;

import java.util.ArrayList;
import java.util.List;

public class Notificacion {

    /**
     * Atributos de la clase
     */
    private List<Observer> observadores;

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
    public List<Observer> getObservadores() {
        return observadores;
    }

    public void setObservadores(List<Observer> observadores) {
        this.observadores = observadores;
    }

    /**
     * Metodo para agregar observadores
     * @param observer
     */
    public void suscribir(Observer observer) {
        observadores.add(observer);
    }

    /**
     * Metodo para eliminar observadores
     * @param observer
     */
    public void eliminar(Observer observer) {
        observadores.remove(observer);
    }

    /**
     * Metodo para actualizar el mensaje de los observadores
     * @param mensaje
     */
    public void notificar(String mensaje) {
        for (Observer observer : observadores) {
            observer.actualizar(mensaje);
        }
    }
}
