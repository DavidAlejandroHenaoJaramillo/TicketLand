package model;

import observer.Notificacion;

import java.time.LocalDate;

public abstract class Evento {

    protected String nombre;
    protected String categoria;
    protected LocalDate fecha;
    protected String ciudad;
    protected String descripcion;
    protected String politicas;
    protected EstadoEvento estado;

    protected Notificacion notificaciones;

    public Evento(String nombre, String categoria,
                  LocalDate fecha, String ciudad,
                  String descripcion, String politicas) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.politicas = politicas;
        estado = EstadoEvento.EN_ESPERA;
        notificaciones = new Notificacion();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPoliticas() {
        return politicas;
    }

    public void setPoliticas(String politicas) {
        this.politicas = politicas;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public Notificacion getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Notificacion notificaciones) {
        this.notificaciones = notificaciones;
    }

    public void cambiarEstado(EstadoEvento nuevoEstado) {
        estado = nuevoEstado;
        notificarCambios();
    }

    public void notificarCambios() {
        notificaciones.notificar(
                "El evento " + nombre +
                        " cambió a " + estado
        );
    }

    @Override
    public String toString() {
        return nombre + " - " + ciudad +
                " (" + fecha + ")";
    }
}