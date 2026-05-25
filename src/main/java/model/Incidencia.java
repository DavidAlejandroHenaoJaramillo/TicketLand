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

    // RF-041: identificador único de incidencia
    private int idIncidencia;
    private Tipo tipo;
    private String descripcion;
    private LocalDate fecha;
    private String entidadAfectada;

    public Incidencia(int idIncidencia, Tipo tipo, String descripcion,
                      LocalDate fecha, String entidadAfectada) {
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidadAfectada = entidadAfectada;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
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
        return "Incidencia #" + idIncidencia +
                " | " + tipo +
                " - " + descripcion +
                " | Afectado: " + entidadAfectada +
                " (" + fecha + ")";
    }
}