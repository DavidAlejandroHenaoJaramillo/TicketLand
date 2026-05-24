package model;

import decorator.EntradaBase;
import state.EstadoCompra;
import strategy.PagoStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private LocalDate fechaCompra;
    private EstadoCompra estadoCompra;
    private Usuario usuario;
    private Evento evento;
    private PagoStrategy metodoPago;
    private List<EntradaBase> entradas;

    public Compra(LocalDate fechaCompra, EstadoCompra estadoCompra, Usuario usuario, Evento evento, PagoStrategy metodoPago) {
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

    @Override
    public String toString() {
        return "Compra | Fecha: " +
                fechaCompra +
                " | Total: $" +
                calcularTotal();
    }
}