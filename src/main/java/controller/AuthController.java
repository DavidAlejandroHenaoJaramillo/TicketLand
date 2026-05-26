package controller;

import model.TicketLand;
import model.Usuario;

public class AuthController {

    private final TicketLand sistema = TicketLand.getInstance();

    public Usuario loginUsuario(String correo) {
        if (correo == null || correo.isBlank()) {
            return null;
        }
        return sistema.buscarUsuarioPorCorreo(correo.trim());
    }

    public boolean loginAdmin() {
        return true;
    }
}