package com.example.ticketland;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.TicketLand;
import model.Usuario;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private TextField txtPassword;
    @FXML private Label lblMensaje;

    // RF-001: ingresar como usuario
    @FXML
    private void loginUsuario() {
        String correo = txtCorreo.getText().trim();
        Usuario usuario = TicketLand.getInstance().buscarUsuarioPorCorreo(correo);
        if (usuario != null) {
            abrirVistaUsuario(usuario);
        } else {
            lblMensaje.setText("Usuario no encontrado. Intenta con: luna@gmail.com");
        }
    }

    // RF-001: ingresar como administrador
    @FXML
    private void loginAdmin() {
        abrirVistaAdmin();
    }

    private void abrirVistaUsuario(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("usuario-view.fxml")
            );
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 700, 500));
            UsuarioController controller = loader.getController();
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
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.setTitle("TicketLand - Administrador");
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar la vista.");
        }
    }

    // RF-001: ir a pantalla de registro
    @FXML
    private void irARegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("registro-view.fxml"));
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 500, 600));
            stage.setTitle("TicketLand - Registro");
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar registro.");
        }
    }
}