package pis.hue2.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LaunchClient implements Runnable {

    private String name = "";
    private String newMessage = "";

    @Override
    public void run() {
        try {
            Socket server = new Socket("localhost", 3141);
            Scanner in = new Scanner(server.getInputStream());
            PrintWriter out = new PrintWriter(server.getOutputStream(), true);

            new Thread(new ClientInput(in)).start();

            while (true) {
                if (!newMessage.equals("")) {
                    out.println(name + ": " + newMessage);
                    newMessage = "";
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Disconnected");
                    return;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host");
        } catch (IOException e) {
            System.err.println("IO issues");
        }
    }

    void sendMessage(String message) {
        newMessage = message;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}