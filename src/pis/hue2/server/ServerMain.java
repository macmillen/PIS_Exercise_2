package pis.hue2.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Klasse des Servers, welche die GUI startet
 */
public class ServerMain extends Application {

    static LaunchServer server = new LaunchServer();
    static Thread serverThread;
    static ServerController serverController;

    /**
     * Diese Methode wird beim starten der GUI ausgeführt
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ServerGUI.fxml"));
        Parent root = fxmlLoader.load();
        serverController = fxmlLoader.getController();
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 350, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
