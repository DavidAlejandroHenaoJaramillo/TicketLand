package com.example.ticketland;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import strategy.PagoTarjeta;
import decorator.EntradaVIP;
import decorator.SeguroDecorator;
import decorator.MerchandisingDecorator;
import decorator.ParqueaderoDecorator;
import decorator.EntradaBase;

import java.io.IOException;
import java.util.List;

public class UsuarioController {

    @FXML private Label lblBienvenida;
    @FXML private TextField txtFiltroCiudad;
    @FXML private TextField txtFiltroCategoria;
    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colCiudad;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colCategoria;
    @FXML private TableColumn<Evento, String> colEstado;
    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraFecha;
    @FXML private TableColumn<Compra, String> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraEstado;
    @FXML private Label lblMensajeCompra;
    @FXML private CheckBox chkVIP;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkMerchandising;
    @FXML private CheckBox chkParqueadero;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private Label lblMensajePerfil;

    private Usuario usuario;
    private TicketLand sistema = TicketLand.getInstance();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
        // RF-002: precargar datos actuales del perfil
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getCorreo());
        txtTelefono.setText(usuario.getTelefono());
        cargarEventos();
        cargarHistorial();
    }

    // RF-003: cargar eventos con filtros
    @FXML
    private void buscarEventos() {
        String ciudad = txtFiltroCiudad.getText().trim();
        String categoria = txtFiltroCategoria.getText().trim();
        List<Evento> eventos = sistema.buscarEventos(
                ciudad.isEmpty() ? null : ciudad,
                categoria.isEmpty() ? null : categoria,
                null
        );
        cargarTablaEventos(eventos);
    }

    private void cargarEventos() {
        cargarTablaEventos(sistema.getEventosActivos());
    }

    private void cargarTablaEventos(List<Evento> eventos) {
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCiudad.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCiudad()));
        colFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFecha().toString()));
        colCategoria.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategoria()));
        colEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().toString()));
        tablaEventos.setItems(FXCollections.observableArrayList(eventos));
    }

    // RF-010: cargar historial de compras
    private void cargarHistorial() {
        colCompraFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaCompra().toString()));
        colCompraEvento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEvento().getNombre()));
        colCompraTotal.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().calcularTotal()));
        colCompraEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstadoCompra().toString()));
        tablaCompras.setItems(FXCollections.observableArrayList(usuario.getHistorialCompras()));
    }

    // RF-034: comprar entrada del evento seleccionado
    @FXML
    private void comprarEntrada() {
        Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (eventoSeleccionado == null) {
            lblMensajeCompra.setText("Selecciona un evento primero.");
            lblMensajeCompra.setStyle("-fx-text-fill: red;");
            return;
        }
        if (!eventoSeleccionado.consultarDisponibilidad()) {
            lblMensajeCompra.setText("No hay asientos disponibles para este evento.");
            lblMensajeCompra.setStyle("-fx-text-fill: red;");
            return;
        }
        // tomar primer asiento disponible de la primera zona
        Zona zona = eventoSeleccionado.getRecinto().getZonas().get(0);
        Asiento asiento = zona.getAsientosDisponibles().get(0);
        asiento.reservar();
        // RF-005: crear entrada base
        EntradaBase entradaFinal = new Entrada(sistema.getCompras().size() + 1, zona.getPrecioBase(), EstadoEntrada.ACTIVA, zona, asiento);

// RF-009: agregar servicios adicionales con decorators
        if (chkVIP.isSelected()) entradaFinal = new EntradaVIP(entradaFinal);
        if (chkSeguro.isSelected()) entradaFinal = new SeguroDecorator(entradaFinal);
        if (chkMerchandising.isSelected()) entradaFinal = new MerchandisingDecorator(entradaFinal);
        if (chkParqueadero.isSelected()) entradaFinal = new ParqueaderoDecorator(entradaFinal);

        Compra compra = sistema.crearCompra(usuario, eventoSeleccionado, new PagoTarjeta("0000-0000-0000-0000", usuario.getNombre()));
        compra.agregarEntrada(entradaFinal);
        compra.pagar();
        cargarHistorial();
        lblMensajeCompra.setText("¡Compra realizada exitosamente!");
        lblMensajeCompra.setStyle("-fx-text-fill: green;");
    }

    // RF-036: cancelar compra seleccionada
    @FXML
    private void cancelarCompra() {
        Compra compraSeleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (compraSeleccionada == null) {
            lblMensajeCompra.setText("Selecciona una compra primero.");
            lblMensajeCompra.setStyle("-fx-text-fill: red;");
            return;
        }
        boolean cancelado = compraSeleccionada.cancelar();
        cargarHistorial();
        lblMensajeCompra.setText(cancelado ? "Compra cancelada." : "No se puede cancelar esta compra.");
        lblMensajeCompra.setStyle(cancelado ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    // RF-011: exportar historial a CSV
    @FXML
    private void exportarCSV() {
        GeneradorReporte reporte = new GeneradorReporte(sistema);
        reporte.exportarVentasCSV("reporte_usuario.csv", null, null);
        lblMensajeCompra.setText("CSV exportado como reporte_usuario.csv");
        lblMensajeCompra.setStyle("-fx-text-fill: green;");
    }

    // RF-002: guardar cambios del perfil del usuario
    @FXML
    private void guardarPerfil() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            lblMensajePerfil.setText("Todos los campos son obligatorios.");
            lblMensajePerfil.setStyle("-fx-text-fill: red;");
            return;
        }

        usuario.actualizarPerfil(nombre, correo, telefono);
        lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
        lblMensajePerfil.setText("Perfil actualizado correctamente.");
        lblMensajePerfil.setStyle("-fx-text-fill: green;");
    }

    // RF-001: cerrar sesión
    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) lblBienvenida.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 400));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            lblMensajeCompra.setText("Error al cerrar sesión.");
        }
    }
}