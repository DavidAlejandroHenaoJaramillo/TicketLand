package singleton;

import model.Compra;
import model.*;
import service.*;
import facade.CompraFacade;
import java.time.LocalDate;
import java.util.List;

/**
 * RF-049 (Singleton): punto de entrada único al sistema.
 * Garantiza una sola instancia y coordina todos los servicios.
 */
public class SistemaEventos {

    private static SistemaEventos instance;

    private UsuarioService usuarioService;
    private EventoService eventoService;
    private EntradaService entradaService;
    private CompraService compraService;
    private PagoService pagoService;
    private ReporteService reporteService;
    private AuthService authService;
    private IncidenciaService incidenciaService;
    private CompraFacade compraFacade;

    private SistemaEventos() {}

    public static SistemaEventos getInstance() {
        if (instance == null) {
            instance = new SistemaEventos();
        }
        return instance;
    }

    public void initialize(UsuarioService usuarioService,
                           EventoService eventoService,
                           CompraService compraService,
                           PagoService pagoService,
                           ReporteService reporteService,
                           AuthService authService,
                           IncidenciaService incidenciaService,
                           EntradaService entradaService,
                           CompraFacade compraFacade) {
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
        this.compraService = compraService;
        this.pagoService = pagoService;
        this.reporteService = reporteService;
        this.authService = authService;
        this.incidenciaService = incidenciaService;
        this.entradaService = entradaService;
        this.compraFacade = compraFacade;
    }

    public Usuario login(String correo, String clave) {
        return authService.login(correo, clave);
    }

    public Usuario registrarUsuario(String nombre, String correo,
                                    String telefono, String clave, Rol rol) {
        return authService.registrar(nombre, correo, telefono, clave, rol);
    }

    public List<Evento> listarEventos() {
        return eventoService.listarTodos();
    }

    public Compra realizarCompra(Usuario usuario, Evento evento,
                                 decorator.EntradaBase entrada,
                                 strategy.PagoStrategy metodoPago) {
        return compraFacade.realizarCompra(usuario, evento, entrada, metodoPago);
    }

    public byte[] generarReporte(TipoReporte tipo, FormatoReporte formato,
                                 LocalDate inicio, LocalDate fin) {
        return reporteService.generarReporte(tipo, formato, inicio, fin);
    }

    // Getters para los servicios (para uso desde controllers)
    public UsuarioService getUsuarioService() { return usuarioService; }
    public EventoService getEventoService() { return eventoService; }
    public CompraService getCompraService() { return compraService; }
    public IncidenciaService getIncidenciaService() { return incidenciaService; }
    public EntradaService getEntradaService() { return entradaService; }
    public ReporteService getReporteService() { return reporteService; }
}