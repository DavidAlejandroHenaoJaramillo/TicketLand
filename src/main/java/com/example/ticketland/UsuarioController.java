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
import decorator.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import java.io.IOException;
import java.util.List;

public class UsuarioController {

    @FXML private Label lblBienvenida;

    // --- TAB EVENTOS ---
    @FXML private TextField txtFiltroCiudad;
    @FXML private TextField txtFiltroCategoria;
    @FXML private TextField txtFiltroPrecio;
    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colCiudad;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colCategoria;
    @FXML private TableColumn<Evento, String> colEstado;

    // RF-005: selección de zona y asiento
    @FXML private ComboBox<Zona> cmbZona;
    @FXML private ComboBox<Asiento> cmbAsiento;
    @FXML private Label lblInfoZona;

    // RF-009: servicios adicionales
    @FXML private CheckBox chkVIP;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkMerchandising;
    @FXML private CheckBox chkParqueadero;
    @FXML private Label lblMensajeCompra;

    // --- TAB HISTORIAL ---
    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, String> colCompraFecha;
    @FXML private TableColumn<Compra, String> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraTotal;
    @FXML private TableColumn<Compra, String> colCompraEstado;

    // RF-033: mapa de asientos del evento seleccionado
    @FXML private TableView<Asiento> tablaMapaAsientos;
    @FXML private TableColumn<Asiento, String> colMapaAsientoId;
    @FXML private TableColumn<Asiento, String> colMapaFila;
    @FXML private TableColumn<Asiento, String> colMapaNumero;
    @FXML private TableColumn<Asiento, String> colMapaZona;
    @FXML private TableColumn<Asiento, String> colMapaEstado;
    @FXML private Label lblInfoMapa;

    // --- TAB PERFIL ---
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private Label lblMensajePerfil;

