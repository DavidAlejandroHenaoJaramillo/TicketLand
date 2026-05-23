package model;

import java.time.LocalDate;

public class Incidencia {

    private String tipo;
    private String descripcion;
    private LocalDate fecha;

    public Incidencia(String tipo, String descripcion, LocalDate fecha) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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

    @Override
    public String toString() {
        return tipo +
                " - " +
                descripcion +
                " (" +
                fecha +
                ")";
    }
}