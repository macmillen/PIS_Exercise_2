package pis.hue2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientDetector implements Runnable {

    public static List<LaunchServer> clients = new ArrayList<>();

    @Override
    public void run() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(3141);
        } catch (IOException e) {
        }

        while (true) {
            try {
                Thread.sleep(0);
                LaunchServer client = new LaunchServer(server.accept());
                clients.add(client);
                new Thread(client).start();
                System.out.println("Client added");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
