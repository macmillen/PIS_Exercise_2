package pis.hue2.server;

import java.net.*;
import java.io.*;

public class LaunchServer implements Runnable {

    private int portNumber = 3141;

    private String inputMessage = "";

    public void startServer() {


        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
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
    }

    public String getInputMessage() {
        return inputMessage;
    }

    @Override
    public void run() {
        startServer();
    }
}