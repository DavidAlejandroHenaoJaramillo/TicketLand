// state/EstadoAsiento.java — ACTUALIZAR
package state;

public interface EstadoAsiento {
    void reservar();
    void bloquear();
    void vender();
    void liberar();
    String manejarEstado();
}