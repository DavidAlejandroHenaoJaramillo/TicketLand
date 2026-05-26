package model;

import decorator.EntradaBase;
import decorator.EntradaVIP;
import decorator.MerchandisingDecorator;
import decorator.ParqueaderoDecorator;
import decorator.SeguroDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import state.*;
import strategy.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el proyecto TicketLand
 * Cubre: Asiento, Zona, Compra, Entrada, Decorators, Strategies y Usuario
 */
class TicketLandTest {

    // ─── Objetos de apoyo ────────────────────────────────────────────────────
    private Tarifa tarifa;
    private Zona zona;
    private Asiento asiento;

    @BeforeEach
    void setUp() {
        tarifa  = new Tarifa(1, "General", 80_000, "Zona general", TipoZona.GENERAL);
        zona    = new Zona(1, "Zona A", 100, 80_000, TipoZona.GENERAL, tarifa);
        asiento = new Asiento(1, "A", 1, new Disponible());
    }

    // =========================================================================
    // ASIENTO
    // =========================================================================

    /** RF-032: asiento disponible puede reservarse */
    @Test
    void asientoDisponible_puedeSer_reservado() {
        assertTrue(asiento.reservar());
        assertInstanceOf(Reservado.class, asiento.getEstado());
    }

    /** RF-032: asiento ya reservado NO puede reservarse de nuevo */
    @Test
    void asientoReservado_noPuedeReservarse_nuevamente() {
        asiento.reservar();
        assertFalse(asiento.reservar());
    }

    /** RF-032: asiento reservado puede liberarse */
    @Test
    void asientoReservado_puedeLiberarse() {
        asiento.reservar();
        assertTrue(asiento.liberar());
        assertInstanceOf(Disponible.class, asiento.getEstado());
    }

    /** RF-015: asiento disponible puede bloquearse */
    @Test
    void asientoDisponible_puedeBloquearse() {
        assertTrue(asiento.bloquear());
        assertInstanceOf(Bloqueado.class, asiento.getEstado());
    }

    /** RF-015: asiento ya bloqueado NO puede bloquearse de nuevo */
    @Test
    void asientoBloqueado_noPuedeBloquearse_nuevamente() {
        asiento.bloquear();
        assertFalse(asiento.bloquear());
    }

    /** Estado DISPONIBLE entrega texto correcto */
    @Test
    void estadoDisponible_retornaTexto_correcto() {
        assertEquals("Asiento disponible", asiento.mostrarEstadoAsiento());
    }

    // =========================================================================
    // ZONA
    // =========================================================================

    /** Zona nueva sin asientos no tiene ocupación */
    @Test
    void zonaVacia_tieneOcupacion_cero() {
        assertEquals(0, zona.calcularOcupacion());
    }

    /** RF-029: zona sin asientos no tiene disponibilidad */
    @Test
    void zonaVacia_noTiene_disponibilidad() {
        assertFalse(zona.hayDisponibilidad());
    }

    /** RF-025: asientos disponibles se listan correctamente */
    @Test
    void zona_conAsientoDisponible_aparece_enListaDisponibles() {
        zona.agregarAsiento(asiento);
        List<Asiento> disponibles = zona.getAsientosDisponibles();
        assertEquals(1, disponibles.size());
        assertEquals(asiento, disponibles.get(0));
    }

    /** RF-030: asiento no-disponible se cuenta como ocupado */
    @Test
    void zona_conAsientoReservado_cuentaOcupacion() {
        asiento.reservar();
        zona.agregarAsiento(asiento);
        assertEquals(1, zona.calcularOcupacion());
    }

    /** RF-029: zona con asiento disponible reporta disponibilidad */
    @Test
    void zona_conAsientoDisponible_reportaDisponibilidad() {
        zona.agregarAsiento(asiento);
        assertTrue(zona.hayDisponibilidad());
    }

    // =========================================================================
    // ENTRADA
    // =========================================================================

    /** Entrada activa puede anularse */
    @Test
    void entrada_activa_puedeAnularse() {
        Entrada entrada = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        assertTrue(entrada.anular());
        assertEquals(EstadoEntrada.ANULADA, entrada.getEstadoEntrada());
    }

