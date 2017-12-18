package pis.hue2.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class LaunchServer implements Runnable {

    private Socket client;

    public LaunchServer(Socket client) {
        this.client = client;
    }

    private void handleConnection() throws IOException {
        while (true) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(client.toString());
            Scanner in = new Scanner(client.getInputStream());

            String message = "";
            if (in.hasNext())
                message = in.nextLine();

            for (LaunchServer client : ClientDetector.clients) {
                PrintWriter out = new PrintWriter(client.client.getOutputStream(), true);
                out.println(message);
            }
        }
    }

    @Override
    public void run() {
        try {
            handleConnection();
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
    }
}