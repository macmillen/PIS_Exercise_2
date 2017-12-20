package pis.hue2.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ClientController {

    @FXML
    private ListView listView;
    static ListView listViewStatic;

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
    static void updateNamelist(String[] namesList) {

        ObservableList<String> items = listViewStatic.getItems();

        items.clear();

        for (int i = 1; i < namesList.length; ++i) {
            items.add(namesList[i]);
        }

    }

    @FXML
    private void connect() {
        if (ClientMain.clientThread == null || !ClientMain.clientThread.isAlive()) {
            ClientMain.clientThread = new Thread(ClientMain.client);

            ClientMain.clientThread.start();
            System.out.println("Client connected");
        } else
            System.out.println("Client already connected");
        chatStatic = chat;
        listViewStatic = listView;
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