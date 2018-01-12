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

/**
 * Controller Klasse der Clients. Diese Klasse beinhaltet die funktionalität der Buttons. Außerdem werden verschiedene Anzeigen aktualisiert
 */
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


    /**
     * Methode zum aktualisieren der Namensliste
     *
     * @param namesList String Array, welche die Namen der Clients beinhaltet
     */
    void updateNamelist(String[] namesList) {

        listView.itemsProperty().bind(listProperty);

        ArrayList<String> newItems = new ArrayList<>();

        for (int i = 1; i < namesList.length; ++i) {
            newItems.add(namesList[i]);
        }
        listProperty.set(FXCollections.observableArrayList(newItems));

    }

    /**
     * Methode des Buttons, um zum Server zu connecten
     */
    @FXML
    private void connect() {
        if (ClientMain.clientThread == null || !ClientMain.clientThread.isAlive()) {
            ClientMain.clientThread = new Thread(ClientMain.client);

            ClientMain.clientThread.start();
        } else
            System.out.println("Client already connected");
    }

    /**
     * Methode des Buttons, um sich vom Server zu disconnecten
     */
    @FXML
    private void disconnect() {
        PrintWriter out = ClientMain.client.getOutputStream();
        out.println("disconnect:");
    }

    /**
     * Methode des Buttons, um den Benutzername zu bestätigen und sich einzuloggen
     *
     * @throws IOException
     */
    @FXML
    private void btnLogin() throws IOException {
        ClientMain.client.setName(textFieldLogin.getText());
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        ClientMain.primaryStage.setTitle("Client: " + ClientMain.client.getName());
        stage.close();
    }

    /**
     * Methode des Buttons, zum senden von Nachrichten
     */
    @FXML
    private void sendMessage() {
        ClientMain.client.sendMessage(chatInput.getText());
        chatInput.setText("");
//        listView.setDisable(ClientMain.clientThread.isInterrupted());
    }
}