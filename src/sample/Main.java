package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pis.hue2.client.LaunchClient;
import pis.hue2.server.LaunchServer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Thread server = new Thread(new LaunchServer());
        Thread clientDominik = new Thread(new LaunchClient("Dominik"));
        Thread clientMilan = new Thread(new LaunchClient("Milan"));

        server.start();
        clientMilan.start();
        clientDominik.start();

        launch(args);
    }
}
