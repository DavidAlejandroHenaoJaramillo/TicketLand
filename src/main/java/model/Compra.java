package model;

import state.EstadoCompra;
import strategy.PagoStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private double totalPagado;
    private LocalDate fechaCompra;
    private EstadoCompra estadoCompra;
    private Usuario usuario;
    private Evento evento;
    private PagoStrategy metodoPago;
    private List<Entrada> entradas;

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
        return totalPagado;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
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

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public void agregarEntrada(Entrada entrada) {
        entradas.add(entrada);
    }

    @Override
    public String toString() {
        return "Compra | Fecha: " +
                fechaCompra +
                " | Total: $" +
                totalPagado;
    }
}