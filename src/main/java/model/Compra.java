package model;

import decorator.EntradaBase;
import state.*;
import strategy.PagoStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Entrada;

public class Compra {

    private double totalPagado;
    private LocalDate fechaCompra;
    private EstadoCompra estadoCompra;
    private Usuario usuario;
    private Evento evento;
    private PagoStrategy metodoPago;
    private List<EntradaBase> entradas;

    public Compra(double totalPagado, LocalDate fechaCompra, EstadoCompra estadoCompra, Usuario usuario, Evento evento, PagoStrategy metodoPago) {

        this.totalPagado = totalPagado;
        this.fechaCompra = fechaCompra;
        this.estadoCompra = estadoCompra;
        this.usuario = usuario;
        this.evento = evento;
        this.metodoPago = metodoPago;
        entradas = new ArrayList<>();
    }

    public double getTotalPagado() {
        return calcularTotal();
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public PagoStrategy getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(PagoStrategy metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<EntradaBase> getEntradas() {
        return entradas;
    }

    public void agregarEntrada(EntradaBase entrada){
        entradas.add(entrada);
    }

    public String mostrarEstadoCompra() {
        return estadoCompra.manejarEstado();
    }

    public double calcularTotal() {
        double total = 0;
        for (EntradaBase entrada : entradas) {
            total += entrada.getCosto();
        }
        return total;
    }

    // RF-035: modificar compra antes de pagar — elimina una entrada y libera su asiento
    public boolean eliminarEntrada(EntradaBase entradaAEliminar) {
        if (!(estadoCompra instanceof CompraCreada)) {
            return false; // solo se puede modificar si la compra aún no fue pagada
        }
        // si la entrada tiene asiento, lo liberamos antes de eliminar
        if (entradaAEliminar instanceof Entrada e && e.getAsiento() != null) {
            e.getAsiento().setEstado(new Disponible());
        }
        return entradas.remove(entradaAEliminar);
    }

    // RF-007: pagar la compra usando la estrategia de pago configurada
    public boolean pagar() {
        if (!(estadoCompra instanceof CompraCreada)) {
            return false; // solo se puede pagar una compra recién creada
        }
        if (entradas.isEmpty()) {
            return false; // no tiene sentido pagar una compra sin entradas
        }
        boolean exitoso = metodoPago.procesarPago(calcularTotal());
        if (exitoso) {
            estadoCompra = new CompraPagada();
            // marcar cada asiento como vendido
            for (EntradaBase eb : entradas) {
                if (eb instanceof Entrada e && e.getAsiento() != null) {
                    e.getAsiento().setEstado(new Vendido());
                }
            }
        }
        return exitoso;
    }

    // RF-008: confirmar una compra que ya fue pagada
    public boolean confirmar() {
        if (!(estadoCompra instanceof CompraPagada)) {
            return false; // solo se puede confirmar si ya fue pagada
        }
        estadoCompra = new CompraConfirmada();
        return true;
    }

    // RF-036: cancelar la compra y liberar los asientos reservados
    public boolean cancelar() {
        if (estadoCompra instanceof CompraCancelada
                || estadoCompra instanceof CompraReembolsada) {
            return false; // ya está en un estado final, no se puede cancelar de nuevo
        }
        for (EntradaBase eb : entradas) {
            if (eb instanceof Entrada e && e.getAsiento() != null) {
                e.getAsiento().setEstado(new Disponible());
            }
        }
        estadoCompra = new CompraCancelada();
        return true;
    }

    @Override
    public String toString() {
        return "Compra | Fecha: " +
                fechaCompra +
                " | Total: $" +
                calcularTotal();
    }
}