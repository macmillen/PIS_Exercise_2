package pis.hue2.server;


import javafx.fxml.FXML;


public class ServerController {


    @FXML
    private void startServer() {
        Main.server.start();
    }
}
