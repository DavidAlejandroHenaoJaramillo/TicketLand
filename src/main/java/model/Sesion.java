package model;

import java.time.LocalDateTime;

public class Sesion {

    private static Sesion instancia;
    private Usuario usuarioActual;
    private boolean activa;
    private LocalDateTime fechaInicio;

    private Sesion() {
        activa = false;
    }

    public static synchronized Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public void iniciar(Usuario usuario) {
        this.usuarioActual = usuario;
        this.activa = true;
        this.fechaInicio = LocalDateTime.now();
        System.out.println("[Sesion] Sesión iniciada: " + usuario.getNombre());
    }

    public void cerrar() {
        System.out.println("[Sesion] Sesión cerrada: "
                + (usuarioActual != null ? usuarioActual.getNombre() : "ninguno"));
        this.usuarioActual = null;
        this.activa = false;
        this.fechaInicio = null;
    }

    public boolean estaActiva() { return activa; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }

    public boolean esAdmin() {
        return activa && usuarioActual instanceof Administrador;
    }
}