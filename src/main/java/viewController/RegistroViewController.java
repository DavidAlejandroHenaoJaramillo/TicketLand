package viewController;

import com.example.ticketland.HelloApplication;
import controller.UsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroViewController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;
    @FXML private Label lblMensaje;

    private final UsuarioController usuarioController = new UsuarioController();

    @FXML
    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String password = txtPassword.getText();
        String confirmar = txtConfirmarPassword.getText();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return;
        }

        if (!password.equals(confirmar)) {
            mostrarError("Las contraseñas no coinciden.");
            return;
        }

        try {
            usuarioController.registrarUsuario(nombre, correo, telefono);
            mostrarExito(correo);
            volverLogin();
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void volverLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("login-view.fxml")
            );

            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 500));
            stage.setTitle("TicketLand");
        } catch (IOException e) {
            mostrarError("Error al volver al login.");
        }
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle("-fx-text-fill: #e94560;");
    }

    private void mostrarExito(String correo) {
        Alert exito = new Alert(Alert.AlertType.INFORMATION);
        exito.setTitle("Cuenta creada");
        exito.setHeaderText("¡Bienvenido a TicketLand!");
        exito.setContentText("Tu cuenta fue creada exitosamente.\nYa puedes iniciar sesión con: " + correo);
        exito.showAndWait();
    }
}