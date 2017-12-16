package pis.hue2.server;

import java.net.*;
import java.io.*;

public class LaunchServer implements Runnable {

    private String inputMessage = "";

    private void startServer() {

        int portNumber = 3141;
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                inputMessage = in.readLine();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
        System.out.println("done");
    }

    public String getInputMessage() {
        return inputMessage;
    }

    @Override
    public void run() {
        startServer();
    }

    public void sendMessage() {

    }
}