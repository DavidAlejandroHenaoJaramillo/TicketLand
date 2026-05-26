package viewController;

import com.example.ticketland.HelloApplication;
import controller.ReporteController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import state.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import java.util.LinkedHashMap;
import java.util.Map;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AdminViewController implements Initializable {

    @FXML private Label lblAdminStats;

    @FXML private Label kpiTotalVentas;
    @FXML private Label kpiCompras;
    @FXML private Label kpiEventos;
    @FXML private Label kpiUsuarios;
    @FXML private Label kpiCancelacion;

    @FXML private BarChart<String, Number> chartVentas;
    @FXML private PieChart chartEstados;
    @FXML private LineChart<String, Number> chartLineas;

    @FXML private TableView<String[]> tablaOcupacion;
    @FXML private TableColumn<String[], String> colOcupEvento;
    @FXML private TableColumn<String[], String> colOcupZona;
    @FXML private TableColumn<String[], String> colOcupTipo;
    @FXML private TableColumn<String[], String> colOcupVendidos;
    @FXML private TableColumn<String[], String> colOcupCapacidad;
    @FXML private TableColumn<String[], String> colOcupPorcentaje;

    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colCiudad;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colCategoria;
    @FXML private TableColumn<Evento, String> colEstado;
    @FXML private Label lblMensajeEvento;

    @FXML private TextField txtNuevoEvNombre;
    @FXML private ComboBox<String> cmbNuevoEvCategoria;
    @FXML private TextField txtNuevoEvCiudad;
    @FXML private DatePicker dpNuevoEvFecha;
    @FXML private ComboBox<Recinto> cmbNuevoEvRecinto;
    @FXML private TextArea txtNuevoEvDescripcion;
    @FXML private TextArea txtNuevoEvPoliticas;
    @FXML private Label lblMensajeCrearEvento;

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colUsuarioId;
    @FXML private TableColumn<Usuario, String> colUsuarioNombre;
    @FXML private TableColumn<Usuario, String> colUsuarioCorreo;
    @FXML private TableColumn<Usuario, String> colUsuarioTelefono;
    @FXML private TableColumn<Usuario, String> colUsuarioCompras;
    @FXML private TableColumn<Usuario, String> colUsuarioGastado;
    @FXML private Label lblMensajeUsuario;
    @FXML private TextField txtNuevoUsNombre;
    @FXML private TextField txtNuevoUsCorreo;
    @FXML private TextField txtNuevoUsTelefono;

    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraId;
    @FXML private TableColumn<Compra, String> colCompraUsuario;
    @FXML private TableColumn<Compra, String> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraFecha;
    @FXML private TableColumn<Compra, String> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraMetodo;
    @FXML private TableColumn<Compra, String> colCompraEstado;
    @FXML private Label lblMensajeCompra;

    @FXML private TableView<Recinto> tablaRecintos;
    @FXML private TableColumn<Recinto, String> colRecintoNombre;
    @FXML private TableColumn<Recinto, String> colRecintoCiudad;
    @FXML private TableColumn<Recinto, String> colRecintoDireccion;
    @FXML private TextField txtRecintoNombre;
    @FXML private TextField txtRecintoDireccion;
    @FXML private TextField txtRecintoCiudad;
    @FXML private Label lblMensajeRecinto;

    @FXML private ComboBox<Evento> cmbEventoZonas;
    @FXML private TableView<Zona> tablaZonasAdmin;
    @FXML private TableColumn<Zona, String> colZonaAdminNombre;
    @FXML private TableColumn<Zona, String> colZonaAdminTipo;
    @FXML private TableColumn<Zona, String> colZonaAdminCapacidad;
    @FXML private TableColumn<Zona, String> colZonaAdminPrecio;
    @FXML private TableColumn<Zona, String> colZonaAdminOcupacion;

    @FXML private TableView<Asiento> tablaAsientos;
    @FXML private TableColumn<Asiento, String> colAsientoId;
    @FXML private TableColumn<Asiento, String> colAsientoFila;
    @FXML private TableColumn<Asiento, String> colAsientoNumero;
    @FXML private TableColumn<Asiento, String> colAsientoEstado;
    @FXML private ComboBox<Evento> cmbEventoAsientos;
    @FXML private ComboBox<Zona> cmbZonaAsientos;
    @FXML private Label lblMensajeAsiento;

    @FXML private ComboBox<Incidencia.Tipo> cmbTipoIncidencia;
    @FXML private TextArea txtDescripcionIncidencia;
    @FXML private TextField txtEntidadAfectada;
    @FXML private Label lblMensajeIncidencia;
    @FXML private TableView<Incidencia> tablaIncidencias;
    @FXML private TableColumn<Incidencia, String> colIncidenciaId;
    @FXML private TableColumn<Incidencia, String> colIncidenciaTipo;
    @FXML private TableColumn<Incidencia, String> colIncidenciaDesc;
    @FXML private TableColumn<Incidencia, String> colIncidenciaFecha;
    @FXML private TableColumn<Incidencia, String> colIncidenciaEntidad;
    @FXML private ComboBox<Incidencia.Tipo> cmbFiltroTipo;
    @FXML private DatePicker dpFiltroDesde;
    @FXML private DatePicker dpFiltroHasta;
    @FXML private GridPane gridAsientosAdmin;

    private final TicketLand sistema = TicketLand.getInstance();
    private final controller.AdminController adminController = new controller.AdminController();
    private final ReporteController reporteController = new ReporteController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarCombosCrearEvento();
        cargarEventos();
        cargarUsuarios();
        cargarCompras();
        actualizarMetricas();
        cargarTiposIncidencia();
        cargarIncidencias();
        cargarRecintos();
        cargarCombosAsientos();
        cargarZonasAdmin();

        if (lblAdminStats != null) {
            lblAdminStats.setText("Sistema: " + sistema.getEventos().size() + " eventos | "
                    + sistema.getUsuarios().size() + " usuarios | "
                    + sistema.getCompras().size() + " compras");
        }
    }

    @FXML
    public void actualizarMetricas() {
        actualizarKPIs();
        actualizarBarChart();
        actualizarPieChart();
        actualizarLineChart();
        actualizarTablaOcupacion();
    }

    private void actualizarKPIs() {
        double totalVentas = sistema.getCompras().stream().mapToDouble(Compra::calcularTotal).sum();
        long activos = sistema.getEventos().stream()
                .filter(e -> e.getEstado() == EstadoEvento.ACTIVO)
                .count();
        long canceladas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraCancelada)
                .count();

        double tasaCancelacion = sistema.getCompras().isEmpty()
                ? 0
                : 100.0 * canceladas / sistema.getCompras().size();

        if (kpiTotalVentas != null) kpiTotalVentas.setText("$" + (int) totalVentas);
        if (kpiCompras != null) kpiCompras.setText(String.valueOf(sistema.getCompras().size()));
        if (kpiEventos != null) kpiEventos.setText(String.valueOf(activos));
        if (kpiUsuarios != null) kpiUsuarios.setText(String.valueOf(sistema.getUsuarios().size()));
        if (kpiCancelacion != null) kpiCancelacion.setText(String.format("%.0f%%", tasaCancelacion));

        if (lblAdminStats != null) {
            lblAdminStats.setText("Sistema: " + sistema.getEventos().size() + " eventos | "
                    + sistema.getUsuarios().size() + " usuarios | "
                    + sistema.getCompras().size() + " compras");
        }
    }

    private void actualizarBarChart() {
        if (chartVentas == null) return;

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ventas ($)");

        Map<String, Double> ventasPorEvento = new LinkedHashMap<>();

        for (Compra compra : sistema.getCompras()) {
            ventasPorEvento.merge(compra.getEvento().getNombre(), compra.calcularTotal(), Double::sum);
        }

        ventasPorEvento.forEach((evento, total) ->
                serie.getData().add(new XYChart.Data<>(evento, total))
        );

        chartVentas.getData().clear();
        chartVentas.getData().add(serie);
    }

    private void actualizarPieChart() {
        if (chartEstados == null) return;

        long pagadas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraPagada)
                .count();
        long confirmadas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraConfirmada)
                .count();
        long canceladas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraCancelada)
                .count();
        long reembolsadas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraReembolsada)
                .count();
        long incidencias = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraIncidencia)
                .count();
        long creadas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraCreada)
                .count();

        chartEstados.getData().clear();

        if (pagadas > 0) chartEstados.getData().add(new PieChart.Data("Pagadas", pagadas));
        if (confirmadas > 0) chartEstados.getData().add(new PieChart.Data("Confirmadas", confirmadas));
        if (canceladas > 0) chartEstados.getData().add(new PieChart.Data("Canceladas", canceladas));
        if (reembolsadas > 0) chartEstados.getData().add(new PieChart.Data("Reembolsadas", reembolsadas));
        if (incidencias > 0) chartEstados.getData().add(new PieChart.Data("Incidencias", incidencias));
        if (creadas > 0) chartEstados.getData().add(new PieChart.Data("Creadas", creadas));
    }

    private void actualizarLineChart() {
        if (chartLineas == null) return;

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ingresos por fecha");

        Map<String, Double> ingresosPorFecha = new LinkedHashMap<>();

        sistema.getCompras().stream()
                .sorted(Comparator.comparing(Compra::getFechaCompra))
                .forEach(c -> ingresosPorFecha.merge(
                        c.getFechaCompra().toString(),
                        c.calcularTotal(),
                        Double::sum
                ));

        ingresosPorFecha.forEach((fecha, total) ->
                serie.getData().add(new XYChart.Data<>(fecha, total))
        );

        chartLineas.getData().clear();
        chartLineas.getData().add(serie);
    }

    private void actualizarTablaOcupacion() {
        if (tablaOcupacion == null) return;

        colOcupEvento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[0]));
        colOcupZona.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[1]));
        colOcupTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[2]));
        colOcupVendidos.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[3]));
        colOcupCapacidad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[4]));
        colOcupPorcentaje.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[5]));

        List<String[]> filas = new ArrayList<>();

        for (Evento evento : sistema.getEventos()) {
            if (evento.getRecinto() == null) continue;

            for (Zona zona : evento.getRecinto().getZonas()) {
                int vendidos = zona.calcularOcupacion();
                int capacidad = zona.getCapacidad();
                double porcentaje = capacidad > 0 ? 100.0 * vendidos / capacidad : 0;

                filas.add(new String[]{
                        evento.getNombre(),
                        zona.getNombre(),
                        zona.getTipoZona().toString(),
                        String.valueOf(vendidos),
                        String.valueOf(capacidad),
                        String.format("%.1f%%", porcentaje)
                });
            }
        }

        tablaOcupacion.setItems(FXCollections.observableArrayList(filas));
    }

    private void inicializarCombosCrearEvento() {
        if (cmbNuevoEvCategoria != null) {
            cmbNuevoEvCategoria.setItems(FXCollections.observableArrayList(
                    "Concierto", "Teatro", "Conferencia", "Festival", "Deportes", "Otro"
            ));
        }

        if (cmbNuevoEvRecinto != null) {
            List<Recinto> recintos = sistema.getEventos().stream()
                    .filter(e -> e.getRecinto() != null)
                    .map(Evento::getRecinto)
                    .distinct()
                    .collect(Collectors.toList());

            cmbNuevoEvRecinto.setItems(FXCollections.observableArrayList(recintos));
        }
    }

    private void cargarEventos() {
        if (tablaEventos == null) return;

        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));

        if (colCategoria != null) {
            colCategoria.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategoria()));
        }

        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));

        tablaEventos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Evento item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                    return;
                }

                switch (item.getEstado().toString()) {
                    case "ACTIVO" -> setStyle("-fx-background-color: #061406;");
                    case "PAUSADO" -> setStyle("-fx-background-color: #141000;");
                    case "CANCELADO" -> setStyle("-fx-background-color: #140606;");
                    default -> setStyle("");
                }
            }
        });

        tablaEventos.setItems(FXCollections.observableArrayList(adminController.obtenerEventos()));
    }

    @FXML
    private void activarEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();

        try {
            adminController.activarEvento(evento);
            cargarEventos();
            actualizarKPIs();
            setMsgEvento("✅ Evento \"" + evento.getNombre() + "\" activado.", true);
        } catch (IllegalArgumentException e) {
            setMsgEvento(e.getMessage(), false);
        }
    }

    @FXML
    private void pausarEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();

        try {
            adminController.pausarEvento(evento);
            cargarEventos();
            actualizarKPIs();
            setMsgEvento("⏸ Evento \"" + evento.getNombre() + "\" pausado.", true);
        } catch (IllegalArgumentException e) {
            setMsgEvento(e.getMessage(), false);
        }
    }

    @FXML
    private void cancelarEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();

        try {
            adminController.cancelarEvento(evento);
            cargarEventos();
            actualizarKPIs();
            setMsgEvento("❌ Evento \"" + evento.getNombre() + "\" cancelado.", true);
        } catch (IllegalArgumentException e) {
            setMsgEvento(e.getMessage(), false);
        }
    }

    @FXML
    private void finalizarEvento() {
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();

        try {
            adminController.finalizarEvento(evento);
            cargarEventos();
            actualizarKPIs();
            setMsgEvento("✔ Evento \"" + evento.getNombre() + "\" finalizado.", true);
        } catch (IllegalArgumentException e) {
            setMsgEvento(e.getMessage(), false);
        }
    }

    @FXML
    private void crearEvento() {
        try {
            Evento nuevo = adminController.crearEvento(
                    txtNuevoEvNombre.getText().trim(),
                    cmbNuevoEvCategoria != null ? cmbNuevoEvCategoria.getValue() : "",
                    txtNuevoEvCiudad != null ? txtNuevoEvCiudad.getText().trim() : "",
                    dpNuevoEvFecha != null ? dpNuevoEvFecha.getValue() : null,
                    cmbNuevoEvRecinto != null ? cmbNuevoEvRecinto.getValue() : null,
                    txtNuevoEvDescripcion != null ? txtNuevoEvDescripcion.getText().trim() : "",
                    txtNuevoEvPoliticas != null ? txtNuevoEvPoliticas.getText().trim() : ""
            );

            cargarEventos();
            actualizarKPIs();
            inicializarCombosCrearEvento();

            txtNuevoEvNombre.clear();
            if (txtNuevoEvCiudad != null) txtNuevoEvCiudad.clear();
            if (dpNuevoEvFecha != null) dpNuevoEvFecha.setValue(null);
            if (txtNuevoEvDescripcion != null) txtNuevoEvDescripcion.clear();
            if (txtNuevoEvPoliticas != null) txtNuevoEvPoliticas.clear();

            setMsgCrearEvento("✅ Evento \"" + nuevo.getNombre() + "\" creado exitosamente.");
        } catch (IllegalArgumentException e) {
            setMsgCrearEvento("❌ " + e.getMessage());
        }
    }

    private void setMsgEvento(String msg, boolean ok) {
        if (lblMensajeEvento == null) return;

        lblMensajeEvento.setText(msg);
        lblMensajeEvento.setStyle(ok ? "-fx-text-fill: #2ecc71;" : "-fx-text-fill: #e74c3c;");
    }

    private void setMsgCrearEvento(String msg) {
        if (lblMensajeCrearEvento == null) return;

        lblMensajeCrearEvento.setText(msg);
        lblMensajeCrearEvento.setStyle(msg.startsWith("✅")
                ? "-fx-text-fill: #2ecc71;"
                : "-fx-text-fill: #e74c3c;");
    }

    private void cargarUsuarios() {
        if (tablaUsuarios == null) return;

        if (colUsuarioId != null) {
            colUsuarioId.setCellValueFactory(d ->
                    new SimpleStringProperty(String.valueOf(d.getValue().getId())));
        }

        colUsuarioNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colUsuarioCorreo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCorreo()));
        colUsuarioTelefono.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));

        if (colUsuarioCompras != null) {
            colUsuarioCompras.setCellValueFactory(d ->
                    new SimpleStringProperty(String.valueOf(d.getValue().getHistorialCompras().size())));
        }

        if (colUsuarioGastado != null) {
            colUsuarioGastado.setCellValueFactory(d -> {
                double total = d.getValue().getHistorialCompras().stream()
                        .mapToDouble(Compra::calcularTotal)
                        .sum();

                return new SimpleStringProperty("$" + (int) total);
            });
        }

        tablaUsuarios.setItems(FXCollections.observableArrayList(adminController.obtenerUsuarios()));
    }

    @FXML
    private void crearUsuario() {
        try {
            Usuario usuario = adminController.crearUsuario(
                    txtNuevoUsNombre.getText().trim(),
                    txtNuevoUsCorreo != null ? txtNuevoUsCorreo.getText().trim() : "",
                    txtNuevoUsTelefono != null ? txtNuevoUsTelefono.getText().trim() : ""
            );

            cargarUsuarios();
            actualizarKPIs();

            txtNuevoUsNombre.clear();
            if (txtNuevoUsCorreo != null) txtNuevoUsCorreo.clear();
            if (txtNuevoUsTelefono != null) txtNuevoUsTelefono.clear();

            setMsg(lblMensajeUsuario, "✅ Usuario \"" + usuario.getNombre() + "\" creado.", true);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeUsuario, "❌ " + e.getMessage(), false);
        }
    }

    @FXML
    private void eliminarUsuario() {
        if (tablaUsuarios == null) return;

        Usuario usuario = tablaUsuarios.getSelectionModel().getSelectedItem();

        try {
            adminController.eliminarUsuario(usuario);
            cargarUsuarios();
            actualizarKPIs();
            setMsg(lblMensajeUsuario, "🗑 Usuario \"" + usuario.getNombre() + "\" eliminado.", true);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeUsuario, e.getMessage(), false);
        }
    }

    private void cargarCompras() {
        if (tablaCompras == null) return;

        if (colCompraId != null) {
            colCompraId.setCellValueFactory(d ->
                    new SimpleStringProperty(String.valueOf(d.getValue().getIdCompra())));
        }

        colCompraUsuario.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getUsuario().getNombre()));
        colCompraEvento.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEvento().getNombre()));

        if (colCompraFecha != null) {
            colCompraFecha.setCellValueFactory(d ->
                    new SimpleStringProperty(d.getValue().getFechaCompra().toString()));
        }

        colCompraTotal.setCellValueFactory(d ->
                new SimpleStringProperty("$" + (int) d.getValue().calcularTotal()));

        if (colCompraMetodo != null) {
            colCompraMetodo.setCellValueFactory(d ->
                    new SimpleStringProperty(d.getValue().getMetodoPago() != null
                            ? d.getValue().getMetodoPago().toString()
                            : "—"));
        }

        colCompraEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstadoCompra().toString()));

        tablaCompras.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Compra item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                    return;
                }

                switch (item.getEstadoCompra().toString()) {
                    case "PAGADA" -> setStyle("-fx-background-color: #061406;");
                    case "CONFIRMADA" -> setStyle("-fx-background-color: #060a14;");
                    case "CANCELADA" -> setStyle("-fx-background-color: #140606;");
                    case "REEMBOLSADA" -> setStyle("-fx-background-color: #100614;");
                    default -> setStyle("");
                }
            }
        });

        tablaCompras.setItems(FXCollections.observableArrayList(adminController.obtenerCompras()));
    }

    @FXML
    private void confirmarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();

        try {
            boolean ok = adminController.confirmarCompra(compra);
            cargarCompras();
            actualizarMetricas();
            setMsg(lblMensajeCompra, ok ? "✅ Compra confirmada." : "❌ Solo se puede confirmar compras PAGADAS.", ok);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeCompra, e.getMessage(), false);
        }
    }

    @FXML
    private void cancelarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();

        try {
            boolean ok = adminController.cancelarCompra(compra);
            cargarCompras();
            actualizarMetricas();
            setMsg(lblMensajeCompra, ok ? "❌ Compra cancelada." : "❌ No se puede cancelar.", ok);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeCompra, e.getMessage(), false);
        }
    }

    @FXML
    private void reembolsarCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();

        try {
            boolean ok = adminController.reembolsarCompra(compra);
            cargarCompras();
            actualizarMetricas();
            setMsg(lblMensajeCompra, ok ? "💰 Reembolso procesado." : "❌ Solo PAGADAS o CONFIRMADAS.", ok);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeCompra, e.getMessage(), false);
        }
    }

    @FXML
    private void marcarIncidenciaCompra() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();

        try {
            boolean ok = adminController.marcarIncidenciaCompra(compra);
            cargarCompras();
            actualizarMetricas();
            setMsg(lblMensajeCompra, ok ? "⚠ Compra marcada como incidencia." : "❌ No aplicable.", ok);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeCompra, e.getMessage(), false);
        }
    }

    @FXML
    private void exportarCSV() {
        reporteController.exportarVentasCSV("reporte_admin.csv");
        setMsg(lblMensajeCompra, "📄 CSV exportado como reporte_admin.csv", true);
    }

    @FXML
    private void exportarPDF() {
        reporteController.exportarVentasPDF("reporte_admin.pdf");
        setMsg(lblMensajeCompra, "📑 PDF exportado como reporte_admin.pdf", true);
    }

    private void cargarRecintos() {
        if (tablaRecintos == null) return;

        colRecintoNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colRecintoCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colRecintoDireccion.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDireccion()));

        List<Recinto> recintos = sistema.getEventos().stream()
                .filter(e -> e.getRecinto() != null)
                .map(Evento::getRecinto)
                .distinct()
                .collect(Collectors.toList());

        tablaRecintos.setItems(FXCollections.observableArrayList(recintos));
    }

    @FXML
    private void crearRecinto() {
        if (tablaRecintos == null) return;

        String nombre = txtRecintoNombre.getText().trim();
        String direccion = txtRecintoDireccion.getText().trim();
        String ciudad = txtRecintoCiudad.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || ciudad.isEmpty()) {
            setMsg(lblMensajeRecinto, "❌ Completa todos los campos.", false);
            return;
        }

        txtRecintoNombre.clear();
        txtRecintoDireccion.clear();
        txtRecintoCiudad.clear();

        setMsg(lblMensajeRecinto, "✅ Recinto \"" + nombre + "\" registrado.", true);
    }

    private void cargarZonasAdmin() {
        if (cmbEventoZonas == null) return;

        cmbEventoZonas.setItems(FXCollections.observableArrayList(sistema.getEventos()));

        cmbEventoZonas.setOnAction(e -> {
            Evento evento = cmbEventoZonas.getValue();

            if (evento != null && evento.getRecinto() != null && tablaZonasAdmin != null) {
                tablaZonasAdmin.setItems(FXCollections.observableArrayList(evento.getRecinto().getZonas()));
            }
        });

        if (colZonaAdminNombre != null) {
            colZonaAdminNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
            colZonaAdminTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipoZona().toString()));
            colZonaAdminCapacidad.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCapacidad())));
            colZonaAdminPrecio.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().getPrecioBase()));
            colZonaAdminOcupacion.setCellValueFactory(d -> {
                int ocupacion = d.getValue().calcularOcupacion();
                int capacidad = d.getValue().getCapacidad();

                return new SimpleStringProperty(
                        ocupacion + "/" + capacidad + " ("
                                + (capacidad > 0 ? String.format("%.0f", 100.0 * ocupacion / capacidad) : 0)
                                + "%)"
                );
            });
        }
    }

    private void cargarCombosAsientos() {
        if (cmbEventoAsientos == null) return;

        cmbEventoAsientos.setItems(FXCollections.observableArrayList(sistema.getEventos()));

        cmbEventoAsientos.setOnAction(e -> {
            Evento evento = cmbEventoAsientos.getValue();

            if (evento != null && evento.getRecinto() != null) {
                cmbZonaAsientos.setItems(FXCollections.observableArrayList(evento.getRecinto().getZonas()));
                pintarAsientosAdminPorEvento(evento);
            }
        });

        cmbZonaAsientos.setOnAction(e -> cargarAsientosDeZona());
    }

    private void cargarAsientosDeZona() {
        if (cmbZonaAsientos == null || tablaAsientos == null) return;

        Zona zona = cmbZonaAsientos.getValue();
        if (zona == null) return;

        colAsientoId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getIdAsiento())));
        colAsientoFila.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFila()));
        colAsientoNumero.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getNumero())));
        colAsientoEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstado().toString()));

        tablaAsientos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Asiento item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                    return;
                }

                switch (item.getEstado().toString()) {
                    case "DISPONIBLE" -> setStyle("-fx-background-color: #061406;");
                    case "RESERVADO" -> setStyle("-fx-background-color: #141000;");
                    case "VENDIDO" -> setStyle("-fx-background-color: #140606;");
                    case "BLOQUEADO" -> setStyle("-fx-background-color: #0a0a14;");
                    default -> setStyle("");
                }
            }
        });

        tablaAsientos.setItems(FXCollections.observableArrayList(zona.getAsientos()));
        pintarAsientosAdmin(zona);
    }

    private void pintarAsientosAdmin(Zona zona) {
        if (gridAsientosAdmin == null || zona == null) return;

        gridAsientosAdmin.getChildren().clear();

        Map<String, Integer> filas = new LinkedHashMap<>();
        for (Asiento asiento : zona.getAsientos()) {
            filas.putIfAbsent(asiento.getFila(), filas.size());
        }

        for (Asiento asiento : zona.getAsientos()) {
            ToggleButton btn = crearBotonAsientoAdmin(asiento);

            btn.setOnAction(e -> {
                if (tablaAsientos != null) {
                    tablaAsientos.getSelectionModel().select(asiento);
                    tablaAsientos.scrollTo(asiento);
                }

                limpiarSeleccionAdmin();
                btn.setStyle(estiloAsientoAdmin(asiento) + "-fx-border-color: #0057ff; -fx-border-width: 3;");
            });

            gridAsientosAdmin.add(btn, asiento.getNumero(), filas.get(asiento.getFila()));
        }
    }

    private void pintarAsientosAdminPorEvento(Evento evento) {
        if (gridAsientosAdmin == null || evento == null || evento.getRecinto() == null) return;

        gridAsientosAdmin.getChildren().clear();

        int filaBase = 0;

        for (Zona zona : evento.getRecinto().getZonas()) {
            Label lblZona = new Label(zona.getNombre() + " - " + zona.getTipoZona());
            lblZona.setStyle("-fx-font-weight: bold; -fx-text-fill: #0057ff; -fx-padding: 10 0 4 0;");

            gridAsientosAdmin.add(lblZona, 0, filaBase, 12, 1);
            filaBase++;

            Map<String, Integer> filas = new LinkedHashMap<>();

            for (Asiento asiento : zona.getAsientos()) {
                filas.putIfAbsent(asiento.getFila(), filas.size());
            }

            for (Asiento asiento : zona.getAsientos()) {
                ToggleButton btn = crearBotonAsientoAdmin(asiento);
                btn.setOnAction(e -> {
                    if (cmbZonaAsientos != null) {
                        cmbZonaAsientos.getSelectionModel().select(zona);
                    }
                    if (tablaAsientos != null) {
                        tablaAsientos.setItems(FXCollections.observableArrayList(zona.getAsientos()));
                        tablaAsientos.getSelectionModel().select(asiento);
                        tablaAsientos.scrollTo(asiento);
                    }
                    limpiarSeleccionAdmin();
                    btn.setStyle(estiloAsientoAdmin(asiento) + "-fx-border-color: #0057ff; -fx-border-width: 3;");
                });
                int fila = filaBase + filas.get(asiento.getFila());
                int columna = asiento.getNumero();
                gridAsientosAdmin.add(btn, columna, fila);
            }

            filaBase += filas.size() + 1;
        }
    }

    private ToggleButton crearBotonAsientoAdmin(Asiento asiento) {
        ToggleButton btn = new ToggleButton(asiento.getFila() + asiento.getNumero());

        btn.setUserData(asiento);
        btn.setMinSize(42, 36);
        btn.setPrefSize(42, 36);
        btn.setMaxSize(42, 36);
        btn.setAlignment(Pos.CENTER);
        btn.setStyle(estiloAsientoAdmin(asiento));

        return btn;
    }

    private String estiloAsientoAdmin(Asiento asiento) {
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

    private void limpiarSeleccionAdmin() {
        if (gridAsientosAdmin == null) return;

        for (Node node : gridAsientosAdmin.getChildren()) {
            if (node instanceof ToggleButton btn && btn.getUserData() instanceof Asiento asiento) {
                btn.setStyle(estiloAsientoAdmin(asiento));
            }
        }
    }

    @FXML
    private void bloquearAsiento() {
        if (tablaAsientos == null) return;

        Asiento asiento = tablaAsientos.getSelectionModel().getSelectedItem();

        try {
            boolean ok = adminController.bloquearAsiento(asiento);
            cargarAsientosDeZona();
            setMsg(lblMensajeAsiento, ok ? "🔒 Asiento bloqueado." : "❌ Solo disponibles se pueden bloquear.", ok);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeAsiento, e.getMessage(), false);
        }
    }

    @FXML
    private void liberarAsiento() {
        if (tablaAsientos == null) return;

        Asiento asiento = tablaAsientos.getSelectionModel().getSelectedItem();

        try {
            boolean ok = adminController.liberarAsiento(asiento);
            cargarAsientosDeZona();
            setMsg(lblMensajeAsiento, ok ? "🔓 Asiento liberado." : "❌ No se puede liberar.", ok);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeAsiento, e.getMessage(), false);
        }
    }

    private void cargarTiposIncidencia() {
        if (cmbTipoIncidencia != null) {
            cmbTipoIncidencia.getItems().setAll(Incidencia.Tipo.values());
            cmbTipoIncidencia.getSelectionModel().selectFirst();
        }

        if (cmbFiltroTipo != null) {
            cmbFiltroTipo.getItems().clear();
            cmbFiltroTipo.getItems().add(null);
            cmbFiltroTipo.getItems().addAll(Incidencia.Tipo.values());
            cmbFiltroTipo.getSelectionModel().selectFirst();
        }
    }

    private void cargarIncidencias() {
        if (tablaIncidencias == null) return;

        colIncidenciaId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getIdIncidencia())));
        colIncidenciaTipo.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getTipo().toString()));
        colIncidenciaDesc.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDescripcion()));
        colIncidenciaFecha.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFecha().toString()));
        colIncidenciaEntidad.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEntidadAfectada()));

        tablaIncidencias.setItems(FXCollections.observableArrayList(adminController.obtenerIncidencias()));
    }

    @FXML
    private void registrarIncidencia() {
        try {
            adminController.registrarIncidencia(
                    cmbTipoIncidencia != null ? cmbTipoIncidencia.getValue() : null,
                    txtDescripcionIncidencia != null ? txtDescripcionIncidencia.getText().trim() : "",
                    txtEntidadAfectada != null ? txtEntidadAfectada.getText().trim() : ""
            );

            if (txtDescripcionIncidencia != null) txtDescripcionIncidencia.clear();
            if (txtEntidadAfectada != null) txtEntidadAfectada.clear();

            cargarIncidencias();
            setMsg(lblMensajeIncidencia, "⚠ Incidencia registrada correctamente.", true);
        } catch (IllegalArgumentException e) {
            setMsg(lblMensajeIncidencia, "❌ " + e.getMessage(), false);
        }
    }

    @FXML
    private void filtrarIncidencias() {
        Incidencia.Tipo tipo = cmbFiltroTipo != null ? cmbFiltroTipo.getValue() : null;
        LocalDate desde = dpFiltroDesde != null ? dpFiltroDesde.getValue() : null;
        LocalDate hasta = dpFiltroHasta != null ? dpFiltroHasta.getValue() : null;

        List<Incidencia> resultado = adminController.buscarIncidencias(tipo, desde, hasta);

        tablaIncidencias.setItems(FXCollections.observableArrayList(resultado));
        setMsg(lblMensajeIncidencia, "🔍 Filtro aplicado: " + resultado.size() + " resultado(s).", true);
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("login-view.fxml")
            );

            Stage stage = (Stage) tablaEventos.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 580, 450));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            if (lblMensajeCompra != null) {
                lblMensajeCompra.setText("Error al cerrar sesión.");
            }
        }
    }

    private void setMsg(Label label, String msg, boolean ok) {
        if (label == null) return;

        label.setText(msg);
        label.setStyle(ok ? "-fx-text-fill: #2ecc71;" : "-fx-text-fill: #e74c3c;");
    }
}