package model;

import java.time.LocalDate;

public class Incidencia {

    public enum Tipo {
        ERROR_PAGO,
        DOBLE_COMPRA,
        CANCELACION_MASIVA,
        ASIENTO_NO_DISPONIBLE,
        OTRO
    }

    private Tipo tipo;
    private String descripcion;
    private LocalDate fecha;
    private String entidadAfectada; // RF-041: evento, compra o usuario afectado

    public Incidencia(Tipo tipo, String descripcion, LocalDate fecha, String entidadAfectada) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidadAfectada = entidadAfectada;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEntidadAfectada() {
        return entidadAfectada;
    }

    public void setEntidadAfectada(String entidadAfectada) {
        this.entidadAfectada = entidadAfectada;
    }

    @Override
    public String toString() {
        return tipo +
                " - " +
                descripcion +
                " | Afectado: " +
                entidadAfectada +
                " (" + fecha + ")";
    }
}