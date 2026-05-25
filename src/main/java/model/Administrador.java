package model;

import java.time.LocalDate;
import java.util.List;

public class Administrador extends Persona {

    private String permisosAdministrador;

    public Administrador(int id, String nombre, String correo, String telefono, String permisosAdministrador) {
        super(id, nombre, correo, telefono);
        this.permisosAdministrador = permisosAdministrador;
    }

    public String getPermisosAdministrador() {
        return permisosAdministrador;
    }

    public void setPermisosAdministrador(String permisosAdministrador) {
        this.permisosAdministrador = permisosAdministrador;
    }

    // RF-013: gestionar eventos
    public void crearEvento(Evento evento, TicketLand sistema) {
        sistema.agregarEvento(evento);
    }

    public boolean eliminarEvento(Evento evento, TicketLand sistema) {
        return sistema.eliminarEvento(evento);
    }

    public void publicarEvento(Evento evento) {
        evento.activar();
    }

    public void pausarEvento(Evento evento) {
        evento.pausar();
    }

    public void cancelarEvento(Evento evento) {
        evento.cancelar();
    }

    // RF-012: gestionar usuarios
    public void crearUsuario(Usuario usuario, TicketLand sistema) {
        sistema.agregarUsuario(usuario);
    }

    public boolean eliminarUsuario(Usuario usuario, TicketLand sistema) {
        return sistema.eliminarUsuario(usuario);
    }

    public void actualizarUsuario(Usuario usuario, String nombre, String correo, String telefono) {
        if (nombre != null) usuario.setNombre(nombre);
        if (correo != null) usuario.setCorreo(correo);
        if (telefono != null) usuario.setTelefono(telefono);
    }

    // RF-014: gestionar recintos y zonas
    public void agregarRecintoAEvento(Recinto recinto, Evento evento) {
        evento.setRecinto(recinto);
    }

    public void agregarZonaARecinto(Zona zona, Recinto recinto) {
        recinto.agregarZona(zona);
    }

    // RF-015: gestionar asientos
    public boolean bloquearAsiento(Asiento asiento) {
        return asiento.bloquear();
    }

    public boolean liberarAsiento(Asiento asiento) {
        return asiento.liberar();
    }

    // RF-016: gestionar compras
    public boolean cancelarCompra(Compra compra) {
        return compra.cancelar();
    }

    public boolean confirmarCompra(Compra compra) {
        return compra.confirmar();
    }

    // RF-017: registrar incidencia
    public void registrarIncidencia(Incidencia.Tipo tipo, String descripcion,
                                    String entidadAfectada, TicketLand sistema) {
        int idIncidencia = sistema.getIncidencias().size() + 1;
        Incidencia incidencia = new Incidencia(idIncidencia, tipo, descripcion,
                LocalDate.now(), entidadAfectada);
        sistema.agregarIncidencia(incidencia);
    }

    // RF-010: consultar historial de compras del sistema
    public List<Compra> consultarTodasLasCompras(TicketLand sistema) {
        return sistema.getCompras();
    }

    @Override
    public String toString() {
        return nombre + " - ADMIN";
    }
}