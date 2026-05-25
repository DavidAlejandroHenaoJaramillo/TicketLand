package builder;

import decorator.EntradaBase;
import model.Compra;
import model.Evento;
import model.Usuario;
import state.CompraCreada;
import strategy.PagoStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * RF-034, RF-049: Builder para construir una Compra paso a paso.
 * Patrón creacional Builder — resuelve la creación de Compra con múltiples
 * parámetros opcionales (entradas, servicios adicionales) de forma legible
 * y sin constructores sobrecargados.
 */
public class CompraBuilder {

    private int idCompra;
    private Usuario usuario;
    private Evento evento;
    private PagoStrategy metodoPago;
    private List<EntradaBase> entradas;

    public CompraBuilder(int idCompra) {
        this.idCompra = idCompra;
        this.entradas = new ArrayList<>();
    }

    // RF-034: definir el usuario que realiza la compra
    public CompraBuilder conUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    // RF-034: definir el evento al que pertenece la compra
    public CompraBuilder conEvento(Evento evento) {
        this.evento = evento;
        return this;
    }

    // RF-034: agregar una entrada a la compra
    public CompraBuilder conEntrada(EntradaBase entrada) {
        this.entradas.add(entrada);
        return this;
    }

    // RF-021: definir el método de pago a usar
    public CompraBuilder conMetodoPago(PagoStrategy metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    // RF-034: construir y retornar la Compra lista para usar
    public Compra build() {
        if (usuario == null || evento == null || metodoPago == null) {
            throw new IllegalStateException(
                    "CompraBuilder: usuario, evento y metodoPago son obligatorios"
            );
        }
        Compra compra = new Compra(
                idCompra,
                LocalDate.now(),
                new CompraCreada(),
                usuario,
                evento,
                metodoPago
        );
        for (EntradaBase entrada : entradas) {
            compra.agregarEntrada(entrada);
        }
        return compra;
    }
}
