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
    protected Recinto recinto;

    protected Notificacion notificaciones;

    public Evento(String nombre, String categoria,
                  LocalDate fecha, String ciudad,
                  String descripcion, String politicas,Recinto recinto) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.politicas = politicas;
        this.recinto = recinto;
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

    //obtener recinto asociado al evento
    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    //consultar disponibilidad del evento por zonas
    public boolean consultarDisponibilidad() {
        if (recinto == null) return false;
        for (Zona zona : recinto.getZonas()) {
            if (zona.hayDisponibilidad()) {
                return true; // hay al menos una zona con asientos disponibles
            }
        }
        return false;
    }

    //publicar, pausar y cancelar evento
    public void activar() {
        if (estado == EstadoEvento.EN_ESPERA || estado == EstadoEvento.PAUSADO) {
            cambiarEstado(EstadoEvento.ACTIVO);
        }
    }

    public void pausar() {
        if (estado == EstadoEvento.ACTIVO) {
            cambiarEstado(EstadoEvento.PAUSADO);
        }
    }

    public void cancelar() {
        if (estado != EstadoEvento.CANCELADO) {
            cambiarEstado(EstadoEvento.CANCELADO);
        }
    }

    public void finalizar() {
        if (estado == EstadoEvento.ACTIVO) {
            cambiarEstado(EstadoEvento.FINALIZADO);
        }

    }
    @Override
    public String toString() {
        return nombre + " - " + ciudad +
                " (" + fecha + ")";
    }
}