package model;

import observer.Observador;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona implements Observador {

    private List<Compra> historialCompras;
    private List<String> notificaciones;

    public Usuario(int id, String nombre, String correo, String telefono) {

        super(id, nombre, correo, telefono);
        historialCompras = new ArrayList<>();
        notificaciones = new ArrayList<>();
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

    @Override
    public void actualizar(String mensaje) {
        notificaciones.add(mensaje);
    }

    @Override
    public String toString() {
        return nombre + " - " + correo;
    }
}