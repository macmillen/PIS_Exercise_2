package pis.hue2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class LaunchServer implements Runnable {

    static List<ServerInput> clients = new ArrayList<>();

    @Override
    public void run() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(3141);
        } catch (IOException e) {
            System.err.println("Failed to create ServerSocket");
        }

        while (true) {
            try {
                ServerInput client = new ServerInput(server.accept());
                clients.add(client);
                new Thread(client).start();
                System.out.println("Client added");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
