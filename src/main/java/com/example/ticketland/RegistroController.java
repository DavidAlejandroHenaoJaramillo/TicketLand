package com.example.ticketland;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.TicketLand;
import model.Usuario;

import java.io.IOException;

public class RegistroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;
    @FXML private Label lblMensaje;

    private TicketLand sistema = TicketLand.getInstance();

    // RF-001: registrar nuevo usuario
    @FXML
    private void registrar() {
        String nombre    = txtNombre.getText().trim();
        String correo    = txtCorreo.getText().trim();
        String telefono  = txtTelefono.getText().trim();
        String password  = txtPassword.getText();
        String confirmar = txtConfirmarPassword.getText();

        // validaciones
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            lblMensaje.setStyle("-fx-text-fill: #e94560;");
            return;
        }
        if (!password.equals(confirmar)) {
            lblMensaje.setText("Las contraseñas no coinciden.");
            lblMensaje.setStyle("-fx-text-fill: #e94560;");
            return;
        }
        if (sistema.buscarUsuarioPorCorreo(correo) != null) {
            lblMensaje.setText("Ya existe una cuenta con ese correo.");
            lblMensaje.setStyle("-fx-text-fill: #e94560;");
            return;
        }

        // crear usuario
        int nuevoId = sistema.getUsuarios().size() + 1;
        Usuario nuevoUsuario = new Usuario(nuevoId, nombre, correo, telefono);
        sistema.agregarUsuario(nuevoUsuario);

        // Alert de éxito
        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle("Cuenta creada");
        exito.setHeaderText("¡Bienvenido a TicketLand!");
        exito.setContentText("Tu cuenta fue creada exitosamente.\nYa puedes iniciar sesión con: " + correo);
        exito.showAndWait();

        volverLogin();
    }

    // volver al login
    @FXML
    private void volverLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("login-view.fxml"));
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 500));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            lblMensaje.setText("Error al volver al login.");
        }
    }
}
