package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import state.CompraCreada;
import strategy.PagoStrategy;

public class TicketLand {

    private static TicketLand instancia;

    private String nombre;
    private String nit;
    private List<Usuario> usuarios;
    private List<Administrador> administradores;
    private List<Evento> eventos;
    private List<Compra> compras;
    private List<Incidencia> incidencias;

    private TicketLand(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
        usuarios = new ArrayList<>();
        administradores = new ArrayList<>();
        eventos = new ArrayList<>();
        compras = new ArrayList<>();
        incidencias = new ArrayList<>();
    }

    public static TicketLand getInstancia() {
        return instancia;
    }

    public static void setInstancia(TicketLand instancia) {
        TicketLand.instancia = instancia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public static TicketLand getInstance() {

        if (instancia == null) {
            instancia = new TicketLand(
                    "TicketLand",
                    "000-000"
            );
        }

        return instancia;
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void agregarAdministrador(Administrador administrador) {
        administradores.add(administrador);
    }

    public void agregarEvento(Evento evento) {
        eventos.add(evento);
    }

    public void agregarCompra(Compra compra) {
        compras.add(compra);
    }

    public void agregarIncidencia(Incidencia incidencia) {
        incidencias.add(incidencia);
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // RF-003: buscar eventos con filtros opcionales
    public List<Evento> buscarEventos(String ciudad, String categoria, LocalDate fecha) {
        List<Evento> resultado = new ArrayList<>();
        for (Evento e : eventos) {
            boolean coincide = true;
            if (ciudad != null && !e.getCiudad().equalsIgnoreCase(ciudad)) {
                coincide = false;
            }
            if (categoria != null && !e.getCategoria().equalsIgnoreCase(categoria)) {
                coincide = false;
            }
            if (fecha != null && !e.getFecha().equals(fecha)) {
                coincide = false;
            }
            if (coincide) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    // RF-003: buscar eventos solo activos
    public List<Evento> getEventosActivos() {
        List<Evento> activos = new ArrayList<>();
        for (Evento e : eventos) {
            if (e.getEstado() == EstadoEvento.ACTIVO) {
                activos.add(e);
            }
        }
        return activos;
    }

    // RF-022: buscar usuario por id
    public Usuario buscarUsuarioPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    // RF-034: crear una compra nueva para un usuario
    public Compra crearCompra(Usuario usuario, Evento evento, PagoStrategy metodoPago) {
        Compra compra = new Compra(
                0,
                LocalDate.now(),
                new CompraCreada(),
                usuario,
                evento,
                metodoPago
        );
        compras.add(compra);
        usuario.realizarCompra(compra);
        return compra;
    }

    // RF-012: buscar usuario por correo
    public Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null;
    }

    // RF-013: eliminar evento
    public boolean eliminarEvento(Evento evento) {
        return eventos.remove(evento);
    }

    // RF-012: eliminar usuario
    public boolean eliminarUsuario(Usuario usuario) {
        return usuarios.remove(usuario);
    }
    // RF-042: consultar incidencias por rango de fechas y tipo
    public List<Incidencia> buscarIncidencias(Incidencia.Tipo tipo, LocalDate desde, LocalDate hasta) {
        List<Incidencia> resultado = new ArrayList<>();
        for (Incidencia i : incidencias) {
            boolean coincide = true;
            if (tipo != null && i.getTipo() != tipo) coincide = false;
            if (desde != null && i.getFecha().isBefore(desde)) coincide = false;
            if (hasta != null && i.getFecha().isAfter(hasta)) coincide = false;
            if (coincide) resultado.add(i);
        }
        return resultado;
    }

    @Override
    public String toString() {

        return nombre +
                " - NIT: " +
                nit;
    }
}