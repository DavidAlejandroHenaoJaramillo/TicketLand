package controller;

import decorator.EntradaBase;
import decorator.EntradaVIP;
import decorator.MerchandisingDecorator;
import decorator.ParqueaderoDecorator;
import decorator.SeguroDecorator;
import model.Asiento;
import model.Compra;
import model.Entrada;
import model.EstadoEntrada;
import model.Evento;
import model.TicketLand;
import model.Usuario;
import model.Zona;
import strategy.PagoStrategy;
import strategy.PagoTarjeta;

import java.util.List;
import java.util.stream.Collectors;

public class CompraController {

    private final TicketLand sistema = TicketLand.getInstance();

    public Compra comprarEntrada(
            Usuario usuario,
            Evento evento,
            Zona zona,
            Asiento asiento,
            PagoStrategy metodoPago,
            boolean vip,
            boolean seguro,
            boolean merchandising,
            boolean parqueadero
    ) {
        if (usuario == null) {
            throw new IllegalArgumentException("No hay usuario activo.");
        }

        if (evento == null) {
            throw new IllegalArgumentException("Selecciona un evento.");
        }

        if (zona == null || !zona.hayDisponibilidad()) {
            throw new IllegalArgumentException("No hay asientos disponibles en la zona seleccionada.");
        }

        Asiento asientoSeleccionado = asiento != null ? asiento : zona.getAsientosDisponibles().get(0);

        if (!asientoSeleccionado.reservar()) {
            throw new IllegalArgumentException("El asiento seleccionado no está disponible.");
        }

        PagoStrategy pago = metodoPago != null
                ? metodoPago
                : new PagoTarjeta("0000-0000-0000-0000", usuario.getNombre());

        EntradaBase entrada = new Entrada(
                sistema.getCompras().size() + 1,
                zona.getPrecioBase(),
                EstadoEntrada.ACTIVA,
                zona,
                asientoSeleccionado
        );

        if (vip) entrada = new EntradaVIP(entrada);
        if (seguro) entrada = new SeguroDecorator(entrada);
        if (merchandising) entrada = new MerchandisingDecorator(entrada);
        if (parqueadero) entrada = new ParqueaderoDecorator(entrada);

        Compra compra = sistema.crearCompra(usuario, evento, pago);
        compra.agregarEntrada(entrada);

        if (!compra.pagar()) {
            throw new IllegalArgumentException("No se pudo procesar el pago.");
        }

        return compra;
    }

    public boolean cancelarCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("Selecciona una compra primero.");
        }

        return compra.cancelar();
    }

    public List<Compra> filtrarHistorial(Usuario usuario, String estado) {
        if (usuario == null) {
            return List.of();
        }

        if (estado == null || estado.equals("Todas")) {
            return usuario.getHistorialCompras();
        }

        return usuario.getHistorialCompras()
                .stream()
                .filter(compra -> compra.getEstadoCompra().toString().equals(estado))
                .collect(Collectors.toList());
    }
}