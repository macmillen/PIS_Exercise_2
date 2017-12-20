package pis.hue2.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ClientMain extends Application {

    static LaunchClient client = new LaunchClient();
    static Thread clientThread;
    static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
        ClientMain.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            if (clientThread != null && clientThread.isAlive())
                clientThread.interrupt();
            primaryStage.close();
        });
        Stage popupStage;
        Parent rootStage;
        popupStage = new Stage();
        rootStage = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
        popupStage.setScene(new Scene(rootStage, 300, 150));
        popupStage.setTitle("Login");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(primaryStage);
        popupStage.showAndWait();
    }
}
