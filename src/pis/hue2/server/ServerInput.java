package pis.hue2.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ServerInput implements Runnable {

    private Socket client;
    private boolean interrupted;

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

                // broadcast
                for (ServerInput client : LaunchServer.clients) {
                    if (!message.equals("")) {
                        PrintWriter out = new PrintWriter(client.client.getOutputStream(), true);
                        out.println(message);
                    }
                }

                if (message.equals("")) {
                    interrupted = true;
                    for (int i = 0; i < LaunchServer.clients.size(); ++i) {
                        if (LaunchServer.clients.get(i).interrupted) {
                            LaunchServer.clients.remove(i);
                        }
                    }
                    return;
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