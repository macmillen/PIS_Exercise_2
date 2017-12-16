package pis.hue2.server;


import javafx.fxml.FXML;

import java.lang.reflect.InvocationTargetException;


public class ServerController {


    @FXML
    private void startServer() {

        try {
            Main.server.start();
            System.out.println("Server started");
        } catch (IllegalThreadStateException e) {
            System.out.println("Server already started");

        }
    }
}
