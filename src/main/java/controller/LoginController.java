package controller;

import model.*;

public class LoginController {

    private final TicketLand sistema;

    public LoginController() {
        sistema = TicketLand.getInstance();
    }

    public Persona iniciarSesion(String correo) {
        if (correo == null || correo.isBlank()) {
            return null;
        }
        Usuario usuario = sistema.buscarUsuarioPorCorreo(correo);
        if (usuario != null) {
            Sesion.getInstancia().iniciar(usuario);
            return usuario;
        }
        for (Administrador admin : sistema.getAdministradores()) {
            if (admin.getCorreo().equalsIgnoreCase(correo)) {
                Sesion.getInstancia().iniciar(admin);
                return admin;
            }
        }
        return null;
    }
}