package builder;

import model.*;
import state.CompraCreada;
import strategy.PagoStrategy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraConcretaBuilder implements ICompraBuilder {

    private Usuario usuario;
    private Evento evento;
    private PagoStrategy metodoPago;
    private final List<Entrada> entradas = new ArrayList<>();
    private final List<ServicioAdicional> servicios = new ArrayList<>();
    private Pago pago;

    @Override
    public void agregarUsuario(Usuario usuario) { this.usuario = usuario; }

    @Override
    public void agregarEvento(Evento evento) { this.evento = evento; }

    @Override
    public void agregarEntrada(Entrada entrada) { entradas.add(entrada); }

    @Override
    public void agregarServicio(ServicioAdicional servicio) { servicios.add(servicio); }

    @Override
    public void agregarPago(Pago pago) { this.pago = pago; }

    // Fluent API adicional para uso cómodo
    public CompraConcretaBuilder conUsuario(Usuario usuario) {
        this.usuario = usuario; return this;
    }
    public CompraConcretaBuilder conEvento(Evento evento) {
        this.evento = evento; return this;
    }
    public CompraConcretaBuilder agregarItem(ItemCompra item) {
        return this; // compatibilidad con diagrama
    }

    public CompraConcretaBuilder conMetodoPago(PagoStrategy metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    @Override
    public Compra construir() {
        if (usuario == null || evento == null)
            throw new IllegalStateException("Compra requiere usuario y evento.");
        int id = (int)(System.currentTimeMillis() % 100000);
        Compra compra = new Compra(id, LocalDate.now(),
                new CompraCreada(), usuario, evento, metodoPago); //
        entradas.forEach(compra::agregarEntrada);
        servicios.forEach(compra::agregarServicio);
        return compra;
    }

    // Alias para el diagrama
    public Compra build() { return construir(); }
}