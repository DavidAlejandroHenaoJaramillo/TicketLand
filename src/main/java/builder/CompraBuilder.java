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
 * parametros opcionales (entradas, servicios adicionales) de forma legible
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

    public CompraBuilder conUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public CompraBuilder conEvento(Evento evento) {
        this.evento = evento;
        return this;
    }

    public CompraBuilder conEntrada(EntradaBase entrada) {
        this.entradas.add(entrada);
        return this;
    }

    public CompraBuilder conMetodoPago(PagoStrategy metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

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
