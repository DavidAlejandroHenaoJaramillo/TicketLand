package proxy;

import builder.Compra;
import model.Entrada;
import model.EstadoEntrada;
import model.Evento;
import model.TicketLand;
import model.Usuario;
import model.Zona;
import model.Asiento;
import decorator.EntradaBase;
import strategy.PagoStrategy;

/**
 * RF-050: Patrón estructural Proxy (Proxy de Protección).
 *
 * Problema (RF-001/RF-012): el sistema no valida si el usuario tiene sesión activa
 * antes de permitir operaciones sensibles como comprar o cancelar.
 *
 * Propósito: interponer un objeto Proxy entre el cliente y el sistema real (TicketLand)
 * para controlar el acceso y añadir validaciones sin modificar la clase original.
 *
 * Solución: SistemaProxy verifica que el usuario esté autenticado antes de
 * delegar la operación al TicketLand real. También puede registrar logs.
 */
public class SistemaProxy {

    private TicketLand sistemaReal;
    private Usuario usuarioActivo;

    public SistemaProxy(TicketLand sistemaReal) {
        this.sistemaReal = sistemaReal;
        this.usuarioActivo = null;
    }

    /** Autentica al usuario por correo. Retorna true si existe en el sistema. */
    public boolean autenticar(String correo) {
        Usuario u = sistemaReal.buscarUsuarioPorCorreo(correo);
        if (u != null) {
            usuarioActivo = u;
            System.out.println("[Proxy] Usuario autenticado: " + u.getNombre());
            return true;
        }
        System.out.println("[Proxy] Autenticación fallida para: " + correo);
        return false;
    }

    public void cerrarSesion() {
        System.out.println("[Proxy] Sesión cerrada para: "
                + (usuarioActivo != null ? usuarioActivo.getNombre() : "ninguno"));
        usuarioActivo = null;
    }

    /**
     * Proxy de compra: solo permite comprar si hay usuario autenticado.
     * RF-034: crear compra con control de acceso.
     */
    public Compra comprarEntrada(Evento evento, Zona zona, PagoStrategy metodoPago) {
        if (usuarioActivo == null) {
            System.out.println("[Proxy] Acceso denegado: debe iniciar sesión primero.");
            return null;
        }
        if (!zona.hayDisponibilidad()) {
            System.out.println("[Proxy] Sin disponibilidad en zona: " + zona.getNombre());
            return null;
        }
        Asiento asiento = zona.getAsientosDisponibles().get(0);
        asiento.reservar();
        EntradaBase entrada = new Entrada(
                sistemaReal.getCompras().size() + 1,
                zona.getPrecioBase(),
                EstadoEntrada.ACTIVA,
                zona,
                asiento
        );
        Compra compra = sistemaReal.crearCompra(usuarioActivo, evento, metodoPago);
        compra.agregarEntrada(entrada);
        compra.pagar();
        System.out.println("[Proxy] Compra realizada por: " + usuarioActivo.getNombre());
        return compra;
    }

    public Usuario getUsuarioActivo() { return usuarioActivo; }
    public TicketLand getSistemaReal() { return sistemaReal; }
}