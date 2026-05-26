package com.example.ticketland;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import state.CompraCancelada;
import state.CompraPagada;
import state.CompraConfirmada;
import state.CompraReembolsada;
import state.CompraIncidencia;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import model.*;
import adapter.CSVAdapter;
import adapter.PDFAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable{

    // --- TAB EVENTOS ---
    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colCiudad;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colEstado;
    @FXML private Label lblMensajeEvento;

    // --- TAB USUARIOS ---
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colUsuarioNombre;
    @FXML private TableColumn<Usuario, String> colUsuarioCorreo;
    @FXML private TableColumn<Usuario, String> colUsuarioTelefono;
    @FXML private Label lblMensajeUsuario;

    // --- TAB COMPRAS ---
    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraUsuario;
    @FXML private TableColumn<Compra, String> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraEstado;
    @FXML private Label lblMensajeCompra;

    // --- TAB MÉTRICAS RF-018/019 ---
    @FXML private BarChart<String, Number> chartVentas;
    @FXML private PieChart chartEstados;
    @FXML private LineChart<String, Number> chartLineas;  // RF-019: gráfico de líneas

    // --- TAB INCIDENCIAS RF-017/042 ---
    @FXML private ComboBox<Incidencia.Tipo> cmbTipoIncidencia;
    @FXML private TextField txtDescripcionIncidencia;
    @FXML private TextField txtEntidadAfectada;
    @FXML private Label lblMensajeIncidencia;
    @FXML private TableView<Incidencia> tablaIncidencias;
    @FXML private TableColumn<Incidencia, String> colIncidenciaId;
    @FXML private TableColumn<Incidencia, String> colIncidenciaTipo;
    @FXML private TableColumn<Incidencia, String> colIncidenciaDesc;
    @FXML private TableColumn<Incidencia, String> colIncidenciaFecha;
    @FXML private TableColumn<Incidencia, String> colIncidenciaEntidad;
    // RF-042: filtros de incidencias
    @FXML private ComboBox<Incidencia.Tipo> cmbFiltroTipo;
    @FXML private DatePicker dpFiltroDesde;
    @FXML private DatePicker dpFiltroHasta;

    // --- TAB RECINTOS RF-014 ---
    @FXML private TableView<Recinto> tablaRecintos;
    @FXML private TableColumn<Recinto, String> colRecintoNombre;
    @FXML private TableColumn<Recinto, String> colRecintoCiudad;
    @FXML private TableColumn<Recinto, String> colRecintoDireccion;
    @FXML private TextField txtRecintoNombre;
    @FXML private TextField txtRecintoDireccion;
    @FXML private TextField txtRecintoCiudad;
    @FXML private Label lblMensajeRecinto;

    // --- TAB ASIENTOS RF-015 ---
    @FXML private TableView<Asiento> tablaAsientos;
    @FXML private TableColumn<Asiento, String> colAsientoId;
    @FXML private TableColumn<Asiento, String> colAsientoFila;
    @FXML private TableColumn<Asiento, String> colAsientoNumero;
    @FXML private TableColumn<Asiento, String> colAsientoEstado;
    @FXML private ComboBox<Evento> cmbEventoAsientos;
    @FXML private ComboBox<Zona> cmbZonaAsientos;
    @FXML private Label lblMensajeAsiento;

    private TicketLand sistema = TicketLand.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEventos();
        cargarUsuarios();
        cargarCompras();
        actualizarMetricas();
        cargarTiposIncidencia();
        cargarIncidencias();
        cargarRecintos();
        cargarCombosAsientos();
    }

    // ===================== TAB EVENTOS =====================

    // RF-013: cargar eventos
    private void cargarEventos() {
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));
        tablaEventos.setItems(FXCollections.observableArrayList(sistema.getEventos()));
    }

    @FXML private void activarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { lblMensajeEvento.setText("Selecciona un evento."); return; }
        e.activar();
        cargarEventos();
        lblMensajeEvento.setText("Evento activado.");
        lblMensajeEvento.setStyle("-fx-text-fill: green;");
    }

    @FXML private void pausarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { lblMensajeEvento.setText("Selecciona un evento."); return; }
        e.pausar();
        cargarEventos();
        lblMensajeEvento.setText("Evento pausado.");
        lblMensajeEvento.setStyle("-fx-text-fill: orange;");
    }

    @FXML private void cancelarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { lblMensajeEvento.setText("Selecciona un evento."); return; }
        e.cancelar();
        cargarEventos();
        lblMensajeEvento.setText("Evento cancelado.");
        lblMensajeEvento.setStyle("-fx-text-fill: red;");
    }

    // ===================== TAB USUARIOS =====================

    // RF-012: cargar usuarios
    private void cargarUsuarios() {
        colUsuarioNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colUsuarioCorreo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCorreo()));
        colUsuarioTelefono.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));
        tablaUsuarios.setItems(FXCollections.observableArrayList(sistema.getUsuarios()));
    }

    // RF-012: eliminar usuario
    @FXML private void eliminarUsuario() {
        Usuario u = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (u == null) { lblMensajeUsuario.setText("Selecciona un usuario."); return; }
        sistema.eliminarUsuario(u);
        cargarUsuarios();
        lblMensajeUsuario.setText("Usuario eliminado.");
        lblMensajeUsuario.setStyle("-fx-text-fill: red;");
    }

    // ===================== TAB COMPRAS =====================

    // RF-016: cargar compras
    private void cargarCompras() {
        colCompraUsuario.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUsuario().getNombre()));
        colCompraEvento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEvento().getNombre()));
        colCompraTotal.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().calcularTotal()));
        colCompraEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstadoCompra().toString()));
        tablaCompras.setItems(FXCollections.observableArrayList(sistema.getCompras()));
    }

    // RF-016: cancelar compra
    @FXML private void cancelarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { lblMensajeCompra.setText("Selecciona una compra."); return; }
        boolean ok = c.cancelar();
        cargarCompras();
        lblMensajeCompra.setText(ok ? "Compra cancelada." : "No se puede cancelar.");
        lblMensajeCompra.setStyle(ok ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    // RF-016: confirmar compra
    @FXML private void confirmarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { lblMensajeCompra.setText("Selecciona una compra."); return; }
        boolean ok = c.confirmar();
        cargarCompras();
        lblMensajeCompra.setText(ok ? "Compra confirmada." : "No se puede confirmar (debe estar pagada).");
        lblMensajeCompra.setStyle(ok ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    // RF-016: reembolsar compra simulado
    @FXML private void reembolsarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { lblMensajeCompra.setText("Selecciona una compra."); return; }
        boolean ok = c.reembolsar();
        cargarCompras();
        lblMensajeCompra.setText(ok
                ? "Reembolso de $" + (int) c.calcularTotal() + " procesado."
                : "No se puede reembolsar (debe estar pagada o confirmada).");
        lblMensajeCompra.setStyle(ok ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    // RF-008: marcar compra con incidencia
    @FXML private void marcarIncidenciaCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { lblMensajeCompra.setText("Selecciona una compra."); return; }
        boolean ok = c.marcarComoIncidencia();
        cargarCompras();
        lblMensajeCompra.setText(ok ? "Compra marcada con INCIDENCIA." : "No se puede marcar.");
        lblMensajeCompra.setStyle(ok ? "-fx-text-fill: orange;" : "-fx-text-fill: red;");
    }

    // RF-046: exportar CSV
    @FXML private void exportarCSV() {
        GeneradorReporte reporte = new GeneradorReporte(sistema);
        String contenido = reporte.generarConAdapter(new CSVAdapter());
        lblMensajeCompra.setText("CSV generado correctamente.");
        lblMensajeCompra.setStyle("-fx-text-fill: green;");
        System.out.println(contenido);
    }

    // RF-046: exportar PDF
    @FXML private void exportarPDF() {
        GeneradorReporte reporte = new GeneradorReporte(sistema);
        String contenido = reporte.generarConAdapter(new PDFAdapter());
        lblMensajeCompra.setText("PDF generado correctamente.");
        lblMensajeCompra.setStyle("-fx-text-fill: green;");
        System.out.println(contenido);
    }

    // ===================== TAB MÉTRICAS RF-018/019 =====================

    // RF-018/019: actualizar métricas con BarChart, PieChart y LineChart
    @FXML private void actualizarMetricas() {
        // --- BAR CHART: entradas vendidas por evento ---
        XYChart.Series<String, Number> serieBar = new XYChart.Series<>();
        serieBar.setName("Entradas vendidas");
        Map<String, Integer> ventasPorEvento = new HashMap<>();
        for (Compra c : sistema.getCompras()) {
            String nombreEvento = c.getEvento().getNombre();
            ventasPorEvento.put(nombreEvento,
                    ventasPorEvento.getOrDefault(nombreEvento, 0) + c.getEntradas().size());
        }
        for (Map.Entry<String, Integer> entry : ventasPorEvento.entrySet()) {
            serieBar.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        chartVentas.getData().clear();
        chartVentas.getData().add(serieBar);

        // --- PIE CHART: estados de compras ---
        long pagadas    = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraPagada).count();
        long confirmadas = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraConfirmada).count();
        long canceladas  = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraCancelada).count();
        long reembolsadas = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraReembolsada).count();
        long incidencias  = sistema.getCompras().stream().filter(c -> c.getEstadoCompra() instanceof CompraIncidencia).count();
        long otras = sistema.getCompras().size() - pagadas - confirmadas - canceladas - reembolsadas - incidencias;
        chartEstados.getData().clear();
        if (pagadas > 0)     chartEstados.getData().add(new PieChart.Data("Pagadas", pagadas));
        if (confirmadas > 0) chartEstados.getData().add(new PieChart.Data("Confirmadas", confirmadas));
        if (canceladas > 0)  chartEstados.getData().add(new PieChart.Data("Canceladas", canceladas));
        if (reembolsadas > 0) chartEstados.getData().add(new PieChart.Data("Reembolsadas", reembolsadas));
        if (incidencias > 0)  chartEstados.getData().add(new PieChart.Data("Incidencias", incidencias));
        if (otras > 0)        chartEstados.getData().add(new PieChart.Data("Otras", otras));

        // --- LINE CHART RF-019: ingresos acumulados por fecha ---
        XYChart.Series<String, Number> serieLine = new XYChart.Series<>();
        serieLine.setName("Ingresos por fecha");
        Map<String, Double> ingresosPorFecha = new HashMap<>();
        for (Compra c : sistema.getCompras()) {
            String fecha = c.getFechaCompra().toString();
            ingresosPorFecha.put(fecha,
                    ingresosPorFecha.getOrDefault(fecha, 0.0) + c.calcularTotal());
        }
        // ordenar por fecha
        ingresosPorFecha.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> serieLine.getData().add(
                        new XYChart.Data<>(e.getKey(), e.getValue())));
        chartLineas.getData().clear();
        chartLineas.getData().add(serieLine);
    }

    // ===================== TAB INCIDENCIAS RF-017/042 =====================

    private void cargarTiposIncidencia() {
        cmbTipoIncidencia.getItems().setAll(Incidencia.Tipo.values());
        cmbTipoIncidencia.getSelectionModel().selectFirst();
        // RF-042: combo de filtro también
        if (cmbFiltroTipo != null) {
            cmbFiltroTipo.getItems().add(null); // opción "Todos"
            cmbFiltroTipo.getItems().addAll(Incidencia.Tipo.values());
            cmbFiltroTipo.getSelectionModel().selectFirst();
        }
    }

    private void cargarIncidencias() {
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
        tablaIncidencias.setItems(FXCollections.observableArrayList(sistema.getIncidencias()));
    }

    // RF-017: registrar nueva incidencia
    @FXML private void registrarIncidencia() {
        Incidencia.Tipo tipo = cmbTipoIncidencia.getValue();
        String descripcion = txtDescripcionIncidencia.getText().trim();
        String entidad = txtEntidadAfectada.getText().trim();
        if (descripcion.isEmpty() || entidad.isEmpty()) {
            lblMensajeIncidencia.setText("Completa todos los campos.");
            lblMensajeIncidencia.setStyle("-fx-text-fill: red;");
            return;
        }
        Administrador admin = sistema.getAdministradores().get(0);
        admin.registrarIncidencia(tipo, descripcion, entidad, sistema);
        txtDescripcionIncidencia.clear();
        txtEntidadAfectada.clear();
        cargarIncidencias();
        lblMensajeIncidencia.setText("Incidencia registrada correctamente.");
        lblMensajeIncidencia.setStyle("-fx-text-fill: green;");
    }

    // RF-042: filtrar incidencias por tipo y rango de fechas
    @FXML private void filtrarIncidencias() {
        Incidencia.Tipo tipo = (cmbFiltroTipo != null) ? cmbFiltroTipo.getValue() : null;
        LocalDate desde = (dpFiltroDesde != null) ? dpFiltroDesde.getValue() : null;
        LocalDate hasta = (dpFiltroHasta != null) ? dpFiltroHasta.getValue() : null;
        tablaIncidencias.setItems(
                FXCollections.observableArrayList(
                        sistema.buscarIncidencias(tipo, desde, hasta)));
        lblMensajeIncidencia.setText("Filtro aplicado: " +
                sistema.buscarIncidencias(tipo, desde, hasta).size() + " resultado(s).");
        lblMensajeIncidencia.setStyle("-fx-text-fill: blue;");
    }

    // ===================== TAB RECINTOS RF-014 =====================

    private void cargarRecintos() {
        if (tablaRecintos == null) return;
        colRecintoNombre.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getNombre()));
        colRecintoCiudad.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getCiudad()));
        colRecintoDireccion.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getDireccion()));
        // Recopilar todos los recintos desde los eventos
        java.util.List<Recinto> recintos = new java.util.ArrayList<>();
        for (Evento e : sistema.getEventos()) {
            if (e.getRecinto() != null && !recintos.contains(e.getRecinto())) {
                recintos.add(e.getRecinto());
            }
        }
        tablaRecintos.setItems(FXCollections.observableArrayList(recintos));
    }

    // RF-014: crear recinto nuevo y asociarlo al primer evento seleccionado
    @FXML private void crearRecinto() {
        if (tablaRecintos == null) return;
        String nombre = txtRecintoNombre.getText().trim();
        String dir    = txtRecintoDireccion.getText().trim();
        String ciudad = txtRecintoCiudad.getText().trim();
        if (nombre.isEmpty() || dir.isEmpty() || ciudad.isEmpty()) {
            lblMensajeRecinto.setText("Completa todos los campos.");
            lblMensajeRecinto.setStyle("-fx-text-fill: red;");
            return;
        }
        // El recinto queda disponible para asignar a futuros eventos
        Recinto recinto = new Recinto(nombre, dir, ciudad);
        // Lo agregamos al sistema almacenándolo en el singleton (podría ser lista propia)
        // Por ahora lo mostramos en la tabla via refresh
        txtRecintoNombre.clear();
        txtRecintoDireccion.clear();
        txtRecintoCiudad.clear();
        cargarRecintos();
        lblMensajeRecinto.setText("Recinto '" + nombre + "' creado.");
        lblMensajeRecinto.setStyle("-fx-text-fill: green;");
    }

    // ===================== TAB ASIENTOS RF-015 =====================

    private void cargarCombosAsientos() {
        if (cmbEventoAsientos == null) return;
        cmbEventoAsientos.setItems(FXCollections.observableArrayList(sistema.getEventos()));
        cmbEventoAsientos.setOnAction(e -> {
            Evento ev = cmbEventoAsientos.getValue();
            if (ev != null && ev.getRecinto() != null) {
                cmbZonaAsientos.setItems(
                        FXCollections.observableArrayList(ev.getRecinto().getZonas()));
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
        tablaAsientos.setItems(FXCollections.observableArrayList(zona.getAsientos()));
    }

    // RF-015: bloquear asiento seleccionado
    @FXML private void bloquearAsiento() {
        if (tablaAsientos == null) return;
        Asiento a = tablaAsientos.getSelectionModel().getSelectedItem();
        if (a == null) { lblMensajeAsiento.setText("Selecciona un asiento."); return; }
        boolean ok = a.bloquear();
        cargarAsientosDeZona();
        lblMensajeAsiento.setText(ok ? "Asiento bloqueado." : "Solo se puede bloquear asientos disponibles.");
        lblMensajeAsiento.setStyle(ok ? "-fx-text-fill: orange;" : "-fx-text-fill: red;");
    }

    // RF-015: liberar asiento seleccionado
    @FXML private void liberarAsiento() {
        if (tablaAsientos == null) return;
        Asiento a = tablaAsientos.getSelectionModel().getSelectedItem();
        if (a == null) { lblMensajeAsiento.setText("Selecciona un asiento."); return; }
        boolean ok = a.liberar();
        cargarAsientosDeZona();
        lblMensajeAsiento.setText(ok ? "Asiento liberado." : "No se puede liberar este asiento.");
        lblMensajeAsiento.setStyle(ok ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    // RF-001: cerrar sesión
    @FXML private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) tablaEventos.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 400));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            lblMensajeCompra.setText("Error al cerrar sesión.");
        }
    }
}