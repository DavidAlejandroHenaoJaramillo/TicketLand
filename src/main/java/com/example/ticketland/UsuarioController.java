package com.example.ticketland;

import decorator.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import state.*;
import strategy.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioController {

    @FXML private Label lblBienvenida;
    @FXML private Label lblNotificacion;

    @FXML private TextField txtFiltroCiudad;
    @FXML private TextField txtFiltroCategoria;
    @FXML private TextField txtFiltroPrecio;
    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colCiudad;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colCategoria;
    @FXML private TableColumn<Evento, String> colEstado;

    @FXML private Label lblDetalleNombre;
    @FXML private Label lblDetalleLugar;
    @FXML private Label lblDetalleFecha;
    @FXML private Label lblDetalleCategoria;
    @FXML private Label lblDetalleDescripcion;
    @FXML private Label lblDetallePoliticas;

    @FXML private TableView<Zona> tablaZonas;
    @FXML private TableColumn<Zona, String> colZonaNombre;
    @FXML private TableColumn<Zona, String> colZonaTipo;
    @FXML private TableColumn<Zona, String> colZonaPrecio;
    @FXML private TableColumn<Zona, String> colZonaDisponibles;
    @FXML private TableColumn<Zona, String> colZonaCapacidad;

    @FXML private ComboBox<Zona> cmbZona;
    @FXML private ComboBox<Asiento> cmbAsiento;
    @FXML private ComboBox<PagoStrategy> cmbMetodoPago;
    @FXML private Label lblInfoZona;
    @FXML private CheckBox chkVIP;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkMerchandising;
    @FXML private CheckBox chkParqueadero;
    @FXML private Label lblTotalEstimado;
    @FXML private Label lblMensajeCompra;

    @FXML private TableView<Asiento> tablaMapaAsientos;
    @FXML private TableColumn<Asiento, String> colMapaAsientoId;
    @FXML private TableColumn<Asiento, String> colMapaFila;
    @FXML private TableColumn<Asiento, String> colMapaNumero;
    @FXML private TableColumn<Asiento, String> colMapaZona;
    @FXML private TableColumn<Asiento, String> colMapaTipo;
    @FXML private TableColumn<Asiento, String> colMapaPrecio;
    @FXML private TableColumn<Asiento, String> colMapaEstado;
    @FXML private Label lblInfoMapa;

    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraFecha;
    @FXML private TableColumn<Compra, String> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraEstado;

    @FXML private Label lblComprobanteEvento;
    @FXML private Label lblComprobanteId;
    @FXML private Label lblComprobanteFecha;
    @FXML private Label lblComprobanteEstado;
    @FXML private Label lblComprobantePago;
    @FXML private Label lblComprobanteRecinto;
    @FXML private TableView<EntradaBase> tablaEntradasCompra;
    @FXML private TableColumn<EntradaBase, String> colEntradaId;
    @FXML private TableColumn<EntradaBase, String> colEntradaZona;
    @FXML private TableColumn<EntradaBase, String> colEntradaAsiento;
    @FXML private TableColumn<EntradaBase, String> colEntradaCosto;
    @FXML private TableColumn<EntradaBase, String> colEntradaEstado;
    @FXML private Label lblComprobanteTotal;

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private Label lblMensajePerfil;
    @FXML private TableView<PagoStrategy> tablaMetodosPago;
    @FXML private TableColumn<PagoStrategy, String> colPagoTipo;
    @FXML private TableColumn<PagoStrategy, String> colPagoDetalle;
    @FXML private ComboBox<String> cmbTipoPago;
    @FXML private TextField txtDatoPago;
    @FXML private Label lblMensajePago;
    @FXML private Label lblStatCompras;
    @FXML private Label lblStatGastado;
    @FXML private Label lblStatEventos;

    private Usuario usuario;
    private TicketLand sistema = TicketLand.getInstance();
    private Evento eventoSeleccionado = null;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getCorreo());
        txtTelefono.setText(usuario.getTelefono());
        inicializarFiltroEstado();
        inicializarMetodoPago();
        inicializarTipoPago();
        cargarEventos();
        cargarHistorial(null);
        configurarSeleccionEvento();
        configurarSeleccionCompra();
        actualizarCheckboxTotales();
        cargarMetodosPago();
        actualizarStats();
        if (lblNotificacion != null)
            lblNotificacion.setText("🔔 " + usuario.getHistorialCompras().size() + " compras");
    }

    private void inicializarFiltroEstado() {
        if (cmbFiltroEstado == null) return;
        cmbFiltroEstado.setItems(FXCollections.observableArrayList(
                "Todas", "CREADA", "PAGADA", "CONFIRMADA", "CANCELADA", "REEMBOLSADA", "INCIDENCIA"));
        cmbFiltroEstado.getSelectionModel().selectFirst();
    }

    private void inicializarMetodoPago() {
        if (cmbMetodoPago == null) return;
        cmbMetodoPago.setItems(FXCollections.observableArrayList(usuario.getMetodosDepago()));
        if (!usuario.getMetodosDepago().isEmpty())
            cmbMetodoPago.getSelectionModel().selectFirst();
    }

    private void inicializarTipoPago() {
        if (cmbTipoPago == null) return;
        cmbTipoPago.setItems(FXCollections.observableArrayList("Tarjeta", "PSE", "Efectivo"));
        cmbTipoPago.getSelectionModel().selectFirst();
    }

    private void configurarSeleccionEvento() {
        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) {
                eventoSeleccionado = nuevo;
                mostrarDetalleEvento(nuevo);
                cargarZonasEvento(nuevo);
                mostrarMapaAsientos(nuevo);
            }
        });
        if (cmbZona != null) {
            cmbZona.setOnAction(e -> {
                actualizarAsientosDeZona();
                actualizarCheckboxTotales();
            });
        }
        if (chkVIP != null)           chkVIP.setOnAction(e -> actualizarCheckboxTotales());
        if (chkSeguro != null)        chkSeguro.setOnAction(e -> actualizarCheckboxTotales());
        if (chkMerchandising != null) chkMerchandising.setOnAction(e -> actualizarCheckboxTotales());
        if (chkParqueadero != null)   chkParqueadero.setOnAction(e -> actualizarCheckboxTotales());
    }

    private void mostrarDetalleEvento(Evento evento) {
        if (lblDetalleNombre == null) return;
        lblDetalleNombre.setText(evento.getNombre());
        lblDetalleLugar.setText(evento.getRecinto() != null
                ? evento.getRecinto().getNombre() + " — " + evento.getRecinto().getCiudad()
                : evento.getCiudad());
        lblDetalleFecha.setText(evento.getFecha().toString());
        lblDetalleCategoria.setText(evento.getCategoria());
        lblDetalleDescripcion.setText(evento.getDescripcion() != null && !evento.getDescripcion().isEmpty()
                ? evento.getDescripcion() : "Sin descripción disponible.");
        lblDetallePoliticas.setText(evento.getPoliticas() != null && !evento.getPoliticas().isEmpty()
                ? evento.getPoliticas() : "Sin políticas definidas.");
    }

    private void cargarZonasEvento(Evento evento) {
        if (tablaZonas == null || evento.getRecinto() == null) return;
        colZonaNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colZonaTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipoZona().toString()));
        colZonaPrecio.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().getPrecioBase()));
        colZonaDisponibles.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getAsientosDisponibles().size())));
        colZonaCapacidad.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getCapacidad())));
        tablaZonas.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(Zona item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) { setStyle(""); return; }
                if (item.getAsientosDisponibles().isEmpty())
                    setStyle("-fx-background-color: #3a1010;");
                else if (item.getAsientosDisponibles().size() < 3)
                    setStyle("-fx-background-color: #3a2a00;");
                else
                    setStyle("-fx-background-color: #0a2a1a;");
            }
        });
        tablaZonas.setItems(FXCollections.observableArrayList(evento.getRecinto().getZonas()));
        if (cmbZona != null) {
            cmbZona.setItems(FXCollections.observableArrayList(evento.getRecinto().getZonas()));
            cmbZona.getSelectionModel().selectFirst();
            actualizarAsientosDeZona();
        }
    }

    private void actualizarAsientosDeZona() {
        Zona zona = cmbZona != null ? cmbZona.getValue() : null;
        if (zona == null) return;
        List<Asiento> disponibles = zona.getAsientosDisponibles();
        if (cmbAsiento != null) {
            cmbAsiento.setItems(FXCollections.observableArrayList(disponibles));
            if (!disponibles.isEmpty()) cmbAsiento.getSelectionModel().selectFirst();
        }
        if (lblInfoZona != null)
            lblInfoZona.setText(zona.getNombre() + " | Precio base: $" + (int) zona.getPrecioBase()
                    + " | Disponibles: " + disponibles.size() + " / " + zona.getCapacidad());
        actualizarCheckboxTotales();
    }

    private void actualizarCheckboxTotales() {
        if (lblTotalEstimado == null) return;
        Zona zona = cmbZona != null ? cmbZona.getValue() : null;
        double base = (zona != null) ? zona.getPrecioBase() : 0;
        double extras = 0;
        if (chkVIP != null && chkVIP.isSelected())                     extras += 100000;
        if (chkSeguro != null && chkSeguro.isSelected())               extras += 20000;
        if (chkMerchandising != null && chkMerchandising.isSelected()) extras += 50000;
        if (chkParqueadero != null && chkParqueadero.isSelected())     extras += 30000;
        lblTotalEstimado.setText("$" + (int)(base + extras));
    }

    private void mostrarMapaAsientos(Evento evento) {
        if (tablaMapaAsientos == null || evento.getRecinto() == null) return;
        colMapaAsientoId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getIdAsiento())));
        colMapaFila.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFila()));
        colMapaNumero.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getNumero())));
        colMapaZona.setCellValueFactory(d -> {
            for (Zona z : evento.getRecinto().getZonas())
                if (z.getAsientos().contains(d.getValue()))
                    return new SimpleStringProperty(z.getNombre());
            return new SimpleStringProperty("-");
        });
        colMapaTipo.setCellValueFactory(d -> {
            for (Zona z : evento.getRecinto().getZonas())
                if (z.getAsientos().contains(d.getValue()))
                    return new SimpleStringProperty(z.getTipoZona().toString());
            return new SimpleStringProperty("-");
        });
        colMapaPrecio.setCellValueFactory(d -> {
            for (Zona z : evento.getRecinto().getZonas())
                if (z.getAsientos().contains(d.getValue()))
                    return new SimpleStringProperty("$" + (int) z.getPrecioBase());
            return new SimpleStringProperty("-");
        });
        colMapaEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstado().toString()));
        tablaMapaAsientos.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(Asiento item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) { setStyle(""); return; }
                switch (item.getEstado().toString()) {
                    case "DISPONIBLE" -> setStyle("-fx-background-color: #0a2a1a;");
                    case "RESERVADO"  -> setStyle("-fx-background-color: #2a2000;");
                    case "VENDIDO"    -> setStyle("-fx-background-color: #2a0a0a;");
                    case "BLOQUEADO"  -> setStyle("-fx-background-color: #1a1a2a;");
                    default           -> setStyle("");
                }
            }
        });
        List<Asiento> todos = new ArrayList<>();
        for (Zona z : evento.getRecinto().getZonas()) todos.addAll(z.getAsientos());
        tablaMapaAsientos.setItems(FXCollections.observableArrayList(todos));
        if (lblInfoMapa != null)
            lblInfoMapa.setText(evento.getNombre() + " | " + evento.getRecinto().getNombre()
                    + " — " + todos.size() + " asientos totales");
    }

    @FXML private void buscarEventos() {
        String ciudad    = txtFiltroCiudad.getText().trim();
        String categoria = txtFiltroCategoria.getText().trim();
        String precioStr = txtFiltroPrecio.getText().trim();
        Double precioMax = null;
        try {
            if (!precioStr.isEmpty()) precioMax = Double.parseDouble(precioStr);
        } catch (NumberFormatException ex) {
            setMsg("Precio máximo inválido.", true); return;
        }
        cargarTablaEventos(sistema.buscarEventos(
                ciudad.isEmpty() ? null : ciudad,
                categoria.isEmpty() ? null : categoria,
                null, precioMax));
    }

    private void cargarEventos() { cargarTablaEventos(sistema.getEventosActivos()); }

    private void cargarTablaEventos(List<Evento> eventos) {
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));
        colCategoria.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategoria()));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));
        tablaEventos.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(Evento item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) { setStyle(""); return; }
                switch (item.getEstado().toString()) {
                    case "ACTIVO"    -> setStyle("-fx-background-color: #0a1f0a;");
                    case "PAUSADO"   -> setStyle("-fx-background-color: #1f1500;");
                    case "CANCELADO" -> setStyle("-fx-background-color: #1f0a0a;");
                    default          -> setStyle("");
                }
            }
        });
        tablaEventos.setItems(FXCollections.observableArrayList(eventos));
    }

    @FXML private void comprarEntrada() {
        if (eventoSeleccionado == null) { setMsg("Selecciona un evento.", true); return; }
        Zona zona = cmbZona != null ? cmbZona.getValue() : null;
        if (zona == null || !zona.hayDisponibilidad()) {
            setMsg("No hay asientos disponibles en la zona seleccionada.", true); return;
        }
        Asiento asiento = (cmbAsiento != null && cmbAsiento.getValue() != null)
                ? cmbAsiento.getValue() : zona.getAsientosDisponibles().get(0);
        PagoStrategy metodoPago = (cmbMetodoPago != null && cmbMetodoPago.getValue() != null)
                ? cmbMetodoPago.getValue()
                : new PagoTarjeta("0000-0000-0000-0000", usuario.getNombre());
        asiento.reservar();
        EntradaBase entradaFinal = new Entrada(
                sistema.getCompras().size() + 1, zona.getPrecioBase(), EstadoEntrada.ACTIVA, zona, asiento);
        if (chkVIP.isSelected())          entradaFinal = new EntradaVIP(entradaFinal);
        if (chkSeguro.isSelected())        entradaFinal = new SeguroDecorator(entradaFinal);
        if (chkMerchandising.isSelected()) entradaFinal = new MerchandisingDecorator(entradaFinal);
        if (chkParqueadero.isSelected())   entradaFinal = new ParqueaderoDecorator(entradaFinal);
        Compra compra = sistema.crearCompra(usuario, eventoSeleccionado, metodoPago);
        compra.agregarEntrada(entradaFinal);
        compra.pagar();
        cargarHistorial(null);
        actualizarAsientosDeZona();
        mostrarMapaAsientos(eventoSeleccionado);
        cargarZonasEvento(eventoSeleccionado);
        actualizarStats();
        if (lblNotificacion != null)
            lblNotificacion.setText("🔔 " + usuario.getHistorialCompras().size() + " compras");
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¡Compra exitosa!");
        alerta.setHeaderText("Tu entrada ha sido comprada");
        alerta.setContentText("Evento: " + eventoSeleccionado.getNombre()
                + "\nZona: " + zona.getNombre()
                + "\nAsiento: " + asiento.getFila() + "-" + asiento.getNumero()
                + "\nMétodo de pago: " + metodoPago.toString()
                + "\nTotal pagado: $" + (int) compra.calcularTotal());
        alerta.showAndWait();
        setMsg("✅ ¡Compra realizada! Total: $" + (int) compra.calcularTotal(), false);
    }

    @FXML private void filtrarHistorial() {
        String filtro = cmbFiltroEstado != null ? cmbFiltroEstado.getValue() : "Todas";
        cargarHistorial("Todas".equals(filtro) ? null : filtro);
    }

    @FXML private void mostrarTodasCompras() {
        if (cmbFiltroEstado != null) cmbFiltroEstado.getSelectionModel().selectFirst();
        cargarHistorial(null);
    }

    @FXML private void refrescarHistorial() { cargarHistorial(null); }

    private void cargarHistorial(String filtroEstado) {
        colCompraFecha.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFechaCompra().toString()));
        colCompraEvento.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEvento().getNombre()));
        colCompraTotal.setCellValueFactory(d ->
                new SimpleStringProperty("$" + (int) d.getValue().calcularTotal()));
        colCompraEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstadoCompra().toString()));
        tablaCompras.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(Compra item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) { setStyle(""); return; }
                switch (item.getEstadoCompra().toString()) {
                    case "PAGADA"      -> setStyle("-fx-background-color: #0a1f10;");
                    case "CONFIRMADA"  -> setStyle("-fx-background-color: #0a1520;");
                    case "CANCELADA"   -> setStyle("-fx-background-color: #1f0a0a;");
                    case "REEMBOLSADA" -> setStyle("-fx-background-color: #1a0a1a;");
                    default            -> setStyle("");
                }
            }
        });
        List<Compra> compras = usuario.getHistorialCompras();
        if (filtroEstado != null)
            compras = compras.stream()
                    .filter(c -> c.getEstadoCompra().toString().equals(filtroEstado))
                    .collect(java.util.stream.Collectors.toList());
        tablaCompras.setItems(FXCollections.observableArrayList(compras));
    }

    private void configurarSeleccionCompra() {
        if (tablaCompras == null) return;
        tablaCompras.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) mostrarComprobante(nuevo);
        });
    }

    private void mostrarComprobante(Compra compra) {
        if (lblComprobanteEvento == null) return;
        lblComprobanteEvento.setText(compra.getEvento().getNombre());
        lblComprobanteId.setText("#" + compra.getIdCompra());
        lblComprobanteFecha.setText(compra.getFechaCompra().toString());
        lblComprobanteEstado.setText(compra.getEstadoCompra().toString());
        lblComprobantePago.setText(compra.getMetodoPago() != null ? compra.getMetodoPago().toString() : "—");
        lblComprobanteRecinto.setText(compra.getEvento().getRecinto() != null
                ? compra.getEvento().getRecinto().getNombre() : "—");
        lblComprobanteTotal.setText("$" + (int) compra.calcularTotal());
        String color = switch (compra.getEstadoCompra().toString()) {
            case "PAGADA", "CONFIRMADA" -> "-fx-text-fill: #2ecc71;";
            case "CANCELADA"            -> "-fx-text-fill: #e74c3c;";
            case "REEMBOLSADA"          -> "-fx-text-fill: #9b59b6;";
            default                     -> "-fx-text-fill: #f1c40f;";
        };
        lblComprobanteEstado.setStyle("-fx-font-weight: bold; " + color);
        if (tablaEntradasCompra != null) {
            colEntradaId.setCellValueFactory(d ->
                    new SimpleStringProperty(String.valueOf(compra.getEntradas().indexOf(d.getValue()) + 1)));
            colEntradaZona.setCellValueFactory(d -> {
                if (d.getValue() instanceof Entrada en && en.getZona() != null)
                    return new SimpleStringProperty(en.getZona().getNombre());
                return new SimpleStringProperty("Entrada con servicios");
            });
            colEntradaAsiento.setCellValueFactory(d -> {
                if (d.getValue() instanceof Entrada en && en.getAsiento() != null)
                    return new SimpleStringProperty(en.getAsiento().getFila() + "-" + en.getAsiento().getNumero());
                return new SimpleStringProperty("—");
            });
            colEntradaCosto.setCellValueFactory(d ->
                    new SimpleStringProperty("$" + (int) d.getValue().getCosto()));
            colEntradaEstado.setCellValueFactory(d -> {
                if (d.getValue() instanceof Entrada en)
                    return new SimpleStringProperty(en.getEstadoEntrada().toString());
                return new SimpleStringProperty("—");
            });
            tablaEntradasCompra.setItems(FXCollections.observableArrayList(compra.getEntradas()));
        }
    }

    @FXML private void cancelarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { setMsg("Selecciona una compra primero.", true); return; }
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Cancelar compra");
        conf.setHeaderText("¿Cancelar la compra del evento \"" + c.getEvento().getNombre() + "\"?");
        conf.setContentText("Total: $" + (int) c.calcularTotal() + "\nEsta acción no se puede deshacer.");
        Optional<ButtonType> res = conf.showAndWait();
        if (res.isEmpty() || res.get() != ButtonType.OK) return;
        boolean ok = c.cancelar();
        cargarHistorial(null);
        actualizarStats();
        if (ok && eventoSeleccionado != null) mostrarMapaAsientos(eventoSeleccionado);
        setMsg(ok ? "✅ Compra cancelada." : "❌ No se puede cancelar esta compra.", !ok);
    }

    @FXML private void exportarCSV() {
        new GeneradorReporte(sistema).exportarVentasCSV("reporte_usuario.csv", null, null);
        setMsg("📄 CSV exportado como reporte_usuario.csv", false);
    }

    @FXML private void exportarPDF() {
        new GeneradorReporte(sistema).exportarVentasPDF("reporte_usuario.pdf", null, null);
        setMsg("📑 PDF exportado como reporte_usuario.pdf", false);
    }

    @FXML private void guardarPerfil() {
        String nombre   = txtNombre.getText().trim();
        String correo   = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            lblMensajePerfil.setText("Todos los campos son obligatorios.");
            lblMensajePerfil.setStyle("-fx-text-fill: #e74c3c;"); return;
        }
        usuario.actualizarPerfil(nombre, correo, telefono);
        lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
        lblMensajePerfil.setText("✅ Perfil actualizado correctamente.");
        lblMensajePerfil.setStyle("-fx-text-fill: #2ecc71;");
    }

    private void cargarMetodosPago() {
        if (tablaMetodosPago == null) return;
        colPagoTipo.setCellValueFactory(d -> {
            PagoStrategy p = d.getValue();
            String tipo = p instanceof PagoTarjeta ? "Tarjeta" : p instanceof PagoPSE ? "PSE" : "Efectivo";
            return new SimpleStringProperty(tipo);
        });
        colPagoDetalle.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().toString()));
        tablaMetodosPago.setItems(FXCollections.observableArrayList(usuario.getMetodosDepago()));
    }

    @FXML private void agregarMetodoPago() {
        String tipo = cmbTipoPago != null ? cmbTipoPago.getValue() : "";
        String dato = txtDatoPago != null ? txtDatoPago.getText().trim() : "";
        PagoStrategy nuevo = switch (tipo) {
            case "Tarjeta"  -> new PagoTarjeta(dato.isEmpty() ? "0000-0000-0000-0000" : dato, usuario.getNombre());
            case "PSE"      -> new PagoPSE(dato.isEmpty() ? "Bancolombia" : dato);
            default         -> new PagoEfectivo();
        };
        usuario.agregarMetodoPago(nuevo);
        cargarMetodosPago();
        inicializarMetodoPago();
        if (txtDatoPago != null) txtDatoPago.clear();
        if (lblMensajePago != null) {
            lblMensajePago.setText("✅ Método de pago agregado.");
            lblMensajePago.setStyle("-fx-text-fill: #2ecc71;");
        }
    }

    private void actualizarStats() {
        if (lblStatCompras == null) return;
        List<Compra> historial = usuario.getHistorialCompras();
        lblStatCompras.setText(String.valueOf(historial.size()));
        lblStatGastado.setText("$" + (int) historial.stream().mapToDouble(Compra::calcularTotal).sum());
        lblStatEventos.setText(String.valueOf(
                historial.stream().map(c -> c.getEvento().getNombre()).distinct().count()));
    }

    @FXML private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) lblBienvenida.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 580, 450));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            setMsg("Error al cerrar sesión.", true);
        }
    }

    private void setMsg(String msg, boolean error) {
        if (lblMensajeCompra == null) return;
        lblMensajeCompra.setText(msg);
        lblMensajeCompra.setStyle(error ? "-fx-text-fill: #e74c3c;" : "-fx-text-fill: #2ecc71;");
    }
}