    /** Entrada ya anulada NO puede anularse de nuevo */
    @Test
    void entrada_yaAnulada_noPuedeAnularse_nuevamente() {
        Entrada entrada = new Entrada(1, 80_000, EstadoEntrada.ANULADA, zona, asiento);
        assertFalse(entrada.anular());
    }

    /** Al anular una entrada el asiento queda disponible */
    @Test
    void entrada_alAnularse_libera_asiento() {
        asiento.reservar(); // asiento en estado RESERVADO
        Entrada entrada = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        entrada.anular();
        assertInstanceOf(Disponible.class, asiento.getEstado());
    }

    /** getCosto() retorna el precio correcto */
    @Test
    void entrada_getCosto_retornaPrecioFinal() {
        Entrada entrada = new Entrada(2, 120_000, EstadoEntrada.ACTIVA, zona, asiento);
        assertEquals(120_000, entrada.getCosto());
    }

    // =========================================================================
    // DECORATOR (servicios adicionales sobre entradas)
    // =========================================================================

    /** VIP agrega $100.000 al costo base */
    @Test
    void decorator_VIP_agrega_costoExtra() {
        Entrada base = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        EntradaBase vip = new EntradaVIP(base);
        assertEquals(180_000, vip.getCosto());
    }

    /** Merchandising agrega $50.000 */
    @Test
    void decorator_Merchandising_agrega_costoExtra() {
        Entrada base = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        EntradaBase merch = new MerchandisingDecorator(base);
        assertEquals(130_000, merch.getCosto());
    }

    /** Parqueadero agrega $30.000 */
    @Test
    void decorator_Parqueadero_agrega_costoExtra() {
        Entrada base = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        EntradaBase park = new ParqueaderoDecorator(base);
        assertEquals(110_000, park.getCosto());
    }

    /** Seguro agrega $20.000 */
    @Test
    void decorator_Seguro_agrega_costoExtra() {
        Entrada base = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        EntradaBase seguro = new SeguroDecorator(base);
        assertEquals(100_000, seguro.getCosto());
    }

    /** Decorators se encadenan correctamente: VIP + Parqueadero */
    @Test
    void decorator_encadenado_VIPMasParqueadero_acumulaCostos() {
        Entrada base   = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        EntradaBase vip  = new EntradaVIP(base);
        EntradaBase park = new ParqueaderoDecorator(vip);
        // 80k + 100k (VIP) + 30k (parqueadero) = 210k
        assertEquals(210_000, park.getCosto());
    }

    /** Descripción incluye los servicios añadidos */
    @Test
    void decorator_descripcion_incluye_servicios() {
        Entrada base    = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        EntradaBase seguro = new SeguroDecorator(base);
        assertTrue(seguro.getDescripcion().contains("Seguro"));
    }

    // =========================================================================
    // STRATEGY (métodos de pago)
    // =========================================================================

    /** Pago con tarjeta acepta montos positivos */
    @Test
    void pagoTarjeta_montoPositivo_retornaTrue() {
        PagoStrategy pago = new PagoTarjeta("1234-5678", "Juan Pérez");
        assertTrue(pago.procesarPago(50_000));
    }

    /** Pago con tarjeta rechaza monto cero */
    @Test
    void pagoTarjeta_montoCero_retornaFalse() {
        PagoStrategy pago = new PagoTarjeta("1234-5678", "Juan Pérez");
        assertFalse(pago.procesarPago(0));
    }

    /** Pago en efectivo acepta montos positivos */
    @Test
    void pagoEfectivo_montoPositivo_retornaTrue() {
        PagoStrategy pago = new PagoEfectivo();
        assertTrue(pago.procesarPago(80_000));
    }

    /** Pago PSE acepta montos positivos */
    @Test
    void pagoPSE_montoPositivo_retornaTrue() {
        PagoStrategy pago = new PagoPSE("Bancolombia");
        assertTrue(pago.procesarPago(100_000));
    }

    /** Pago Nequi acepta montos positivos */
    @Test
    void pagoNequi_montoPositivo_retornaTrue() {
        PagoStrategy pago = new PagoNequi("3001234567");
        assertTrue(pago.procesarPago(60_000));
    }

    /** Pago PayPal acepta montos positivos */
    @Test
    void pagoPaypal_montoPositivo_retornaTrue() {
        PagoStrategy pago = new PagoPaypal("usuario@paypal.com");
        assertTrue(pago.procesarPago(75_000));
    }