    private Usuario usuario;
    private TicketLand sistema = TicketLand.getInstance();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblBienvenida.setText("Bienvenido, " + usuario.getNombre());
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getCorreo());
        txtTelefono.setText(usuario.getTelefono());
        cargarEventos();
        cargarHistorial();
        configurarSeleccionEvento();
    }

    // RF-003: cuando el usuario selecciona un evento, cargar sus zonas
    private void configurarSeleccionEvento() {
        tablaEventos.getSelectionModel().selectedItemProperty().addListener(
                (obs, viejo, nuevo) -> {
                    if (nuevo != null && nuevo.getRecinto() != null) {
                        // RF-005: llenar combo de zonas disponibles
                        cmbZona.setItems(FXCollections.observableArrayList(
                                nuevo.getRecinto().getZonas()));
                        cmbZona.getSelectionModel().selectFirst();
                        actualizarAsientosDeZona();
                        // RF-033: mostrar mapa completo
                        mostrarMapaAsientos(nuevo);
                    }
                });
        // al cambiar zona, actualizar asientos disponibles
        if (cmbZona != null) {
            cmbZona.setOnAction(e -> actualizarAsientosDeZona());
        }
    }

    // RF-005: actualizar asientos disponibles según zona elegida
    private void actualizarAsientosDeZona() {
        Zona zona = cmbZona != null ? cmbZona.getValue() : null;
        if (zona == null) return;
        List<Asiento> disponibles = zona.getAsientosDisponibles();
        if (cmbAsiento != null) {
            cmbAsiento.setItems(FXCollections.observableArrayList(disponibles));
            if (!disponibles.isEmpty()) cmbAsiento.getSelectionModel().selectFirst();
        }
        if (lblInfoZona != null) {
            lblInfoZona.setText(zona.getNombre() + " | Precio: $" + (int) zona.getPrecioBase()
                    + " | Disponibles: " + disponibles.size() + "/" + zona.getCapacidad());
        }
    }

    // RF-033: mostrar mapa de asientos del evento (todos los estados)
    private void mostrarMapaAsientos(Evento evento) {
        if (tablaMapaAsientos == null) return;
        colMapaAsientoId.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getIdAsiento())));
        colMapaFila.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getFila()));
        colMapaNumero.setCellValueFactory(d ->
                new SimpleStringProperty(String.valueOf(d.getValue().getNumero())));
        colMapaZona.setCellValueFactory(d -> {
            // buscar a qué zona pertenece este asiento
            for (Zona z : evento.getRecinto().getZonas()) {
                if (z.getAsientos().contains(d.getValue())) {
                    return new SimpleStringProperty(z.getNombre());
                }
            }
            return new SimpleStringProperty("-");
        });
        colMapaEstado.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getEstado().toString()));

        // colorear filas según estado
        tablaMapaAsientos.setRowFactory(tv -> new TableRow<Asiento>() {
            @Override
            protected void updateItem(Asiento item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    String estado = item.getEstado().toString();
                    switch (estado) {
                        case "DISPONIBLE"  -> setStyle("-fx-background-color: #d5f5e3;");
                        case "RESERVADO"   -> setStyle("-fx-background-color: #fef9e7;");
                        case "VENDIDO"     -> setStyle("-fx-background-color: #fadbd8;");
                        case "BLOQUEADO"   -> setStyle("-fx-background-color: #d5d8dc;");
                        default            -> setStyle("");
                    }
                }
            }
        });

        java.util.List<Asiento> todosAsientos = new java.util.ArrayList<>();
        for (Zona z : evento.getRecinto().getZonas()) {
            todosAsientos.addAll(z.getAsientos());
        }
        tablaMapaAsientos.setItems(FXCollections.observableArrayList(todosAsientos));
        if (lblInfoMapa != null) {
            lblInfoMapa.setText("Verde=Disponible  Amarillo=Reservado  Rojo=Vendido  Gris=Bloqueado");
        }
    }

    // RF-003: buscar eventos con filtros
    @FXML private void buscarEventos() {
        String ciudad    = txtFiltroCiudad.getText().trim();
        String categoria = txtFiltroCategoria.getText().trim();
        String precioStr = txtFiltroPrecio.getText().trim();
        Double precioMax = null;
        try {
            if (!precioStr.isEmpty()) precioMax = Double.parseDouble(precioStr);
        } catch (NumberFormatException ex) {
            lblMensajeCompra.setText("Precio máximo inválido.");
            lblMensajeCompra.setStyle("-fx-text-fill: red;");
            return;
        }
        List<Evento> eventos = sistema.buscarEventos(
                ciudad.isEmpty() ? null : ciudad,
                categoria.isEmpty() ? null : categoria,
                null, precioMax);
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

    // RF-010: cargar historial
    private void cargarHistorial() {
        colCompraFecha.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFechaCompra().toString()));
        colCompraEvento.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEvento().getNombre()));
        colCompraTotal.setCellValueFactory(d -> new SimpleStringProperty("$" + (int) d.getValue().calcularTotal()));
        colCompraEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstadoCompra().toString()));
        tablaCompras.setItems(FXCollections.observableArrayList(usuario.getHistorialCompras()));
    }

    // RF-034 + RF-005: comprar con zona y asiento elegidos por el usuario
    @FXML private void comprarEntrada() {
        Evento eventoSeleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (eventoSeleccionado == null) {
            setMsg("Selecciona un evento primero.", true); return;
        }
        // RF-005: obtener zona y asiento elegidos
        Zona zona = (cmbZona != null) ? cmbZona.getValue() : null;
        if (zona == null && eventoSeleccionado.getRecinto() != null) {
            zona = eventoSeleccionado.getRecinto().getZonas().get(0);
        }
        if (zona == null || !zona.hayDisponibilidad()) {
            setMsg("No hay asientos disponibles en la zona seleccionada.", true); return;
        }
        Asiento asiento = (cmbAsiento != null && cmbAsiento.getValue() != null)
                ? cmbAsiento.getValue()
                : zona.getAsientosDisponibles().get(0);

        asiento.reservar();

        // RF-005/RF-009: construir entrada con servicios adicionales (Decorator)
        EntradaBase entradaFinal = new Entrada(
                sistema.getCompras().size() + 1,
                zona.getPrecioBase(),
                EstadoEntrada.ACTIVA,
                zona, asiento);
        if (chkVIP.isSelected())          entradaFinal = new EntradaVIP(entradaFinal);
        if (chkSeguro.isSelected())        entradaFinal = new SeguroDecorator(entradaFinal);
        if (chkMerchandising.isSelected()) entradaFinal = new MerchandisingDecorator(entradaFinal);
        if (chkParqueadero.isSelected())   entradaFinal = new ParqueaderoDecorator(entradaFinal);

        Compra compra = sistema.crearCompra(usuario, eventoSeleccionado,
                new PagoTarjeta("0000-0000-0000-0000", usuario.getNombre()));
        compra.agregarEntrada(entradaFinal);
        compra.pagar();

        cargarHistorial();
        actualizarAsientosDeZona();
        mostrarMapaAsientos(eventoSeleccionado);
        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle("¡Compra exitosa!");
        exito.setHeaderText("Tu entrada ha sido comprada");
        exito.setContentText("Evento: " + eventoSeleccionado.getNombre()
                + "\nZona: " + zona.getNombre()
                + "\nAsiento: " + asiento.getFila() + asiento.getNumero()
                + "\nTotal: $" + (int) compra.calcularTotal());
        exito.showAndWait();
        setMsg("¡Compra realizada! Total: $" + (int) compra.calcularTotal(), false);
    }

    // RF-036: cancelar compra
    @FXML private void cancelarCompra() {
        Compra c = tablaCompras.getSelectionModel().getSelectedItem();
        if (c == null) { setMsg("Selecciona una compra primero.", true); return; }

        // Alert de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cancelar compra");
        confirmacion.setHeaderText("¿Estás seguro de cancelar esta compra?");
        confirmacion.setContentText("Evento: " + c.getEvento().getNombre()
                + "\nTotal: $" + (int) c.calcularTotal()
                + "\nEsta acción no se puede deshacer.");
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isEmpty() || resultado.get() != ButtonType.OK) return;

        boolean ok = c.cancelar();
        cargarHistorial();
        if (ok) {
            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Compra cancelada");
            exito.setHeaderText(null);
            exito.setContentText("Tu compra fue cancelada exitosamente.");
            exito.showAndWait();
        }
        setMsg(ok ? "Compra cancelada." : "No se puede cancelar esta compra.", !ok);
    }

    // RF-011: exportar CSV
    @FXML private void exportarCSV() {
        GeneradorReporte reporte = new GeneradorReporte(sistema);
        reporte.exportarVentasCSV("reporte_usuario.csv", null, null);
        setMsg("CSV exportado como reporte_usuario.csv", false);
    }

    // RF-011: exportar PDF
    @FXML private void exportarPDF() {
        GeneradorReporte reporte = new GeneradorReporte(sistema);
        reporte.exportarVentasPDF("reporte_usuario.pdf", null, null);
        setMsg("PDF exportado como reporte_usuario.pdf", false);
    }

    // RF-002: guardar perfil
    @FXML private void guardarPerfil() {
        String nombre   = txtNombre.getText().trim();
        String correo   = txtCorreo.getText().trim();
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
    @FXML private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) lblBienvenida.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 400));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            setMsg("Error al cerrar sesión.", true);
        }
    }

    private void setMsg(String msg, boolean error) {
        lblMensajeCompra.setText(msg);
        lblMensajeCompra.setStyle(error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
}