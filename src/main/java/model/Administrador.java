package model;

<<<<<<< Updated upstream
=======
import java.time.LocalDate;
import java.util.List;

>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public String gestionarEventos() {
        return "Eventos gestionados correctamente";
=======
    // =========================================================
    // RF-013: GESTIÓN DE EVENTOS
    // =========================================================

    public void crearEvento(Evento evento, TicketLand sistema) {
        sistema.agregarEvento(evento);
>>>>>>> Stashed changes
    }

    public String gestionarUsuarios() {
        return "Usuarios gestionados correctamente";
    }

<<<<<<< Updated upstream
    public String gestionarRecintos() {
        return "Recintos gestionados correctamente";
=======
    public void publicarEvento(Evento evento) {
        evento.activar();
    }

    public void pausarEvento(Evento evento) {
        evento.pausar();
    }

    public void cancelarEvento(Evento evento) {
        evento.cancelar();
    }

    // =========================================================
    // RF-012: GESTIÓN DE USUARIOS
    // =========================================================

    public void crearUsuario(Usuario usuario, TicketLand sistema) {
        sistema.agregarUsuario(usuario);
    }

    public boolean eliminarUsuario(Usuario usuario, TicketLand sistema) {
        return sistema.eliminarUsuario(usuario);
    }

    public void actualizarUsuario(Usuario usuario, String nombre, String correo, String telefono) {

        if (nombre != null && !nombre.isBlank()) {
            usuario.setNombre(nombre);
        }
        if (correo != null && !correo.isBlank()) {
            usuario.setCorreo(correo);
        }
        if (telefono != null && !telefono.isBlank()) {
            usuario.setTelefono(telefono);
        }
    }

    // =========================================================
    // RF-014: GESTIÓN DE RECINTOS Y ZONAS
    // =========================================================

    public void agregarRecintoAEvento(Recinto recinto, Evento evento) {
        evento.setRecinto(recinto);
    }

    public void agregarZonaARecinto(Zona zona, Recinto recinto) {
        recinto.agregarZona(zona);
    }

    // =========================================================
    // RF-015: GESTIÓN DE ASIENTOS
    // =========================================================

    public boolean bloquearAsiento(Asiento asiento) {
        return asiento.bloquear();
    }

    public boolean liberarAsiento(Asiento asiento) {
        return asiento.liberar();
    }

    // =========================================================
    // RF-016: GESTIÓN DE COMPRAS
    // =========================================================

    public boolean cancelarCompra(Compra compra) {
        return compra.cancelar();
    }

    public boolean confirmarCompra(Compra compra) {
        return compra.confirmar();
    }

    // =========================================================
    // RF-017: REGISTRO DE INCIDENCIAS
    // =========================================================

    public void registrarIncidencia(Incidencia.Tipo tipo, String descripcion, String entidadAfectada, TicketLand sistema) {
        int idIncidencia = sistema.getIncidencias().size() + 1;
        Incidencia incidencia = new Incidencia(idIncidencia, tipo, descripcion, LocalDate.now(), entidadAfectada);
        sistema.agregarIncidencia(incidencia);
    }

    // =========================================================
    // RF-010: CONSULTAR TODAS LAS COMPRAS
    // =========================================================

    public List<Compra> consultarTodasLasCompras(TicketLand sistema) {
        return sistema.getCompras();
>>>>>>> Stashed changes
    }
    @Override
    public String toString() {
        return getNombre() + " - ADMIN";
    }
}