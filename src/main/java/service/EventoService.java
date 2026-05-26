package service;

import model.Evento;
import model.EstadoEvento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoService {

    private List<Evento> eventos;

    public EventoService(List<Evento> eventos) {
        this.eventos = eventos;
    }

    // RF-013
    public void agregarEvento(Evento evento) { eventos.add(evento); }

    public boolean eliminarEvento(String idEvento) {
        return eventos.removeIf(e ->
                String.valueOf(e.hashCode()).equals(idEvento));
    }

    public List<Evento> listarTodos() { return eventos; }

    // RF-003: filtros por fecha, ciudad, categoría
    public List<Evento> filtrar(LocalDate desde, LocalDate hasta,
                                String ciudad, String categoria) {
        List<Evento> resultado = new ArrayList<>();
        for (Evento e : eventos) {
            boolean ok = true;
            if (desde != null && e.getFecha().isBefore(desde)) ok = false;
            if (hasta != null && e.getFecha().isAfter(hasta)) ok = false;
            if (ciudad != null && !e.getCiudad().equalsIgnoreCase(ciudad)) ok = false;
            if (categoria != null && !e.getCategoria().equalsIgnoreCase(categoria)) ok = false;
            if (ok) resultado.add(e);
        }
        return resultado;
    }

    // RF-024
    public void publicarEvento(Evento e) { e.activar(); }
    public void pausarEvento(Evento e) { e.pausar(); }
    public void cancelarEvento(Evento e) { e.cancelar(); }
}