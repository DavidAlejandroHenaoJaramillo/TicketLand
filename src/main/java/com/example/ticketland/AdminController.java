package com.example.ticketland;

import builder.Compra;
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
import model.GeneradorReporte;
import state.*;
import factory.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AdminController implements Initializable {

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

    private TicketLand sistema = TicketLand.getInstance();

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
        if (lblAdminStats != null)
            lblAdminStats.setText("Sistema: " + sistema.getEventos().size() + " eventos | "
                    + sistema.getUsuarios().size() + " usuarios | "
                    + sistema.getCompras().size() + " compras");
    }

    @FXML public void actualizarMetricas() {
        actualizarKPIs();
        actualizarBarChart();
        actualizarPieChart();
        actualizarLineChart();
        actualizarTablaOcupacion();
    }

    private void actualizarKPIs() {
        double totalVentas = sistema.getCompras().stream().mapToDouble(Compra::calcularTotal).sum();
        long activos   = sistema.getEventos().stream().filter(e -> e.getEstado() == EstadoEvento.ACTIVO).count();
        long canceladas= sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraCancelada).count();
        double tasaCanc= sistema.getCompras().isEmpty() ? 0 : (100.0 * canceladas / sistema.getCompras().size());
        if (kpiTotalVentas != null) kpiTotalVentas.setText("$" + (int) totalVentas);
        if (kpiCompras != null)     kpiCompras.setText(String.valueOf(sistema.getCompras().size()));
        if (kpiEventos != null)     kpiEventos.setText(String.valueOf(activos));
        if (kpiUsuarios != null)    kpiUsuarios.setText(String.valueOf(sistema.getUsuarios().size()));
        if (kpiCancelacion != null) kpiCancelacion.setText(String.format("%.0f%%", tasaCanc));
        if (lblAdminStats != null)
            lblAdminStats.setText("Sistema: " + sistema.getEventos().size() + " eventos | "
                    + sistema.getUsuarios().size() + " usuarios | "
                    + sistema.getCompras().size() + " compras");
    }

    private void actualizarBarChart() {
        if (chartVentas == null) return;
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ventas ($)");
        Map<String, Double> mapa = new LinkedHashMap<>();
        for (Compra c : sistema.getCompras()) mapa.merge(c.getEvento().getNombre(), c.calcularTotal(), Double::sum);
        mapa.forEach((ev, total) -> serie.getData().add(new XYChart.Data<>(ev, total)));
        chartVentas.getData().clear();
        chartVentas.getData().add(serie);
    }

    private void actualizarPieChart() {
        if (chartEstados == null) return;
        long pagadas    = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraPagada).count();
        long confirmadas= sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraConfirmada).count();
        long canceladas = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraCancelada).count();
        long reembolsadas=sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraReembolsada).count();
        long incidencias= sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraIncidencia).count();
        long creadas    = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraCreada).count();
        chartEstados.getData().clear();
        if (pagadas > 0)      chartEstados.getData().add(new PieChart.Data("Pagadas", pagadas));
        if (confirmadas > 0)  chartEstados.getData().add(new PieChart.Data("Confirmadas", confirmadas));
        if (canceladas > 0)   chartEstados.getData().add(new PieChart.Data("Canceladas", canceladas));
        if (reembolsadas > 0) chartEstados.getData().add(new PieChart.Data("Reembolsadas", reembolsadas));
        if (incidencias > 0)  chartEstados.getData().add(new PieChart.Data("Incidencias", incidencias));
        if (creadas > 0)      chartEstados.getData().add(new PieChart.Data("Creadas", creadas));
    }

    private void actualizarLineChart() {
        if (chartLineas == null) return;
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ingresos por fecha");
        Map<String, Double> mapa = new LinkedHashMap<>();
        sistema.getCompras().stream()
                .sorted(Comparator.comparing(Compra::getFechaCompra))
                .forEach(c -> mapa.merge(c.getFechaCompra().toString(), c.calcularTotal(), Double::sum));
        mapa.forEach((f, v) -> serie.getData().add(new XYChart.Data<>(f, v)));
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
        for (Evento ev : sistema.getEventos()) {
            if (ev.getRecinto() == null) continue;
            for (Zona z : ev.getRecinto().getZonas()) {
                int vendidos = z.calcularOcupacion(), cap = z.getCapacidad();
                double pct = cap > 0 ? (100.0 * vendidos / cap) : 0;
                filas.add(new String[]{ev.getNombre(), z.getNombre(), z.getTipoZona().toString(),
                        String.valueOf(vendidos), String.valueOf(cap), String.format("%.1f%%", pct)});
            }
        }
        tablaOcupacion.setItems(FXCollections.observableArrayList(filas));
    }

    private void inicializarCombosCrearEvento() {
        if (cmbNuevoEvCategoria != null)
            cmbNuevoEvCategoria.setItems(FXCollections.observableArrayList(
                    "Concierto", "Teatro", "Conferencia", "Festival", "Deportes", "Otro"));
        if (cmbNuevoEvRecinto != null) {
            List<Recinto> recintos = sistema.getEventos().stream()
                    .filter(e -> e.getRecinto() != null).map(Evento::getRecinto)
                    .distinct().collect(Collectors.toList());
            cmbNuevoEvRecinto.setItems(FXCollections.observableArrayList(recintos));
        }
    }

    private void cargarEventos() {
        if (tablaEventos == null) return;
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));
        if (colCategoria != null)
            colCategoria.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategoria()));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));
        tablaEventos.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(Evento item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) { setStyle(""); return; }
                switch (item.getEstado().toString()) {
                    case "ACTIVO"    -> setStyle("-fx-background-color: #061406;");
                    case "PAUSADO"   -> setStyle("-fx-background-color: #141000;");
                    case "CANCELADO" -> setStyle("-fx-background-color: #140606;");
                    default          -> setStyle("");
                }
            }
        });
        tablaEventos.setItems(FXCollections.observableArrayList(sistema.getEventos()));
    }

    @FXML private void activarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { setMsgEvento("Selecciona un evento.", false); return; }
        e.activar(); cargarEventos(); actualizarKPIs();
        setMsgEvento("✅ Evento \"" + e.getNombre() + "\" activado.", true);
    }

    @FXML private void pausarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { setMsgEvento("Selecciona un evento.", false); return; }
        e.pausar(); cargarEventos(); actualizarKPIs();
        setMsgEvento("⏸ Evento \"" + e.getNombre() + "\" pausado.", true);
    }

    @FXML private void cancelarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { setMsgEvento("Selecciona un evento.", false); return; }
        e.cancelar(); cargarEventos(); actualizarKPIs();
        setMsgEvento("❌ Evento \"" + e.getNombre() + "\" cancelado.", true);
    }

    @FXML private void finalizarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { setMsgEvento("Selecciona un evento.", false); return; }
        e.finalizar(); cargarEventos(); actualizarKPIs();
        setMsgEvento("✔ Evento \"" + e.getNombre() + "\" finalizado.", true);
    }

    @FXML private void crearEvento() {
        if (txtNuevoEvNombre == null) return;
        String nombre    = txtNuevoEvNombre.getText().trim();
        String categoria = cmbNuevoEvCategoria != null ? cmbNuevoEvCategoria.getValue() : "";
        String ciudad    = txtNuevoEvCiudad != null ? txtNuevoEvCiudad.getText().trim() : "";
        LocalDate fecha  = dpNuevoEvFecha != null ? dpNuevoEvFecha.getValue() : null;
        Recinto recinto  = cmbNuevoEvRecinto != null ? cmbNuevoEvRecinto.getValue() : null;
        String desc      = txtNuevoEvDescripcion != null ? txtNuevoEvDescripcion.getText().trim() : "";
        String politicas = txtNuevoEvPoliticas != null ? txtNuevoEvPoliticas.getText().trim() : "";
        if (nombre.isEmpty() || categoria == null || ciudad.isEmpty() || fecha == null) {
            setMsgCrearEvento("❌ Completa nombre, categoría, ciudad y fecha."); return;
        }
        EventoFactory factory = switch (categoria) {
            case "Concierto" -> new ConciertoFactory(nombre, ciudad, fecha, nombre, "General", recinto);
            case "Teatro"    -> new TeatroFactory(nombre, ciudad, fecha, nombre, "Por definir", recinto);
            default          -> new ConferenciaFactory(nombre, ciudad, fecha, "Por definir", nombre, recinto);
        };
        Evento nuevo = factory.crearEvento();
        if (!desc.isEmpty()) nuevo.setDescripcion(desc);
        if (!politicas.isEmpty()) nuevo.setPoliticas(politicas);
        sistema.agregarEvento(nuevo);
        cargarEventos(); actualizarKPIs(); inicializarCombosCrearEvento();
        txtNuevoEvNombre.clear();
        if (txtNuevoEvCiudad != null) txtNuevoEvCiudad.clear();
        if (dpNuevoEvFecha != null) dpNuevoEvFecha.setValue(null);
        if (txtNuevoEvDescripcion != null) txtNuevoEvDescripcion.clear();
        if (txtNuevoEvPoliticas != null) txtNuevoEvPoliticas.clear();
        setMsgCrearEvento("✅ Evento \"" + nombre + "\" creado exitosamente.");
    }

    private void setMsgEvento(String msg, boolean ok) {
        if (lblMensajeEvento == null) return;
        lblMensajeEvento.setText(msg);
        lblMensajeEvento.setStyle(ok ? "-fx-text-fill: #2ecc71;" : "-fx-text-fill: #e74c3c;");
    }

    private void setMsgCrearEvento(String msg) {
        if (lblMensajeCrearEvento == null) return;
        lblMensajeCrearEvento.setText(msg);
        lblMensajeCrearEvento.setStyle(msg.startsWith("✅") ? "-fx-text-fill: #2ecc71;" : "-fx-text-fill: #e74c3c;");
    }

    private void cargarUsuarios() {
        if (tablaUsuarios == null) return;
        if (colUsuarioId != null)
            colUsuarioId.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getId())));
        colUsuarioNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colUsuarioCorreo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCorreo()));
        colUsuarioTelefono.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));
        if (colUsuarioCompras != null)
            colUsuarioCompras.setCellValueFactory(d ->
                    new SimpleStringProperty(String.valueOf(d.getValue().getHistorialCompras().size())));
        if (colUsuarioGastado != null)
            colUsuarioGastado.setCellValueFactory(d -> {
                double t = d.getValue().getHistorialCompras().stream().mapToDouble(Compra::calcularTotal).sum();
                return new SimpleStringProperty("$" + (int) t);
            });
        tablaUsuarios.setItems(FXCollections.observableArrayList(sistema.getUsuarios()));
    }

    @FXML private void crearUsuario() {
        if (txtNuevoUsNombre == null) return;
        String nombre   = txtNuevoUsNombre.getText().trim();
        String correo   = txtNuevoUsCorreo != null ? txtNuevoUsCorreo.getText().trim() : "";
        String telefono = txtNuevoUsTelefono != null ? txtNuevoUsTelefono.getText().trim() : "";
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            setMsg(lblMensajeUsuario, "❌ Completa todos los campos.", false); return;
        }
        sistema.agregarUsuario(new Usuario(sistema.getUsuarios().size() + 1, nombre, correo, telefono));
        cargarUsuarios(); actualizarKPIs();
        txtNuevoUsNombre.clear();
        if (txtNuevoUsCorreo != null) txtNuevoUsCorreo.clear();
        if (txtNuevoUsTelefono != null) txtNuevoUsTelefono.clear();
        setMsg(lblMensajeUsuario, "✅ Usuario \"" + nombre + "\" creado.", true);
    }

    @FXML private void eliminarUsuario() {
        if (tablaUsuarios == null) return;
        Usuario u = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (u == null) { setMsg(lblMensajeUsuario, "Selecciona un usuario.", false); return; }
        sistema.getUsuarios().remove(u);
        cargarUsuarios(); actualizarKPIs();
        setMsg(lblMensajeUsuario, "🗑 Usuario \"" + u.getNombre() + "\" eliminado.", true);
    }

    private void cargarCompras() {
        if (tablaCompras == null) return;
        if (colCompraId != null)
            colCompraId.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getIdCompra())));
        colCompraUsuario.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUsuario().getNombre()));
        colCompraEvento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEvento().getNombre()));
        if (colCompraFecha != null)
            colCompraFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaCompra().toString()));
        colCompraTotal.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().calcularTotal()));
        if (colCompraMetodo != null)
            colCompraMetodo.setCellValueFactory(d -> new SimpleStringProperty(
                    d.getValue().getMetodoPago() != null ? d.getValue().getMetodoPago().toString() : "—"));
        colCompraEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstadoCompra().toString()));
        tablaCompras.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(Compra item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) { setStyle(""); return; }
                switch (item.getEstadoCompra().toString()) {
                    case "PAGADA"      -> setStyle("-fx-background-color: #061406;");
                    case "CONFIRMADA"  -> setStyle("-fx-background-color: #060a14;");
                    case "CANCELADA"   -> setStyle("-fx-background-color: #140606;");
                    case "REEMBOLSADA" -> setStyle("-fx-background-color: #100614;");
                    default            -> setStyle("");
                }
            }
        });
        tablaCompras.setItems(FXCollections.observableArrayList(sistema.getCompras()));
    }

    @FXML private void confirmarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { setMsg(lblMensajeCompra, "Selecciona una compra.", false); return; }
        boolean ok = c.confirmar(); cargarCompras(); actualizarMetricas();
        setMsg(lblMensajeCompra, ok ? "✅ Compra confirmada." : "❌ Solo se puede confirmar compras PAGADAS.", ok);
    }

    @FXML private void cancelarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { setMsg(lblMensajeCompra, "Selecciona una compra.", false); return; }
        boolean ok = c.cancelar(); cargarCompras(); actualizarMetricas();
        setMsg(lblMensajeCompra, ok ? "❌ Compra cancelada." : "❌ No se puede cancelar.", ok);
    }

    @FXML private void reembolsarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { setMsg(lblMensajeCompra, "Selecciona una compra.", false); return; }
        boolean ok = c.reembolsar(); cargarCompras(); actualizarMetricas();
        setMsg(lblMensajeCompra, ok ? "💰 Reembolso procesado." : "❌ Solo PAGADAS o CONFIRMADAS.", ok);
    }

    @FXML private void marcarIncidenciaCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { setMsg(lblMensajeCompra, "Selecciona una compra.", false); return; }
        boolean ok = c.marcarComoIncidencia(); cargarCompras(); actualizarMetricas();
        setMsg(lblMensajeCompra, ok ? "⚠ Compra marcada como incidencia." : "❌ No aplicable.", ok);
    }

    @FXML private void exportarCSV() {
        new GeneradorReporte(sistema).exportarVentasCSV("reporte_admin.csv", null, null);
        setMsg(lblMensajeCompra, "📄 CSV exportado como reporte_admin.csv", true);
    }

    @FXML private void exportarPDF() {
        new GeneradorReporte(sistema).exportarVentasPDF("reporte_admin.pdf", null, null);
        setMsg(lblMensajeCompra, "📑 PDF exportado como reporte_admin.pdf", true);
    }

    private void cargarRecintos() {
        if (tablaRecintos == null) return;
        colRecintoNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colRecintoCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colRecintoDireccion.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDireccion()));
        List<Recinto> recintos = sistema.getEventos().stream()
                .filter(e -> e.getRecinto() != null).map(Evento::getRecinto)
                .distinct().collect(Collectors.toList());
        tablaRecintos.setItems(FXCollections.observableArrayList(recintos));
    }

    @FXML private void crearRecinto() {
        if (tablaRecintos == null) return;
        String nombre = txtRecintoNombre.getText().trim();
        String dir    = txtRecintoDireccion.getText().trim();
        String ciudad = txtRecintoCiudad.getText().trim();
        if (nombre.isEmpty() || dir.isEmpty() || ciudad.isEmpty()) {
            setMsg(lblMensajeRecinto, "❌ Completa todos los campos.", false); return;
        }
        txtRecintoNombre.clear(); txtRecintoDireccion.clear(); txtRecintoCiudad.clear();
        setMsg(lblMensajeRecinto, "✅ Recinto \"" + nombre + "\" registrado.", true);
    }

    private void cargarZonasAdmin() {
        if (cmbEventoZonas == null) return;
        cmbEventoZonas.setItems(FXCollections.observableArrayList(sistema.getEventos()));
        cmbEventoZonas.setOnAction(e -> {
            Evento ev = cmbEventoZonas.getValue();
            if (ev != null && ev.getRecinto() != null && tablaZonasAdmin != null)
                tablaZonasAdmin.setItems(FXCollections.observableArrayList(ev.getRecinto().getZonas()));
        });
        if (colZonaAdminNombre != null) {
            colZonaAdminNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
            colZonaAdminTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipoZona().toString()));
            colZonaAdminCapacidad.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCapacidad())));
            colZonaAdminPrecio.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().getPrecioBase()));
            colZonaAdminOcupacion.setCellValueFactory(d -> {
                int oc = d.getValue().calcularOcupacion(), cap = d.getValue().getCapacidad();
                return new SimpleStringProperty(oc + "/" + cap + " (" + (cap > 0 ? String.format("%.0f", 100.0*oc/cap) : 0) + "%)");
            });
        }
    }

    private void cargarCombosAsientos() {
        if (cmbEventoAsientos == null) return;
        cmbEventoAsientos.setItems(FXCollections.observableArrayList(sistema.getEventos()));
        cmbEventoAsientos.setOnAction(e -> {
            Evento ev = cmbEventoAsientos.getValue();
            if (ev != null && ev.getRecinto() != null)
                cmbZonaAsientos.setItems(FXCollections.observableArrayList(ev.getRecinto().getZonas()));
        });
        cmbZonaAsientos.setOnAction(e -> cargarAsientosDeZona());
    }

    private void cargarAsientosDeZona() {
        if (cmbZonaAsientos == null || tablaAsientos == null) return;
        Zona zona = cmbZonaAsientos.getValue();
        if (zona == null) return;
        colAsientoId.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getIdAsiento())));
        colAsientoFila.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFila()));
        colAsientoNumero.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getNumero())));
        colAsientoEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));
        tablaAsientos.setRowFactory(tv -> new TableRow<>() {
            @Override protected void updateItem(Asiento item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) { setStyle(""); return; }
                switch (item.getEstado().toString()) {
                    case "DISPONIBLE" -> setStyle("-fx-background-color: #061406;");
                    case "RESERVADO"  -> setStyle("-fx-background-color: #141000;");
                    case "VENDIDO"    -> setStyle("-fx-background-color: #140606;");
                    case "BLOQUEADO"  -> setStyle("-fx-background-color: #0a0a14;");
                    default           -> setStyle("");
                }
            }
        });
        tablaAsientos.setItems(FXCollections.observableArrayList(zona.getAsientos()));
    }

    @FXML private void bloquearAsiento() {
        if (tablaAsientos == null) return;
        Asiento a = tablaAsientos.getSelectionModel().getSelectedItem();
        if (a == null) { setMsg(lblMensajeAsiento, "Selecciona un asiento.", false); return; }
        boolean ok = a.bloquear(); cargarAsientosDeZona();
        setMsg(lblMensajeAsiento, ok ? "🔒 Asiento bloqueado." : "❌ Solo disponibles se pueden bloquear.", ok);
    }

    @FXML private void liberarAsiento() {
        if (tablaAsientos == null) return;
        Asiento a = tablaAsientos.getSelectionModel().getSelectedItem();
        if (a == null) { setMsg(lblMensajeAsiento, "Selecciona un asiento.", false); return; }
        boolean ok = a.liberar(); cargarAsientosDeZona();
        setMsg(lblMensajeAsiento, ok ? "🔓 Asiento liberado." : "❌ No se puede liberar.", ok);
    }

    private void cargarTiposIncidencia() {
        if (cmbTipoIncidencia != null) {
            cmbTipoIncidencia.getItems().setAll(Incidencia.Tipo.values());
            cmbTipoIncidencia.getSelectionModel().selectFirst();
        }
        if (cmbFiltroTipo != null) {
            cmbFiltroTipo.getItems().add(null);
            cmbFiltroTipo.getItems().addAll(Incidencia.Tipo.values());
            cmbFiltroTipo.getSelectionModel().selectFirst();
        }
    }

    private void cargarIncidencias() {
        if (tablaIncidencias == null) return;
        colIncidenciaId.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getIdIncidencia())));
        colIncidenciaTipo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTipo().toString()));
        colIncidenciaDesc.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDescripcion()));
        colIncidenciaFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));
        colIncidenciaEntidad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEntidadAfectada()));
        tablaIncidencias.setItems(FXCollections.observableArrayList(sistema.getIncidencias()));
    }

    @FXML private void registrarIncidencia() {
        if (cmbTipoIncidencia == null) return;
        String descripcion = txtDescripcionIncidencia != null ? txtDescripcionIncidencia.getText().trim() : "";
        String entidad     = txtEntidadAfectada != null ? txtEntidadAfectada.getText().trim() : "";
        if (descripcion.isEmpty() || entidad.isEmpty()) {
            setMsg(lblMensajeIncidencia, "❌ Completa descripción y entidad afectada.", false); return;
        }
        sistema.getAdministradores().get(0)
                .registrarIncidencia(cmbTipoIncidencia.getValue(), descripcion, entidad, sistema);
        if (txtDescripcionIncidencia != null) txtDescripcionIncidencia.clear();
        if (txtEntidadAfectada != null) txtEntidadAfectada.clear();
        cargarIncidencias();
        setMsg(lblMensajeIncidencia, "⚠ Incidencia registrada correctamente.", true);
    }

    @FXML private void filtrarIncidencias() {
        Incidencia.Tipo tipo = cmbFiltroTipo != null ? cmbFiltroTipo.getValue() : null;
        LocalDate desde = dpFiltroDesde != null ? dpFiltroDesde.getValue() : null;
        LocalDate hasta = dpFiltroHasta != null ? dpFiltroHasta.getValue() : null;
        List<Incidencia> resultado = sistema.buscarIncidencias(tipo, desde, hasta);
        tablaIncidencias.setItems(FXCollections.observableArrayList(resultado));
        setMsg(lblMensajeIncidencia, "🔍 Filtro aplicado: " + resultado.size() + " resultado(s).", true);
    }

    @FXML private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) tablaEventos.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 580, 450));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            if (lblMensajeCompra != null) lblMensajeCompra.setText("Error al cerrar sesión.");
        }
    }

    private void setMsg(Label lbl, String msg, boolean ok) {
        if (lbl == null) return;
        lbl.setText(msg);
        lbl.setStyle(ok ? "-fx-text-fill: #2ecc71;" : "-fx-text-fill: #e74c3c;");
    }
}