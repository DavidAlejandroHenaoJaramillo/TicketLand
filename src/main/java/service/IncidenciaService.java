package service;

import model.Incidencia;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// RF-041 / RF-042
public class IncidenciaService {

    private final List<Incidencia> incidencias;

    public IncidenciaService(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public Incidencia registrar(Incidencia.Tipo tipo, String descripcion,
                                String idCompra, String idEvento) {
        int id = incidencias.size() + 1;
        Incidencia inc = new Incidencia(id, tipo, descripcion,
                LocalDate.now(), idCompra + "/" + idEvento);
        incidencias.add(inc);
        return inc;
    }

    // RF-042: filtrar por rango de fechas y tipo
    public List<Incidencia> filtrar(LocalDate desde, LocalDate hasta,
                                    Incidencia.Tipo tipo) {
        List<Incidencia> resultado = new ArrayList<>();
        for (Incidencia i : incidencias) {
            boolean ok = true;
            if (desde != null && i.getFecha().isBefore(desde)) ok = false;
            if (hasta != null && i.getFecha().isAfter(hasta)) ok = false;
            if (tipo != null && i.getTipo() != tipo) ok = false;
            if (ok) resultado.add(i);
        }
        return resultado;
    }

    public List<Incidencia> listarTodas() { return incidencias; }
}