package pis.hue2.client;

import javafx.fxml.FXML;

public class ClientController {

    @FXML
    private void connect() {
        LaunchClient.connect();
    }

}
