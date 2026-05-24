package com.example.ticketland;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import state.CompraCancelada;
import state.CompraPagada;
import state.CompraConfirmada;
import java.util.HashMap;
import java.util.Map;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colCiudad;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colEstado;
    @FXML private Label lblMensajeEvento;

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colUsuarioNombre;
    @FXML private TableColumn<Usuario, String> colUsuarioCorreo;
    @FXML private TableColumn<Usuario, String> colUsuarioTelefono;
    @FXML private Label lblMensajeUsuario;

    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraUsuario;
    @FXML private TableColumn<Compra, String> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraEstado;
    @FXML private Label lblMensajeCompra;
    @FXML private BarChart<String, Number> chartVentas;
    @FXML private PieChart chartEstados;

    private TicketLand sistema = TicketLand.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEventos();
        cargarUsuarios();
        cargarCompras();
        actualizarMetricas();
    }

    // RF-013: cargar eventos
    private void cargarEventos() {
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));
        tablaEventos.setItems(FXCollections.observableArrayList(sistema.getEventos()));
    }

    // RF-024: activar evento
    @FXML
    private void activarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { lblMensajeEvento.setText("Selecciona un evento."); return; }
        e.activar();
        cargarEventos();
        lblMensajeEvento.setText("Evento activado.");
    }

    // RF-024: pausar evento
    @FXML
    private void pausarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { lblMensajeEvento.setText("Selecciona un evento."); return; }
        e.pausar();
        cargarEventos();
        lblMensajeEvento.setText("Evento pausado.");
    }

    // RF-024: cancelar evento
    @FXML
    private void cancelarEvento() {
        Evento e = tablaEventos.getSelectionModel().getSelectedItem();
        if (e == null) { lblMensajeEvento.setText("Selecciona un evento."); return; }
        e.cancelar();
        cargarEventos();
        lblMensajeEvento.setText("Evento cancelado.");
    }

    // RF-012: cargar usuarios
    private void cargarUsuarios() {
        colUsuarioNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colUsuarioCorreo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCorreo()));
        colUsuarioTelefono.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTelefono()));
        tablaUsuarios.setItems(FXCollections.observableArrayList(sistema.getUsuarios()));
    }

    // RF-012: eliminar usuario
    @FXML
    private void eliminarUsuario() {
        Usuario u = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (u == null) { lblMensajeUsuario.setText("Selecciona un usuario."); return; }
        sistema.eliminarUsuario(u);
        cargarUsuarios();
        lblMensajeUsuario.setText("Usuario eliminado.");
    }

    // RF-016: cargar compras
    private void cargarCompras() {
        colCompraUsuario.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUsuario().getNombre()));
        colCompraEvento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEvento().getNombre()));
        colCompraTotal.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().calcularTotal()));
        colCompraEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstadoCompra().toString()));
        tablaCompras.setItems(FXCollections.observableArrayList(sistema.getCompras()));
    }

    // RF-016: cancelar compra
    @FXML
    private void cancelarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { lblMensajeCompra.setText("Selecciona una compra."); return; }
        boolean ok = c.cancelar();
        cargarCompras();
        lblMensajeCompra.setText(ok ? "Compra cancelada." : "No se puede cancelar.");
    }

    // RF-016: confirmar compra
    @FXML
    private void confirmarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { lblMensajeCompra.setText("Selecciona una compra."); return; }
        boolean ok = c.confirmar();
        cargarCompras();
        lblMensajeCompra.setText(ok ? "Compra confirmada." : "No se puede confirmar.");
    }

    // RF-046: exportar CSV
    @FXML
    private void exportarCSV() {
        GeneradorReporte reporte = new GeneradorReporte(sistema);
        reporte.exportarVentasCSV("reporte_ventas.csv", null, null);
        lblMensajeCompra.setText("CSV exportado como reporte_ventas.csv");
    }

    // RF-046: exportar PDF
    @FXML
    private void exportarPDF() {
        GeneradorReporte reporte = new GeneradorReporte(sistema);
        reporte.exportarVentasPDF("reporte_ventas.pdf", null, null);
        lblMensajeCompra.setText("PDF exportado como reporte_ventas.pdf");
    }

    // RF-018/019: actualizar métricas con JavaFX Charts
    @FXML
    private void actualizarMetricas() {
        // --- BAR CHART: ventas por evento ---
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Entradas vendidas");

        Map<String, Integer> ventasPorEvento = new HashMap<>();
        for (Compra c : sistema.getCompras()) {
            String nombreEvento = c.getEvento().getNombre();
            ventasPorEvento.put(nombreEvento,
                    ventasPorEvento.getOrDefault(nombreEvento, 0) + c.getEntradas().size());
        }
        for (Map.Entry<String, Integer> entry : ventasPorEvento.entrySet()) {
            serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        chartVentas.getData().clear();
        chartVentas.getData().add(serie);

        // --- PIE CHART: estados de compras ---
        long pagadas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraPagada).count();
        long confirmadas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraConfirmada).count();
        long canceladas = sistema.getCompras().stream()
                .filter(c -> c.getEstadoCompra() instanceof CompraCancelada).count();
        long otras = sistema.getCompras().size() - pagadas - confirmadas - canceladas;

        chartEstados.getData().clear();
        chartEstados.getData().addAll(
                new PieChart.Data("Pagadas", pagadas),
                new PieChart.Data("Confirmadas", confirmadas),
                new PieChart.Data("Canceladas", canceladas),
                new PieChart.Data("Otras", otras)
        );
    }

    // RF-001: cerrar sesión
    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) tablaEventos.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 400));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            lblMensajeCompra.setText("Error al cerrar sesión.");
        }
    }
}