package pis.hue2.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientController {

    @FXML
    private TextField textFieldLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField chatInput;
    @FXML
    private TextArea chat;
    static TextArea chatStatic;

    @FXML
    private void connect() {
        if (!ClientMain.clientThread.isAlive()) {
            ClientMain.clientThread.start();
            System.out.println("Client connected");
        } else
            System.out.println("Client already connected");
        chatStatic = chat;
    }

    @FXML
    private void disconnect() {
        if (!ClientMain.clientThread.isInterrupted()) {
            ClientMain.clientThread.interrupt();
            System.out.println("Client disconnected");
        }
    }

    @FXML
    private void btnLogin() throws IOException {
        ClientMain.client.setName(textFieldLogin.getText());
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        ClientMain.primaryStage.setTitle("Client: " + ClientMain.client.getName());
        stage.close();
    }

    @FXML
    private void sendMessage() {
        ClientMain.client.sendMessage(chatInput.getText());
        chatInput.setText("");
    }
}