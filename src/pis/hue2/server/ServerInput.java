package pis.hue2.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ServerInput implements Runnable {

    private Socket client;

    ServerInput(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(client.toString());
            try {
                Scanner in = new Scanner(client.getInputStream());
                String message = "";
                if (in.hasNext())
                    message = in.nextLine();

                for (ServerInput client : LaunchServer.clients) {
                    PrintWriter out = new PrintWriter(client.client.getOutputStream(), true);
                    out.println(message);
                }
            } catch (IOException e) {
                System.err.println("IO Stream issue");
            }
            if (ServerMain.serverThread.isInterrupted()) {
                System.out.println("ServerInput closed");
                return;
            }
        }
    }
}