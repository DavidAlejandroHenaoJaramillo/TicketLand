package viewController;

import com.example.ticketland.HelloApplication;
import controller.CompraController;
import controller.EventoController;
import controller.ReporteController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import decorator.EntradaBase;
import strategy.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import java.util.LinkedHashMap;
import java.util.Map;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UsuarioViewController {

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

    @FXML private GridPane gridAsientosCompra;
    @FXML private GridPane gridMapaAsientos;

    private Usuario usuario;
    private Evento eventoSeleccionado;

    private final EventoController eventoController = new EventoController();
    private final CompraController compraController = new CompraController();
    private final controller.UsuarioController usuarioController = new controller.UsuarioController();
    private final ReporteController reporteController = new ReporteController();

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

        if (lblNotificacion != null) {
            lblNotificacion.setText("🔔 " + usuario.getHistorialCompras().size() + " compras");
        }
    }

    private void inicializarFiltroEstado() {
        if (cmbFiltroEstado == null) return;

        cmbFiltroEstado.setItems(FXCollections.observableArrayList(
                "Todas", "CREADA", "PAGADA", "CONFIRMADA", "CANCELADA", "REEMBOLSADA", "INCIDENCIA"
        ));
        cmbFiltroEstado.getSelectionModel().selectFirst();
    }

    private void inicializarMetodoPago() {
        if (cmbMetodoPago == null) return;

        cmbMetodoPago.setItems(FXCollections.observableArrayList(usuario.getMetodosDepago()));

        if (!usuario.getMetodosDepago().isEmpty()) {
            cmbMetodoPago.getSelectionModel().selectFirst();
        }
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
                pintarAsientosPorEvento(nuevo);
            }
        });

        if (cmbZona != null) {
            cmbZona.setOnAction(e -> {
                actualizarAsientosDeZona();
                actualizarCheckboxTotales();
            });
        }

        if (chkVIP != null) chkVIP.setOnAction(e -> actualizarCheckboxTotales());
        if (chkSeguro != null) chkSeguro.setOnAction(e -> actualizarCheckboxTotales());
        if (chkMerchandising != null) chkMerchandising.setOnAction(e -> actualizarCheckboxTotales());
        if (chkParqueadero != null) chkParqueadero.setOnAction(e -> actualizarCheckboxTotales());
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
                ? evento.getDescripcion()
                : "Sin descripción disponible.");
        lblDetallePoliticas.setText(evento.getPoliticas() != null && !evento.getPoliticas().isEmpty()
                ? evento.getPoliticas()
                : "Sin políticas definidas.");
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
            @Override
            protected void updateItem(Zona item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll("zona-llena", "zona-poca", "zona-disponible");

                if (item == null || empty) return;

                if (item.getAsientosDisponibles().isEmpty()) {
                    getStyleClass().add("zona-llena");
                } else if (item.getAsientosDisponibles().size() < 3) {
                    getStyleClass().add("zona-poca");
                } else {
                    getStyleClass().add("zona-disponible");
                }
            }
        });

        tablaZonas.setItems(FXCollections.observableArrayList(eventoController.obtenerZonas(evento)));

        if (cmbZona != null) {
            cmbZona.setItems(FXCollections.observableArrayList(eventoController.obtenerZonas(evento)));
            cmbZona.getSelectionModel().selectFirst();
            actualizarAsientosDeZona();
        }
    }

    private void actualizarAsientosDeZona() {
        Zona zona = cmbZona != null ? cmbZona.getValue() : null;
        if (zona == null) return;

        List<Asiento> disponibles = eventoController.obtenerAsientosDisponibles(zona);

        if (cmbAsiento != null) {
            cmbAsiento.setItems(FXCollections.observableArrayList(disponibles));

            if (!disponibles.isEmpty()) {
                cmbAsiento.getSelectionModel().selectFirst();
            }
        }

        if (lblInfoZona != null) {
            lblInfoZona.setText(zona.getNombre()
                    + " | Precio base: $" + (int) zona.getPrecioBase()
                    + " | Disponibles: " + disponibles.size()
                    + " / " + zona.getCapacidad());
        }
        actualizarCheckboxTotales();
    }

    private void actualizarCheckboxTotales() {
        if (lblTotalEstimado == null) return;

        Zona zona = cmbZona != null ? cmbZona.getValue() : null;
        double base = zona != null ? zona.getPrecioBase() : 0;
        double extras = 0;

        if (chkVIP != null && chkVIP.isSelected()) extras += 100000;
        if (chkSeguro != null && chkSeguro.isSelected()) extras += 20000;
        if (chkMerchandising != null && chkMerchandising.isSelected()) extras += 50000;
        if (chkParqueadero != null && chkParqueadero.isSelected()) extras += 30000;

        lblTotalEstimado.setText("$" + (int) (base + extras));
    }

    private void mostrarMapaAsientos(Evento evento) {
        if (tablaMapaAsientos == null || evento.getRecinto() == null) return;

        colMapaAsientoId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getIdAsiento())));
        colMapaFila.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFila()));
        colMapaNumero.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getNumero())));

        colMapaZona.setCellValueFactory(d -> {
            for (Zona zona : evento.getRecinto().getZonas()) {
                if (zona.getAsientos().contains(d.getValue())) {
                    return new SimpleStringProperty(zona.getNombre());
                }
            }
            return new SimpleStringProperty("-");
        });

        colMapaTipo.setCellValueFactory(d -> {
            for (Zona zona : evento.getRecinto().getZonas()) {
                if (zona.getAsientos().contains(d.getValue())) {
                    return new SimpleStringProperty(zona.getTipoZona().toString());
                }
            }
            return new SimpleStringProperty("-");
        });

        colMapaPrecio.setCellValueFactory(d -> {
            for (Zona zona : evento.getRecinto().getZonas()) {
                if (zona.getAsientos().contains(d.getValue())) {
                    return new SimpleStringProperty("$" + (int) zona.getPrecioBase());
                }
            }
            return new SimpleStringProperty("-");
        });

        colMapaEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstado().toString()));

        tablaMapaAsientos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Asiento item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll("disponible", "reservado", "vendido", "bloqueado");

                if (item == null || empty) {
                    setStyle("");
                    return;
                }

                switch (item.getEstado().toString()) {
                    case "DISPONIBLE" -> getStyleClass().add("disponible");
                    case "RESERVADO" -> getStyleClass().add("reservado");
                    case "VENDIDO" -> getStyleClass().add("vendido");
                    case "BLOQUEADO" -> getStyleClass().add("bloqueado");
                }
            }
        });

        List<Asiento> asientos = eventoController.obtenerAsientos(evento);
        tablaMapaAsientos.setItems(FXCollections.observableArrayList(asientos));

        if (lblInfoMapa != null) {
            lblInfoMapa.setText(evento.getNombre()
                    + " | " + evento.getRecinto().getNombre()
                    + " — " + asientos.size()
                    + " asientos totales");
        }
        pintarMapaAsientos(evento);
    }

    private void pintarAsientosCompra(Evento evento, Zona zonaSeleccionada) {
        if (gridAsientosCompra == null || evento == null || zonaSeleccionada == null) return;

        gridAsientosCompra.getChildren().clear();

        Map<String, Integer> filas = new LinkedHashMap<>();
        for (Asiento asiento : zonaSeleccionada.getAsientos()) {
            filas.putIfAbsent(asiento.getFila(), filas.size());
        }

        for (Asiento asiento : zonaSeleccionada.getAsientos()) {
            ToggleButton btn = crearBotonAsiento(asiento);
            boolean disponible = asiento.getEstado().toString().equals("DISPONIBLE");

            btn.setDisable(!disponible);
            btn.setOnAction(e -> {
                if (cmbAsiento != null) {
                    cmbAsiento.getSelectionModel().select(asiento);
                }

                limpiarSeleccionVisual(gridAsientosCompra);
                btn.setStyle(estiloAsiento(asiento) + "-fx-border-color: #0057ff; -fx-border-width: 3;");
            });

            gridAsientosCompra.add(btn, asiento.getNumero(), filas.get(asiento.getFila()));
        }
    }

    private void pintarAsientosPorEvento(Evento evento) {
        if (gridAsientosCompra == null || evento == null || evento.getRecinto() == null) return;

        gridAsientosCompra.getChildren().clear();

        int filaBase = 0;

        for (Zona zona : evento.getRecinto().getZonas()) {
            Label lblZona = new Label(zona.getNombre() + " - $" + (int) zona.getPrecioBase());
            lblZona.setStyle("-fx-font-weight: bold; -fx-text-fill: #0057ff; -fx-padding: 10 0 4 0;");

            gridAsientosCompra.add(lblZona, 0, filaBase, 12, 1);
            filaBase++;

            Map<String, Integer> filas = new LinkedHashMap<>();

            for (Asiento asiento : zona.getAsientos()) {
                filas.putIfAbsent(asiento.getFila(), filas.size());
            }

            for (Asiento asiento : zona.getAsientos()) {
                ToggleButton btn = crearBotonAsiento(asiento);

                boolean disponible = asiento.getEstado().toString().equals("DISPONIBLE");
                btn.setDisable(!disponible);

                btn.setOnAction(e -> {
                    if (cmbZona != null) {
                        cmbZona.getSelectionModel().select(zona);
                    }

                    if (cmbAsiento != null) {
                        cmbAsiento.setItems(FXCollections.observableArrayList(zona.getAsientosDisponibles()));
                        cmbAsiento.getSelectionModel().select(asiento);
                    }

                    limpiarSeleccionVisual(gridAsientosCompra);
                    btn.setStyle(estiloAsiento(asiento) + "-fx-border-color: #0057ff; -fx-border-width: 3;");

                    actualizarCheckboxTotales();

                    if (lblInfoZona != null) {
                        lblInfoZona.setText(zona.getNombre()
                                + " | Precio base: $" + (int) zona.getPrecioBase()
                                + " | Disponibles: " + zona.getAsientosDisponibles().size()
                                + " / " + zona.getCapacidad());
                    }
                });

                int fila = filaBase + filas.get(asiento.getFila());
                int columna = asiento.getNumero();

                gridAsientosCompra.add(btn, columna, fila);
            }

            filaBase += filas.size() + 1;
        }
    }

    private void pintarMapaAsientos(Evento evento) {
        if (gridMapaAsientos == null || evento == null || evento.getRecinto() == null) return;

        gridMapaAsientos.getChildren().clear();

        int filaBase = 0;

        for (Zona zona : evento.getRecinto().getZonas()) {
            Label lblZona = new Label(zona.getNombre());
            lblZona.setStyle("-fx-font-weight: bold; -fx-text-fill: #0057ff; -fx-padding: 8 0 4 0;");
            gridMapaAsientos.add(lblZona, 0, filaBase, 10, 1);
            filaBase++;

            Map<String, Integer> filas = new LinkedHashMap<>();
            for (Asiento asiento : zona.getAsientos()) {
                filas.putIfAbsent(asiento.getFila(), filas.size());
            }

            for (Asiento asiento : zona.getAsientos()) {
                ToggleButton btn = crearBotonAsiento(asiento);
                btn.setDisable(true);

                int fila = filaBase + filas.get(asiento.getFila());
                int columna = asiento.getNumero();

                gridMapaAsientos.add(btn, columna, fila);
            }

            filaBase += filas.size() + 1;
        }
    }

    private ToggleButton crearBotonAsiento(Asiento asiento) {
        ToggleButton btn = new ToggleButton(asiento.getFila() + asiento.getNumero());

        btn.setUserData(asiento);
        btn.setMinSize(42, 36);
        btn.setPrefSize(42, 36);
        btn.setMaxSize(42, 36);
        btn.setAlignment(Pos.CENTER);
        btn.setStyle(estiloAsiento(asiento));

        return btn;
    }

    private String estiloAsiento(Asiento asiento) {
        String color = switch (asiento.getEstado().toString()) {
            case "DISPONIBLE" -> "#2ecc71";
            case "RESERVADO" -> "#f39c12";
            case "VENDIDO" -> "#e74c3c";
            case "BLOQUEADO" -> "#7f8c8d";
            default -> "#bdc3c7";
        };

        return "-fx-background-color: " + color + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 11;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 6;"
                + "-fx-border-radius: 6;"
                + "-fx-cursor: hand;";
    }

    private void limpiarSeleccionVisual(GridPane grid) {
        if (grid == null) return;

        for (Node node : grid.getChildren()) {
            if (node instanceof ToggleButton btn && btn.getUserData() instanceof Asiento asiento) {
                btn.setStyle(estiloAsiento(asiento));
            }
        }
    }

    @FXML
    private void buscarEventos() {
        String ciudad = txtFiltroCiudad.getText().trim();
        String categoria = txtFiltroCategoria.getText().trim();
        String precioStr = txtFiltroPrecio.getText().trim();
        Double precioMax = null;

        try {
            if (!precioStr.isEmpty()) {
                precioMax = Double.parseDouble(precioStr);
            }
        } catch (NumberFormatException ex) {
            setMsg("Precio máximo inválido.", true);
            return;
        }

        cargarTablaEventos(eventoController.buscarEventos(ciudad, categoria, precioMax));
    }

    private void cargarEventos() {
        cargarTablaEventos(eventoController.obtenerEventosActivos());
    }

    private void cargarTablaEventos(List<Evento> eventos) {
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));
        colCategoria.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategoria()));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));

        tablaEventos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Evento item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll("activo", "pausado", "cancelado");

                if (item == null || empty) return;

                switch (item.getEstado().toString()) {
                    case "ACTIVO" -> getStyleClass().add("activo");
                    case "PAUSADO" -> getStyleClass().add("pausado");
                    case "CANCELADO" -> getStyleClass().add("cancelado");
                }
            }
        });

        tablaEventos.setItems(FXCollections.observableArrayList(eventos));
    }

    @FXML
    private void comprarEntrada() {
        try {
            Compra compra = compraController.comprarEntrada(
                    usuario,
                    eventoSeleccionado,
                    cmbZona != null ? cmbZona.getValue() : null,
                    cmbAsiento != null ? cmbAsiento.getValue() : null,
                    cmbMetodoPago != null ? cmbMetodoPago.getValue() : null,
                    chkVIP != null && chkVIP.isSelected(),
                    chkSeguro != null && chkSeguro.isSelected(),
                    chkMerchandising != null && chkMerchandising.isSelected(),
                    chkParqueadero != null && chkParqueadero.isSelected()
            );

            cargarHistorial(null);
            actualizarAsientosDeZona();
            mostrarMapaAsientos(eventoSeleccionado);
            cargarZonasEvento(eventoSeleccionado);
            actualizarStats();

            if (lblNotificacion != null) {
                lblNotificacion.setText("🔔 " + usuario.getHistorialCompras().size() + " compras");
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("¡Compra exitosa!");
            alerta.setHeaderText("Tu entrada ha sido comprada");
            alerta.setContentText("Evento: " + eventoSeleccionado.getNombre()
                    + "\nTotal pagado: $" + (int) compra.calcularTotal());
            alerta.showAndWait();

            setMsg("Compra realizada, Total: $" + (int) compra.calcularTotal(), false);
        } catch (IllegalArgumentException e) {
            setMsg(e.getMessage(), true);
        }
    }

    @FXML
    private void filtrarHistorial() {
        String filtro = cmbFiltroEstado != null ? cmbFiltroEstado.getValue() : "Todas";
        cargarHistorial("Todas".equals(filtro) ? null : filtro);
    }

    @FXML
    private void mostrarTodasCompras() {
        if (cmbFiltroEstado != null) {
            cmbFiltroEstado.getSelectionModel().selectFirst();
        }

        cargarHistorial(null);
    }

    @FXML
    private void refrescarHistorial() {
        cargarHistorial(null);
    }

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
            @Override
            protected void updateItem(Compra item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll("pagada", "confirmada", "compra-cancelada", "reembolsada");

                if (item == null || empty) return;

                switch (item.getEstadoCompra().toString()) {
                    case "PAGADA" -> getStyleClass().add("pagada");
                    case "CONFIRMADA" -> getStyleClass().add("confirmada");
                    case "CANCELADA" -> getStyleClass().add("compra-cancelada");
                    case "REEMBOLSADA" -> getStyleClass().add("reembolsada");
                }
            }
        });

        tablaCompras.setItems(FXCollections.observableArrayList(
                compraController.filtrarHistorial(usuario, filtroEstado == null ? "Todas" : filtroEstado)
        ));
    }

    private void configurarSeleccionCompra() {
        if (tablaCompras == null) return;

        tablaCompras.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
            if (nuevo != null) {
                mostrarComprobante(nuevo);
            }
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
                ? compra.getEvento().getRecinto().getNombre()
                : "—");
        lblComprobanteTotal.setText("$" + (int) compra.calcularTotal());

        String color = switch (compra.getEstadoCompra().toString()) {
            case "PAGADA", "CONFIRMADA" -> "-fx-text-fill: #2ecc71;";
            case "CANCELADA" -> "-fx-text-fill: #e74c3c;";
            case "REEMBOLSADA" -> "-fx-text-fill: #9b59b6;";
            default -> "-fx-text-fill: #f1c40f;";
        };

        lblComprobanteEstado.setStyle("-fx-font-weight: bold; " + color);

        if (tablaEntradasCompra != null) {
            colEntradaId.setCellValueFactory(d ->
                    new SimpleStringProperty(String.valueOf(compra.getEntradas().indexOf(d.getValue()) + 1)));
            colEntradaZona.setCellValueFactory(d -> {
                if (d.getValue() instanceof Entrada entrada && entrada.getZona() != null) {
                    return new SimpleStringProperty(entrada.getZona().getNombre());
                }
                return new SimpleStringProperty("Entrada con servicios");
            });
            colEntradaAsiento.setCellValueFactory(d -> {
                if (d.getValue() instanceof Entrada entrada && entrada.getAsiento() != null) {
                    return new SimpleStringProperty(
                            entrada.getAsiento().getFila() + "-" + entrada.getAsiento().getNumero()
                    );
                }
                return new SimpleStringProperty("—");
            });
            colEntradaCosto.setCellValueFactory(d ->
                    new SimpleStringProperty("$" + (int) d.getValue().getCosto()));
            colEntradaEstado.setCellValueFactory(d -> {
                if (d.getValue() instanceof Entrada entrada) {
                    return new SimpleStringProperty(entrada.getEstadoEntrada().toString());
                }
                return new SimpleStringProperty("—");
            });

            tablaEntradasCompra.setItems(FXCollections.observableArrayList(compra.getEntradas()));
        }
    }

    @FXML
    private void cancelarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();

        try {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Cancelar compra");
            conf.setHeaderText("¿Cancelar la compra seleccionada?");
            conf.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> res = conf.showAndWait();

            if (res.isEmpty() || res.get() != ButtonType.OK) {
                return;
            }

            boolean ok = compraController.cancelarCompra(compra);

            cargarHistorial(null);
            actualizarStats();

            if (ok && eventoSeleccionado != null) {
                mostrarMapaAsientos(eventoSeleccionado);
            }

            setMsg(ok ? "Compra cancelada." : "No se puede cancelar esta compra.", !ok);
        } catch (IllegalArgumentException e) {
            setMsg(e.getMessage(), true);
        }
    }

    @FXML
    private void exportarCSV() {
        reporteController.exportarVentasCSV("reporte_usuario.csv");
        setMsg("📄 CSV exportado como reporte_usuario.csv", false);
    }

    @FXML
    private void exportarPDF() {
        reporteController.exportarVentasPDF("reporte_usuario.pdf");
        setMsg("📑 PDF exportado como reporte_usuario.pdf", false);
    }

    @FXML
    private void guardarPerfil() {
        try {
            usuarioController.actualizarPerfil(
                    usuario,
                    txtNombre.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtTelefono.getText().trim()
            );

            lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
            lblMensajePerfil.setText("Perfil actualizado correctamente.");
            lblMensajePerfil.setStyle("-fx-text-fill: #2ecc71;");
        } catch (IllegalArgumentException e) {
            lblMensajePerfil.setText(e.getMessage());
            lblMensajePerfil.setStyle("-fx-text-fill: #e74c3c;");
        }
    }

    private void cargarMetodosPago() {
        if (tablaMetodosPago == null) return;

        colPagoTipo.setCellValueFactory(d -> {
            PagoStrategy pago = d.getValue();
            String tipo = pago instanceof PagoTarjeta
                    ? "Tarjeta"
                    : pago instanceof PagoPSE
                    ? "PSE"
                    : "Efectivo";

            return new SimpleStringProperty(tipo);
        });

        colPagoDetalle.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().toString()));
        tablaMetodosPago.setItems(FXCollections.observableArrayList(usuario.getMetodosDepago()));
    }

    @FXML
    private void agregarMetodoPago() {
        String tipo = cmbTipoPago != null ? cmbTipoPago.getValue() : "";
        String dato = txtDatoPago != null ? txtDatoPago.getText().trim() : "";

        PagoStrategy nuevoMetodo = usuarioController.crearMetodoPago(tipo, dato, usuario.getNombre());
        usuarioController.agregarMetodoPago(usuario, nuevoMetodo);

        cargarMetodosPago();
        inicializarMetodoPago();

        if (txtDatoPago != null) {
            txtDatoPago.clear();
        }

        if (lblMensajePago != null) {
            lblMensajePago.setText("Método de pago agregado.");
            lblMensajePago.setStyle("-fx-text-fill: #2ecc71;");
        }
    }

    private void actualizarStats() {
        if (lblStatCompras == null) return;

        List<Compra> historial = usuario.getHistorialCompras();

        lblStatCompras.setText(String.valueOf(historial.size()));
        lblStatGastado.setText("$" + (int) historial.stream().mapToDouble(Compra::calcularTotal).sum());
        lblStatEventos.setText(String.valueOf(
                historial.stream().map(c -> c.getEvento().getNombre()).distinct().count()
        ));
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("login-view.fxml")
            );

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
        lblMensajeCompra.setStyle(error
                ? "-fx-text-fill: #e74c3c;"
                : "-fx-text-fill: #2ecc71;");
    }
}