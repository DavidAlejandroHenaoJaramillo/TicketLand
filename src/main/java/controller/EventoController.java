package controller;

import model.Asiento;
import model.Evento;
import model.TicketLand;
import model.Zona;

import java.util.ArrayList;
import java.util.List;

public class EventoController {

    private final TicketLand sistema = TicketLand.getInstance();

    public List<Evento> obtenerEventosActivos() {
        return sistema.getEventosActivos();
    }

    public List<Evento> buscarEventos(String ciudad, String categoria, Double precioMaximo) {
        return sistema.buscarEventos(
                ciudad == null || ciudad.isBlank() ? null : ciudad.trim(),
                categoria == null || categoria.isBlank() ? null : categoria.trim(),
                null,
                precioMaximo
        );
    }

    public List<Zona> obtenerZonas(Evento evento) {
        if (evento == null || evento.getRecinto() == null) {
            return List.of();
        }

        return evento.getRecinto().getZonas();
    }

    public List<Asiento> obtenerAsientos(Evento evento) {
        List<Asiento> asientos = new ArrayList<>();

        if (evento == null || evento.getRecinto() == null) {
            return asientos;
        }

        for (Zona zona : evento.getRecinto().getZonas()) {
            asientos.addAll(zona.getAsientos());
        }

        return asientos;
    }

    public List<Asiento> obtenerAsientosDisponibles(Zona zona) {
        if (zona == null) {
            return List.of();
        }

        return zona.getAsientosDisponibles();
    }
}