package pis.hue2.server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerController {

    @FXML
    private TextArea textArea;

    @FXML
    private void closeServer() {
        if (!ServerMain.serverThread.isInterrupted())
            ServerMain.serverThread.interrupt();
    }

    @FXML
    private void startServer() {
        if (!ServerMain.serverThread.isAlive()) {
            ServerMain.serverThread.start();
            textArea.appendText("Server started\n");
        } else textArea.appendText("Server already started\n");
    }
}
