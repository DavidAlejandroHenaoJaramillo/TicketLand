package com.example.ticketland;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    private TicketLand sistema = TicketLand.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEventos();
        cargarUsuarios();
        cargarCompras();
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