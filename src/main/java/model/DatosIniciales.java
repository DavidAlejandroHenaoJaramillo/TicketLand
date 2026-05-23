package model;

import state.Disponible;
import strategy.PagoEfectivo;
import strategy.PagoPSE;
import strategy.PagoTarjeta;

import java.time.LocalDate;

public class DatosIniciales {

    private TicketLand sistema;

    public DatosIniciales(TicketLand sistema) {
        this.sistema = sistema;
        inicializar();
    }

    private void inicializar() {

        // --- USUARIOS ---
        Usuario usuario1 = new Usuario(1, "Luna Rios", "luna@gmail.com", "3001234567");
        Usuario usuario2 = new Usuario(2, "Carlos Pérez", "carlos@gmail.com", "3107654321");
        Usuario usuario3 = new Usuario(3, "María González", "maria@gmail.com", "3209876543");
        sistema.agregarUsuario(usuario1);
        sistema.agregarUsuario(usuario2);
        sistema.agregarUsuario(usuario3);

        // --- RECINTOS Y ZONAS ---
        Recinto estadio = new Recinto("Estadio El Campin", "Cra 30 #57-60, Bogotá");
        Zona zonaVIP = new Zona(50, 350000, TipoZona.VIP);
        Zona zonaGeneral = new Zona(200, 120000, TipoZona.GENERAL);
        Zona zonaPreferencial = new Zona(100, 220000, TipoZona.PREFERENCIAL);

        for (int i = 1; i <= 10; i++) {
            zonaVIP.agregarAsiento(new Asiento("A", i, new Disponible()));
        }
        for (int i = 1; i <= 20; i++) {
            zonaGeneral.agregarAsiento(new Asiento("B", i, new Disponible()));
        }
        for (int i = 1; i <= 15; i++) {
            zonaPreferencial.agregarAsiento(new Asiento("C", i, new Disponible()));
        }

        estadio.agregarZona(zonaVIP);
        estadio.agregarZona(zonaGeneral);
        estadio.agregarZona(zonaPreferencial);

        // --- EVENTOS ---
        Concierto concierto = new Concierto(
                "Concierto Juanes",
                "Concierto",
                LocalDate.of(2026, 8, 15),
                "Bogotá",
                "Gran concierto de Juanes en el Campin",
                "No reembolsable después de 48h",
                "Juanes",
                "Rock latino",
                estadio
        );
        concierto.setRecinto(estadio);
        concierto.activar();
        sistema.agregarEvento(concierto);

        // --- COMPRA DE PRUEBA ---
        Asiento asientoPrueba = zonaVIP.getAsientos().get(0);
        asientoPrueba.reservar();

        Entrada entrada = new Entrada(
                zonaVIP.getPrecioBase(),
                EstadoEntrada.ACTIVA,
                zonaVIP,
                asientoPrueba
        );

        Compra compraPrueba = sistema.crearCompra(usuario1, concierto, new PagoTarjeta("1234-5678-9012-3456", "Luna Rios"));
        compraPrueba.agregarEntrada(entrada);
    }

    @Override
    public String toString() {
        return "Sistema inicializado: " +
                sistema.getUsuarios().size() + " usuarios | " +
                sistema.getEventos().size() + " eventos | " +
                sistema.getCompras().size() + " compras";
    }
}