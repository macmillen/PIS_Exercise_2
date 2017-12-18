package pis.hue2.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class LaunchServer implements Runnable {

    private void startServer() throws IOException {
        ServerSocket server = new ServerSocket(3141);

        while (true) {
            Socket client = null;

            try {
                client = server.accept();
                handleConnection(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            finally {
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                    }
                }
            }
            */
        }
    }

    private static void handleConnection(Socket client) throws IOException {
        Scanner in = new Scanner(client.getInputStream());
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        String message = in.nextLine();

        out.println("Server: " + message);
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (IOException e) {
            System.out.println("Connection failed");
        }
    }
}