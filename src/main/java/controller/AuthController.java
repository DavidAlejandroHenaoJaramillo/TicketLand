package controller;

import model.Administrador;
import model.TicketLand;
import model.Usuario;

public class AuthController {

    private final TicketLand sistema = TicketLand.getInstance();

    public Usuario loginUsuario(String correo, String password) {
        if (correo == null || correo.isBlank() || password == null || password.isBlank()) {
            return null;
        }

        Usuario usuario = sistema.buscarUsuarioPorCorreo(correo.trim());

        if (usuario == null) {
            return null;
        }

        return usuario.getPassword().equals(password) ? usuario : null;
    }

    public Administrador loginAdmin(String correo, String password) {
        if (correo == null || correo.isBlank() || password == null || password.isBlank()) {
            return null;
        }

        for (Administrador admin : sistema.getAdministradores()) {
            if (admin.getCorreo().equalsIgnoreCase(correo.trim())
                    && admin.getPassword().equals(password)) {
                return admin;
            }
        }

        return null;
    }
}