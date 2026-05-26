package model;

import builder.Compra;
import observer.Observer;
import strategy.PagoStrategy;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import state.EstadoCompra;

public class Usuario extends Persona implements Observer {

    private List<Compra> historialCompras;
    private List<String> notificaciones;
    // RF-021: métodos de pago simulados asociados al usuario
    private List<PagoStrategy> metodosDepago;

    public Usuario(int id, String nombre, String correo, String telefono) {
        super(id, nombre, correo, telefono);
        historialCompras = new ArrayList<>();
        notificaciones = new ArrayList<>();
        metodosDepago = new ArrayList<>();
    }

    public List<Compra> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<Compra> historialCompras) {
        this.historialCompras = historialCompras;
    }

    public List<String> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<String> notificaciones) {
        this.notificaciones = notificaciones;
    }

    // RF-021: gestionar métodos de pago del usuario
    public List<PagoStrategy> getMetodosDepago() {
        return metodosDepago;
    }

    public void agregarMetodoPago(PagoStrategy metodoPago) {
        metodosDepago.add(metodoPago);
    }

    public boolean eliminarMetodoPago(PagoStrategy metodoPago) {
        return metodosDepago.remove(metodoPago);
    }

    public String registrarse() {
        return "Usuario registrado correctamente";
    }

    public String iniciarSesion() {
        return "Inicio de sesión exitoso";
    }

    public String consultarEventos() {
        return "Consultando eventos disponibles";
    }

    public void realizarCompra(Compra compra) {
        historialCompras.add(compra);
    }

    // RF-010: consultar historial con filtros por fecha, evento y estado
    public List<Compra> consultarHistorial(LocalDate desde, LocalDate hasta,
                                           Evento evento, EstadoCompra estado) {
        List<Compra> resultado = new ArrayList<>();
        for (Compra c : historialCompras) {
            boolean coincide = true;
            if (desde != null && c.getFechaCompra().isBefore(desde)) coincide = false;
            if (hasta != null && c.getFechaCompra().isAfter(hasta)) coincide = false;
            if (evento != null && !c.getEvento().equals(evento)) coincide = false;
            if (estado != null && !c.getEstadoCompra().getClass().equals(estado.getClass())) coincide = false;
            if (coincide) resultado.add(c);
        }
        return resultado;
    }

    // RF-002: actualizar datos del perfil
    public void actualizarPerfil(String nombre, String correo, String telefono) {
        if (nombre != null) this.nombre = nombre;
        if (correo != null) this.correo = correo;
        if (telefono != null) this.telefono = telefono;
    }

    // RF-022: consultar detalle de una compra específica
    public Compra buscarCompra(int indice) {
        if (indice >= 0 && indice < historialCompras.size()) {
            return historialCompras.get(indice);
        }
        return null;
    }

    @Override
    public void actualizar(String mensaje) {
        notificaciones.add(mensaje);
    }

    @Override
    public String toString() {
        return nombre + " - " + correo;
    }
}