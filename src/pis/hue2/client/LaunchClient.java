package pis.hue2.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LaunchClient implements Runnable {

    private String name = "";
    private Socket server;
    private Scanner in;
    private PrintWriter out;

    public void connect() {
        try {
            server = new Socket("localhost", 3141);
            in = new Scanner(server.getInputStream());
            out = new PrintWriter(server.getOutputStream(), true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        connect();

        out.println(name + ": " + message);
        System.out.println(in.nextLine());
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