package controller;

import model.Compra;
import model.TicketLand;
import model.Usuario;
import strategy.PagoEfectivo;
import strategy.PagoPSE;
import strategy.PagoStrategy;
import strategy.PagoTarjeta;

import java.util.List;

public class UsuarioController {

    private final TicketLand sistema = TicketLand.getInstance();

    public Usuario registrarUsuario(String nombre, String correo, String telefono, String password) {
        validarTexto(nombre, "El nombre es obligatorio.");
        validarTexto(correo, "El correo es obligatorio.");
        validarTexto(telefono, "El teléfono es obligatorio.");
        validarTexto(password, "La contraseña es obligatoria.");

        if (password.length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres.");
        }

        if (sistema.buscarUsuarioPorCorreo(correo) != null) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese correo.");
        }

        int nuevoId = sistema.getUsuarios().size() + 1;
        Usuario usuario = new Usuario(nuevoId, nombre, correo, telefono, password);
        sistema.agregarUsuario(usuario);

        return usuario;
    }


    public void actualizarPerfil(Usuario usuario, String nombre, String correo, String telefono) {
        if (usuario == null) {
            throw new IllegalArgumentException("No hay usuario activo.");
        }

        validarTexto(nombre, "El nombre es obligatorio.");
        validarTexto(correo, "El correo es obligatorio.");
        validarTexto(telefono, "El teléfono es obligatorio.");

        usuario.actualizarPerfil(nombre, correo, telefono);
    }

    public List<Compra> obtenerHistorial(Usuario usuario) {
        return usuario.getHistorialCompras();
    }

    public PagoStrategy crearMetodoPago(String tipo, String dato, String nombreUsuario) {
        return switch (tipo) {
            case "Tarjeta" -> new PagoTarjeta(
                    dato == null || dato.isBlank() ? "0000-0000-0000-0000" : dato,
                    nombreUsuario
            );
            case "PSE" -> new PagoPSE(
                    dato == null || dato.isBlank() ? "Bancolombia" : dato
            );
            default -> new PagoEfectivo();
        };
    }

    public void agregarMetodoPago(Usuario usuario, PagoStrategy metodoPago) {
        if (usuario == null) {
            throw new IllegalArgumentException("No hay usuario activo.");
        }

        usuario.agregarMetodoPago(metodoPago);
    }

    private void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}