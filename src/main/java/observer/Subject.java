package observer;

// RF-051 (Observer): contrato que implementan Compra y Evento
public interface Subject {
    void agregarObserver(Observer observer);
    void eliminarObserver(Observer observer);
    void notificarObservers();
}