package pis.hue2.server;

import pis.hue2.common.Misc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse startet den Server und fügt Clients hinzu
 */
public class LaunchServer implements Runnable {


    static List<ServerInput> clients = new ArrayList<>();
    static ArrayList<String> clientNames = new ArrayList<>();

    /**
     * Diese Methode startet den Server und wartet stetig darauf, dass sich neue Clients verbinden.
     */
    @Override
    public void run() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(3141);

        } catch (IOException e) {
            System.err.println("Failed to create ServerSocket");
        }

        ServerSocket finalServer = server;
        new Thread(() -> {
            while (true) {
                if (ServerMain.serverThread.isInterrupted()) {
                    try {
                        finalServer.close();
                        return;
                    } catch (IOException e) {
                        System.err.println("Failed to close server");
                    }
                }
            }
        }).start();

        while (true) {
            try {
                ServerInput client = new ServerInput(server.accept());
                clients.add(client);
                new Thread(client).start();
                System.out.println("Client added");
            } catch (IOException e) {
                ServerMain.serverController.textArea.appendText(Misc.getTime() + " " + "Server closed\n");
                return;
            }
        }
    }
}
