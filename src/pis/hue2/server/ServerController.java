package pis.hue2.server;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import pis.hue2.common.Misc;

import java.util.ArrayList;

public class ServerController {

    public TextArea textArea;

    @FXML
    private ListView<String> listView;
    private ListProperty<String> listProperty = new SimpleListProperty<>();

    void updateNamelist(String[] namesList) {

        listView.itemsProperty().bind(listProperty);

        ArrayList<String> newItems = new ArrayList<>();

        for (int i = 1; i < namesList.length; ++i) {
            newItems.add(namesList[i]);
        }
        listProperty.set(FXCollections.observableArrayList(newItems));
    }

    @FXML
    private void closeServer() {
        if (!ServerMain.serverThread.isInterrupted()) {
            ServerMain.serverThread.interrupt();
            for (ServerInput serverInput : LaunchServer.clients)
                serverInput.setInterrupted(true);
        }
    }

    @FXML
    private void startServer() {
        if (ServerMain.serverThread == null || !ServerMain.serverThread.isAlive()) {
            ServerMain.serverThread = new Thread(ServerMain.server);
            ServerMain.serverThread.start();
            textArea.appendText(Misc.getTime() + " Server started\n");
        } else textArea.appendText(Misc.getTime() + " Server already running\n");
    }

}
