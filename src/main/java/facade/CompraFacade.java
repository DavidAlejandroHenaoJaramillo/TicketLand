package facade;

import decorator.EntradaBase;
import model.Compra;
import model.Evento;
import model.Usuario;
import observer.Notificacion;
import state.CompraPagada;
import strategy.PagoStrategy;
import java.time.LocalDate;

public class CompraFacade {

    /**
     * Metodo para realizar la compra con las validaciones del Decorator
     * @param usuario asociado a la compra
     * @param evento asociado a la compra
     * @param entrada asociada a la compra
     * @param metodoPago acosiada a la compra
     * @return compra realizada con validaciones del Decorator
     */
    public Compra realizarCompra(Usuario usuario, Evento evento, EntradaBase entrada, PagoStrategy metodoPago
    ) {
        Compra compra = new Compra(LocalDate.now(), new CompraPagada(), usuario, evento, metodoPago
        );
        compra.agregarEntrada(entrada);
        boolean pagoExitoso = metodoPago.procesarPago(compra.calcularTotal());
        if (pagoExitoso) {
            usuario.realizarCompra(compra);
            evento.getNotificaciones().notificar("Compra realizada para el evento: " + evento.getNombre());
            return compra;
        }
        return null;
    }
}