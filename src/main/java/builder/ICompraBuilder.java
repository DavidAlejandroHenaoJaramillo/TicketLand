package builder;

import model.*;

// RF-049 (Builder): contrato del patrón
public interface ICompraBuilder {
    void agregarUsuario(Usuario usuario);
    void agregarEvento(Evento evento);
    void agregarEntrada(Entrada entrada);
    void agregarServicio(ServicioAdicional servicio);
    void agregarPago(Pago pago);
    Compra construir();
}