package controller;

import factory.ConciertoFactory;
import factory.ConferenciaFactory;
import factory.EventoFactory;
import factory.TeatroFactory;
import model.Asiento;
import model.Compra;
import model.Evento;
import model.Incidencia;
import model.Recinto;
import model.TicketLand;
import model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class AdminController {

    private final TicketLand sistema = TicketLand.getInstance();

    public List<Evento> obtenerEventos() {
        return sistema.getEventos();
    }

    public List<Usuario> obtenerUsuarios() {
        return sistema.getUsuarios();
    }

    public List<Compra> obtenerCompras() {
        return sistema.getCompras();
    }

    public List<Incidencia> obtenerIncidencias() {
        return sistema.getIncidencias();
    }

    public Evento crearEvento(
            String nombre,
            String categoria,
            String ciudad,
            LocalDate fecha,
            Recinto recinto,
            String descripcion,
            String politicas
    ) {
        validarTexto(nombre, "El nombre del evento es obligatorio.");
        validarTexto(categoria, "La categoría es obligatoria.");
        validarTexto(ciudad, "La ciudad es obligatoria.");

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }

        EventoFactory factory = switch (categoria) {
            case "Concierto" -> new ConciertoFactory(nombre, ciudad, fecha, nombre, "General", recinto);
            case "Teatro" -> new TeatroFactory(nombre, ciudad, fecha, nombre, "Por definir", recinto);
            default -> new ConferenciaFactory(nombre, ciudad, fecha, "Por definir", nombre, recinto);
        };

        Evento evento = factory.crearEvento();

        if (descripcion != null && !descripcion.isBlank()) {
            evento.setDescripcion(descripcion.trim());
        }

        if (politicas != null && !politicas.isBlank()) {
            evento.setPoliticas(politicas.trim());
        }

        sistema.agregarEvento(evento);
        return evento;
    }

    public Usuario crearUsuario(String nombre, String correo, String telefono) {
        validarTexto(nombre, "El nombre es obligatorio.");
        validarTexto(correo, "El correo es obligatorio.");
        validarTexto(telefono, "El teléfono es obligatorio.");

        Usuario usuario = new Usuario(sistema.getUsuarios().size() + 1, nombre, correo, telefono);
        sistema.agregarUsuario(usuario);

        return usuario;
    }

    public boolean eliminarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Selecciona un usuario.");
        }

        return sistema.eliminarUsuario(usuario);
    }

    public boolean activarEvento(Evento evento) {
        validarEvento(evento);
        evento.activar();
        return true;
    }

    public boolean pausarEvento(Evento evento) {
        validarEvento(evento);
        evento.pausar();
        return true;
    }

    public boolean cancelarEvento(Evento evento) {
        validarEvento(evento);
        evento.cancelar();
        return true;
    }

    public boolean finalizarEvento(Evento evento) {
        validarEvento(evento);
        evento.finalizar();
        return true;
    }

    public boolean confirmarCompra(Compra compra) {
        validarCompra(compra);
        return compra.confirmar();
    }

    public boolean cancelarCompra(Compra compra) {
        validarCompra(compra);
        return compra.cancelar();
    }

    public boolean reembolsarCompra(Compra compra) {
        validarCompra(compra);
        return compra.reembolsar();
    }

    public boolean marcarIncidenciaCompra(Compra compra) {
        validarCompra(compra);
        return compra.marcarComoIncidencia();
    }

    public boolean bloquearAsiento(Asiento asiento) {
        if (asiento == null) {
            throw new IllegalArgumentException("Selecciona un asiento.");
        }

        return asiento.bloquear();
    }

    public boolean liberarAsiento(Asiento asiento) {
        if (asiento == null) {
            throw new IllegalArgumentException("Selecciona un asiento.");
        }

        return asiento.liberar();
    }

    public void registrarIncidencia(Incidencia.Tipo tipo, String descripcion, String entidadAfectada) {
        if (tipo == null) {
            throw new IllegalArgumentException("Selecciona un tipo de incidencia.");
        }

        validarTexto(descripcion, "La descripción es obligatoria.");
        validarTexto(entidadAfectada, "La entidad afectada es obligatoria.");

        sistema.getAdministradores().get(0)
                .registrarIncidencia(tipo, descripcion, entidadAfectada, sistema);
    }

    public List<Incidencia> buscarIncidencias(Incidencia.Tipo tipo, LocalDate desde, LocalDate hasta) {
        return sistema.buscarIncidencias(tipo, desde, hasta);
    }

    private void validarEvento(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Selecciona un evento.");
        }
    }

    private void validarCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("Selecciona una compra.");
        }
    }

    private void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}