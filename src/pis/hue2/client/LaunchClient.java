package pis.hue2.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LaunchClient implements Runnable {

    private String name = "";
    private Socket server;
    private Scanner in;
    private PrintWriter out;
    private String newMessage = "";

    synchronized public void connect() {
        try {
            server = new Socket("localhost", 3141);
            in = new Scanner(server.getInputStream());
            out = new PrintWriter(server.getOutputStream(), true);

            while (true) {
                if (!newMessage.equals("")) {
                    System.out.println("input confirmed");
                    out.println(name + ": " + newMessage);
                    System.out.println("slept");
                    System.out.println(in.nextLine());
                    newMessage = "";
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Disconnected");
                    return;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        newMessage = message;
    }

    @Override
    public void run() {
        connect();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}