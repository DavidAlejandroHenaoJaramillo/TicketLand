package builder;

import decorator.EntradaBase;
import model.Entrada;
import model.Evento;
import model.ServicioAdicional;
import model.Usuario;
import state.*;
import strategy.PagoStrategy;
import observer.Observer;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    // RF-034: identificador único de compra
    private int idCompra;
    private LocalDate fechaCompra;
    private EstadoCompra estadoCompra;
    private Usuario usuario;
    private Evento evento;
    private PagoStrategy metodoPago;
    private List<EntradaBase> entradas;
    private List<ServicioAdicional> serviciosAdicionales;
    private List<Observer> observers;

    public Compra(int idCompra, LocalDate fechaCompra, EstadoCompra estadoCompra,
                  Usuario usuario, Evento evento, PagoStrategy metodoPago) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.estadoCompra = estadoCompra;
        this.usuario = usuario;
        this.evento = evento;
        this.metodoPago = metodoPago;
        entradas = new ArrayList<>();
        observers = new ArrayList<>();
        serviciosAdicionales = new ArrayList<>();
    }

    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public double getTotalPagado() { return calcularTotal(); }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }

    public EstadoCompra getEstadoCompra() { return estadoCompra; }
    public void setEstadoCompra(EstadoCompra estadoCompra) { this.estadoCompra = estadoCompra; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    public PagoStrategy getMetodoPago() { return metodoPago; }
    public void setMetodoPago(PagoStrategy metodoPago) { this.metodoPago = metodoPago; }

    public List<EntradaBase> getEntradas() { return entradas; }

    public void agregarEntrada(EntradaBase entrada) {
        entradas.add(entrada);
    }

    public String mostrarEstadoCompra() {
        return estadoCompra.manejarEstado();
    }


    // ── Servicios adicionales ─────────────────────────────────────────────────

    public void agregarServicio(ServicioAdicional servicio) {
        serviciosAdicionales.add(servicio);
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public double calcularTotal() {
        double total = 0;
        for (EntradaBase entrada : entradas) {
            total += entrada.getCosto();
        }
        return total;
    }

    // RF-035: modificar compra antes de pagar — elimina entrada y libera asiento
    public boolean eliminarEntrada(EntradaBase entradaAEliminar) {
        if (!(estadoCompra instanceof CompraCreada)) return false;
        if (entradaAEliminar instanceof Entrada e && e.getAsiento() != null) {
            e.getAsiento().setEstado(new Disponible());
        }
        return entradas.remove(entradaAEliminar);
    }

    // RF-007: pagar la compra usando la estrategia de pago configurada
    public boolean pagar() {
        if (!(estadoCompra instanceof CompraCreada)) return false;
        if (entradas.isEmpty()) return false;
        boolean exitoso = metodoPago.procesarPago(calcularTotal());
        if (exitoso) {
            estadoCompra = new CompraPagada();
            for (EntradaBase eb : entradas) {
                if (eb instanceof Entrada e && e.getAsiento() != null) {
                    e.getAsiento().setEstado(new Vendido());
                }
            }
        }
        return exitoso;
    }

    // RF-008: confirmar una compra ya pagada
    public boolean confirmar() {
        if (!(estadoCompra instanceof CompraPagada)) return false;
        estadoCompra = new CompraConfirmada();
        return true;
    }

    // RF-036, RF-040: cancelar compra y anular todas las entradas asociadas
    public boolean cancelar() {
        if (estadoCompra instanceof CompraCancelada
                || estadoCompra instanceof CompraReembolsada) return false;
        for (EntradaBase eb : entradas) {
            if (eb instanceof Entrada e) {
                e.anular(); // RF-040: anula entrada y libera asiento
            }
        }
        estadoCompra = new CompraCancelada();
        return true;
    }

    // RF-016: reembolsar compra (solo si está pagada o confirmada)
    // Simula la devolución del dinero al cliente y anula las entradas
    public boolean reembolsar() {
        if (!(estadoCompra instanceof CompraPagada)
                && !(estadoCompra instanceof CompraConfirmada)) {
            return false; // solo se reembolsa si ya fue pagada o confirmada
        }
        for (EntradaBase eb : entradas) {
            if (eb instanceof Entrada e) {
                e.anular(); // libera asiento y anula entrada
            }
        }
        estadoCompra = new CompraReembolsada();
        System.out.println("Reembolso simulado de $" + calcularTotal()
                + " para compra #" + idCompra);
        return true;
    }

    // RF-008: marcar compra con incidencia operativa
    public boolean marcarComoIncidencia() {
        if (estadoCompra instanceof CompraCancelada
                || estadoCompra instanceof CompraReembolsada) {
            return false;
        }
        estadoCompra = new CompraIncidencia();
        return true;
    }

    @Override
    public String toString() {
        return "Compra #" + idCompra +
                " | Fecha: " + fechaCompra +
                " | Total: $" + calcularTotal();
    }
}