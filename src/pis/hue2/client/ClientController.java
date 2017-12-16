package pis.hue2.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientController {

    @FXML
    private TextField textFieldLogin;
    @FXML
    private Button btnLogin;

    @FXML
    private void connect() {
        try {
            ClientMain.clientThread.start();
            System.out.println("Client connected");
        } catch (IllegalThreadStateException e) {
            System.out.println("Client already connected");
        }
    }

    @FXML
    private void btnLogin() throws IOException {
        ClientMain.client.setName(textFieldLogin.getText());
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        ClientMain.primaryStage.setTitle("Client: " + ClientMain.client.getName());
        stage.close();
    }
}