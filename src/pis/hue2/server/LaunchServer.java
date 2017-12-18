package pis.hue2.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class LaunchServer implements Runnable {

    private static void handleConnections() throws IOException {
        while (true) {
            for (Socket client : ClientDetector.clients) {
                System.out.println(client.toString());
                Scanner in = new Scanner(client.getInputStream());
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                String message = "";
                System.out.println("slept");
                if (in.hasNext())
                    message = in.nextLine();

                out.println("Server: " + message);
            }
        }
    }

    @Override
    public void run() {
        try {
            handleConnections();
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
    }
}