package viewController;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Administrador;
import model.Persona;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMensaje;

    private final LoginController controller;

    public LoginViewController() {
        controller = new LoginController();
    }

    @FXML
    public void loginUsuario() {
        String correo = txtCorreo.getText();
        Persona persona = controller.iniciarSesion(correo);
        if (persona == null) {
            lblMensaje.setText("Correo no encontrado");
            return;
        }
        if (persona instanceof Administrador) {
            lblMensaje.setText("Este acceso es solo para usuarios");
            return;
        }
        abrirVentana("/com/example/ticketland/usuario-view.fxml", "Panel Usuario");
    }

    @FXML
    public void loginAdmin() {
        String correo = txtCorreo.getText();
        Persona persona = controller.iniciarSesion(correo);
        if (persona == null) {
            lblMensaje.setText("Correo no encontrado");
            return;
        }
        if (!(persona instanceof Administrador)) {
            lblMensaje.setText("No tienes permisos de administrador");
            return;
        }
        abrirVentana("/com/example/ticketland/admin-view.fxml", "Panel Administrador");
    }

    @FXML
    public void irARegistro() {abrirVentana("/com/example/ticketland/registro-view.fxml", "Registro");
    }

    private void abrirVentana(String ruta, String titulo) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(ruta)
            );
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setText("Error cargando ventana");
        }
    }
}