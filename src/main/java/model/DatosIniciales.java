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

        // --- ADMINISTRADOR ---
        Administrador admin = new Administrador(1, "Admin Principal", "admin@ticketland.com", "3001111111", "FULL");
        sistema.agregarAdministrador(admin);


        // --- RECINTO 1: ESTADIO ---
        Recinto estadio = new Recinto("Estadio El Campin", "Cra 30 #57-60", "Bogotá");
        Zona zonaVIP1 = new Zona(1, "VIP Estadio", 50, 350000, TipoZona.VIP);
        Zona zonaGeneral1 = new Zona(2, "General Estadio", 200, 120000, TipoZona.GENERAL);
        Zona zonaPreferencial1 = new Zona(3, "Preferencial Estadio", 100, 220000, TipoZona.PREFERENCIAL);
        for (int i = 1; i <= 10; i++) zonaVIP1.agregarAsiento(new Asiento(i, "A", i, new Disponible()));
        for (int i = 1; i <= 20; i++) zonaGeneral1.agregarAsiento(new Asiento(i, "B", i, new Disponible()));
        for (int i = 1; i <= 15; i++) zonaPreferencial1.agregarAsiento(new Asiento(i, "C", i, new Disponible()));
        estadio.agregarZona(zonaVIP1);
        estadio.agregarZona(zonaGeneral1);
        estadio.agregarZona(zonaPreferencial1);

        // --- RECINTO 2: TEATRO ---
        // --- RECINTO 2: TEATRO ---
        Recinto teatroRecinto = new Recinto("Teatro Jorge Eliécer Gaitán", "Cra 7 #22-47", "Bogotá");
        Zona zonaVIP2 = new Zona(4, "VIP Teatro", 30, 180000, TipoZona.VIP);
        Zona zonaGeneral2 = new Zona(5, "General Teatro", 150, 80000, TipoZona.GENERAL);
        for (int i = 1; i <= 8; i++) zonaVIP2.agregarAsiento(new Asiento(i, "A", i, new Disponible()));
        for (int i = 1; i <= 15; i++) zonaGeneral2.agregarAsiento(new Asiento(i, "B", i, new Disponible()));
        teatroRecinto.agregarZona(zonaVIP2);
        teatroRecinto.agregarZona(zonaGeneral2);

        // --- EVENTOS ---
        Concierto concierto = new Concierto(
                "Concierto Juanes",
                "Concierto",
                LocalDate.of(2026, 8, 15),
                "Bogotá",
                "Gran concierto de Juanes en el Campin",
                "No reembolsable después de 48h",
                "Juanes", "Rock latino", estadio
        );
        concierto.activar();
        sistema.agregarEvento(concierto);

        Teatro obraTeatro = new Teatro(
                "Hamlet",
                "Teatro",
                LocalDate.of(2026, 9, 10),
                "Bogotá",
                "La obra clásica de Shakespeare",
                "Reembolsable hasta 24h antes",
                "Hamlet", "Carlos Moreno", teatroRecinto
        );
        obraTeatro.activar();
        sistema.agregarEvento(obraTeatro);

        Conferencia conferencia = new Conferencia(
                "Conferencia de IA 2026",
                "Conferencia",
                LocalDate.of(2026, 10, 5),
                "Medellín",
                "Los avances más recientes en inteligencia artificial",
                "No reembolsable",
                "Dr. Ana Torres", "Inteligencia Artificial", teatroRecinto
        );
        conferencia.activar();
        sistema.agregarEvento(conferencia);

        // --- COMPRAS DE PRUEBA ---
        // Compra 1: usuario1 compra entrada VIP al concierto y paga
        Asiento asiento1 = zonaVIP1.getAsientos().get(0);
        asiento1.reservar();
        Entrada entrada1 = new Entrada(1,zonaVIP1.getPrecioBase(), EstadoEntrada.ACTIVA, zonaVIP1, asiento1);
        Compra compra1 = sistema.crearCompra(usuario1, concierto, new PagoTarjeta("1234-5678-9012-3456", "Luna Rios"));
        compra1.agregarEntrada(entrada1);
        compra1.pagar();

        // Compra 2: usuario2 compra entrada General al concierto (pendiente de pago)
        Asiento asiento2 = zonaGeneral1.getAsientos().get(0);
        asiento2.reservar();
        Entrada entrada2 = new Entrada(2,zonaGeneral1.getPrecioBase(), EstadoEntrada.ACTIVA, zonaGeneral1, asiento2);
        Compra compra2 = sistema.crearCompra(usuario2, concierto, new PagoEfectivo());
        compra2.agregarEntrada(entrada2);

        // Compra 3: usuario3 compra entrada al teatro y cancela
        Asiento asiento3 = zonaVIP2.getAsientos().get(0);
        asiento3.reservar();
        Entrada entrada3 = new Entrada(3, zonaVIP2.getPrecioBase(), EstadoEntrada.ACTIVA, zonaVIP2, asiento3);
        Compra compra3 = sistema.crearCompra(usuario3, obraTeatro, new PagoPSE("Bancolombia"));
        compra3.agregarEntrada(entrada3);
        compra3.cancelar();

        // --- INCIDENCIA DE PRUEBA ---
        admin.registrarIncidencia(
                Incidencia.Tipo.ERROR_PAGO,
                "Fallo en pago de compra de usuario2",
                "Compra-usuario2",
                sistema
        );
    }

    @Override
    public String toString() {
        return "Sistema inicializado: " +
                sistema.getUsuarios().size() + " usuarios | " +
                sistema.getEventos().size() + " eventos | " +
                sistema.getCompras().size() + " compras";
    }
}