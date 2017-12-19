package pis.hue2.server;

import javafx.fxml.FXML;

public class ServerController {

    @FXML
    private void closeServer() {
        if (!ServerMain.serverThread.isInterrupted())
            ServerMain.serverThread.interrupt();
    }

    @FXML
    private void startServer() {
        if (!ServerMain.serverThread.isAlive()) {
            ServerMain.serverThread.start();
            System.out.println("Server started");
        } else System.out.println("Server already started");
    }
}
