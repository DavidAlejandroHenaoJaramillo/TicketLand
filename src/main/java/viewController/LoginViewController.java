package viewController;

import com.example.ticketland.HelloApplication;
import controller.AuthController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;

import java.io.IOException;

public class LoginViewController {

    @FXML private TextField txtCorreo;
    @FXML private TextField txtPassword;
    @FXML private Label lblMensaje;

    private final AuthController authController = new AuthController();

    @FXML
    private void loginUsuario() {
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText();

        Usuario usuario = authController.loginUsuario(correo, password);

        if (usuario != null) {
            abrirVistaUsuario(usuario);
        } else {
            lblMensaje.setText("Correo o contraseña incorrectos.");
        }
    }

    @FXML
    private void loginAdmin() {
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText();

        if (authController.loginAdmin(correo, password) != null) {
            abrirVistaAdmin();
        } else {
            lblMensaje.setText("Credenciales de administrador incorrectas.");
        }
    }

    @FXML
    private void irARegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("registro-view.fxml")
            );

            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 600));
            stage.setTitle("TicketLand - Registro");
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar registro.");
        }
    }

    private void abrirVistaUsuario(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("usuario-view.fxml")
            );

            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 700, 500));
            stage.setMaximized(true);

            UsuarioViewController controller = loader.getController();
            controller.setUsuario(usuario);

            stage.setTitle("TicketLand - " + usuario.getNombre());
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar la vista.");
        }
    }

    private void abrirVistaAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("admin-view.fxml")
            );

            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
            stage.setTitle("TicketLand - Administrador");
        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("Error al cargar admin: " + e.getMessage());
        }
    }
}