    // =========================================================================
    // COMPRA
    // =========================================================================

    /** RF-034: compra nueva inicia en estado CREADA */
    @Test
    void compra_nueva_iniciaEnEstado_creada() {
        Compra compra = crearCompraBase();
        assertInstanceOf(CompraCreada.class, compra.getEstadoCompra());
    }

    /** RF-007: pagar compra con entradas cambia estado a PAGADA */
    @Test
    void compra_conEntradas_pagar_cambiaEstado_aPagada() {
        Compra compra = crearCompraBase();
        compra.agregarEntrada(new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento));
        assertTrue(compra.pagar());
        assertInstanceOf(CompraPagada.class, compra.getEstadoCompra());
    }

    /** RF-007: pagar compra sin entradas retorna false */
    @Test
    void compra_sinEntradas_pagar_retornaFalse() {
        Compra compra = crearCompraBase();
        assertFalse(compra.pagar());
    }

    /** RF-008: confirmar compra pagada cambia estado a CONFIRMADA */
    @Test
    void compra_pagada_confirmar_cambiaEstado_aConfirmada() {
        Compra compra = crearCompraBase();
        compra.agregarEntrada(new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento));
        compra.pagar();
        assertTrue(compra.confirmar());
        assertInstanceOf(CompraConfirmada.class, compra.getEstadoCompra());
    }

    /** Confirmar compra no pagada retorna false */
    @Test
    void compra_noPagada_confirmar_retornaFalse() {
        Compra compra = crearCompraBase();
        assertFalse(compra.confirmar());
    }

    /** RF-036: cancelar compra creada cambia estado a CANCELADA */
    @Test
    void compra_creada_cancelar_cambiaEstado_aCancelada() {
        Compra compra = crearCompraBase();
        assertTrue(compra.cancelar());
        assertInstanceOf(CompraCancelada.class, compra.getEstadoCompra());
    }

    /** RF-036: cancelar compra ya cancelada retorna false */
    @Test
    void compra_yaCancelada_cancelar_retornaFalse() {
        Compra compra = crearCompraBase();
        compra.cancelar();
        assertFalse(compra.cancelar());
    }

    /** RF-016: reembolsar compra pagada cambia estado a REEMBOLSADA */
    @Test
    void compra_pagada_reembolsar_cambiaEstado_aReembolsada() {
        Compra compra = crearCompraBase();
        compra.agregarEntrada(new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento));
        compra.pagar();
        assertTrue(compra.reembolsar());
        assertInstanceOf(CompraReembolsada.class, compra.getEstadoCompra());
    }

    /** RF-016: no se puede reembolsar compra creada (sin pagar) */
    @Test
    void compra_creada_reembolsar_retornaFalse() {
        Compra compra = crearCompraBase();
        assertFalse(compra.reembolsar());
    }

    /** calcularTotal suma correctamente el costo de las entradas */
    @Test
    void compra_calcularTotal_sumaCostoEntradas() {
        Compra compra = crearCompraBase();
        compra.agregarEntrada(new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento));
        Asiento asiento2 = new Asiento(2, "A", 2, new Disponible());
        compra.agregarEntrada(new Entrada(2, 90_000, EstadoEntrada.ACTIVA, zona, asiento2));
        assertEquals(170_000, compra.calcularTotal());
    }

    /** RF-035: eliminar entrada en compra CREADA libera el asiento */
    @Test
    void compra_creada_eliminarEntrada_libera_asiento() {
        asiento.reservar();
        Entrada entrada = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        Compra compra = crearCompraBase();
        compra.agregarEntrada(entrada);
        assertTrue(compra.eliminarEntrada(entrada));
        assertInstanceOf(Disponible.class, asiento.getEstado());
    }

    /** RF-035: no se puede eliminar entrada en compra ya pagada */
    @Test
    void compra_pagada_eliminarEntrada_retornaFalse() {
        Entrada entrada = new Entrada(1, 80_000, EstadoEntrada.ACTIVA, zona, asiento);
        Compra compra = crearCompraBase();
        compra.agregarEntrada(entrada);
        compra.pagar();
        assertFalse(compra.eliminarEntrada(entrada));
    }

    /** RF-008: marcar incidencia en compra activa */
    @Test
    void compra_activa_marcarIncidencia_cambiaEstado() {
        Compra compra = crearCompraBase();
        assertTrue(compra.marcarComoIncidencia());
        assertInstanceOf(CompraIncidencia.class, compra.getEstadoCompra());
    }

    /** No se puede marcar incidencia en compra cancelada */
    @Test
    void compra_cancelada_marcarIncidencia_retornaFalse() {
        Compra compra = crearCompraBase();
        compra.cancelar();
        assertFalse(compra.marcarComoIncidencia());
    }

    // =========================================================================
    // USUARIO
    // =========================================================================

    /** Usuario registra compra en historial */
    @Test
    void usuario_realizarCompra_agrega_alHistorial() {
        Usuario usuario = new Usuario(1, "Ana López", "ana@mail.com", "3001234567");
        Compra compra = crearCompraBase();
        usuario.realizarCompra(compra);
        assertEquals(1, usuario.getHistorialCompras().size());
    }

    /** RF-002: actualizar perfil modifica datos correctamente */
    @Test
    void usuario_actualizarPerfil_modificaNombre() {
        Usuario usuario = new Usuario(1, "Ana López", "ana@mail.com", "3001234567");
        usuario.actualizarPerfil("María Gómez", null, null);
        assertEquals("María Gómez", usuario.getNombre());
    }

    /** RF-021: agregar método de pago */
    @Test
    void usuario_agregarMetodoPago_aumentaLista() {
        Usuario usuario = new Usuario(1, "Ana López", "ana@mail.com", "3001234567");
        usuario.agregarMetodoPago(new PagoTarjeta("1234", "Ana"));
        assertEquals(1, usuario.getMetodosDepago().size());
    }

    /** RF-021: eliminar método de pago */
    @Test
    void usuario_eliminarMetodoPago_reduceLista() {
        Usuario usuario = new Usuario(1, "Ana López", "ana@mail.com", "3001234567");
        PagoStrategy tarjetaPago = new PagoTarjeta("1234", "Ana");
        usuario.agregarMetodoPago(tarjetaPago);
        assertTrue(usuario.eliminarMetodoPago(tarjetaPago));
        assertTrue(usuario.getMetodosDepago().isEmpty());
    }

    /** RF-022: buscarCompra por índice válido retorna la compra */
    @Test
    void usuario_buscarCompra_indiceValido_retornaCompra() {
        Usuario usuario = new Usuario(1, "Ana López", "ana@mail.com", "3001234567");
        Compra compra = crearCompraBase();
        usuario.realizarCompra(compra);
        assertEquals(compra, usuario.buscarCompra(0));
    }

    /** RF-022: buscarCompra por índice inválido retorna null */
    @Test
    void usuario_buscarCompra_indiceInvalido_retornaNull() {
        Usuario usuario = new Usuario(1, "Ana López", "ana@mail.com", "3001234567");
        assertNull(usuario.buscarCompra(99));
    }

    // =========================================================================
    // TARIFA
    // =========================================================================

    /** Tarifa retorna monto correcto */
    @Test
    void tarifa_getMonto_retornaValorCorrecto() {
        assertEquals(80_000, tarifa.getMonto());
    }

    /** Tarifa retorna tipo de zona correcto */
    @Test
    void tarifa_getTipoZona_retornaZonaCorrecta() {
        assertEquals(TipoZona.GENERAL, tarifa.getTipoZona());
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private Compra crearCompraBase() {
        Usuario usuario = new Usuario(1, "Ana López", "ana@mail.com", "3001234567");
        // Nota: Concierto es subclase concreta de Evento — ajusta si usas otra
        // Aquí usamos un stub anónimo para no depender de una subclase específica
        Recinto recinto = new Recinto("Estadio", "Calle 1", "Bogotá");
        Evento evento = new Evento("Rock Fest", "Música",
                LocalDate.of(2026, 9, 20), "Bogotá",
                "Descripción", "Políticas", recinto) {};
        PagoStrategy pago = new PagoTarjeta("1234-5678", "Ana López");
        return new Compra(1, LocalDate.now(), new CompraCreada(), usuario, evento, pago);
    }
}