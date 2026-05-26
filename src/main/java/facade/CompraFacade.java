package facade;

import decorator.EntradaBase;
import builder.Compra;
import model.Evento;
import model.Usuario;
import strategy.PagoStrategy;

public class CompraFacade {

    // RF-034: simplifica el proceso de crear y pagar una compra
    public Compra realizarCompra(Usuario usuario, Evento evento,
                                 EntradaBase entrada, PagoStrategy metodoPago) {
        // RF-034, RF-049: usa CompraBuilder para construir la compra correctamente
        Compra compra = new builder.CompraConcretaBuilder()
                .conUsuario(usuario)
                .conEvento(evento)
                .conMetodoPago(metodoPago)
                .build();
        compra.agregarEntrada(entrada);
        boolean exitoso = compra.pagar();
        if (exitoso) {
            usuario.realizarCompra(compra);
            evento.getNotificaciones().notificar(
                    "Compra realizada para el evento: " + evento.getNombre()
            );
            return compra;
        }
        return null;
    }
}