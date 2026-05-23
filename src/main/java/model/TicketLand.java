package model;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {

        return nombre +
                " - NIT: " +
                nit;
    }
}