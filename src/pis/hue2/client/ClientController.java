package pis.hue2.client;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ClientController {

    @FXML
    private ListView<String> listView;
    private ListProperty<String> listProperty = new SimpleListProperty<>();

    @FXML
    private TextField textFieldLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField chatInput;

    public TextArea chat;


    void updateNamelist(String[] namesList) {

        listView.itemsProperty().bind(listProperty);

        ArrayList<String> newItems = new ArrayList<>();

        for (int i = 1; i < namesList.length; ++i) {
            newItems.add(namesList[i]);
        }
        listProperty.set(FXCollections.observableArrayList(newItems));

    }

    @FXML
    private void connect() {
        if (ClientMain.clientThread == null || !ClientMain.clientThread.isAlive()) {
            ClientMain.clientThread = new Thread(ClientMain.client);

            ClientMain.clientThread.start();
        } else
            System.out.println("Client already connected");
    }

    @FXML
    private void disconnect() {
        PrintWriter out = ClientMain.client.getOutputStream();
        out.println("disconnect:");
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
//        listView.setDisable(ClientMain.clientThread.isInterrupted());
    }
}