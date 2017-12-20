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

            Thread inputThread = new Thread(() -> {
                while (in.hasNext()) {
                    if (Thread.currentThread().isInterrupted())
                        return;
                    ClientController.chatStatic.appendText(in.nextLine() + "\n");
                }
            });
            inputThread.start();

            out.println(name + " entered the chatroom");

            while (true) {
                if (!newMessage.equals("")) {
                    out.println(name + ": " + newMessage);
                    newMessage = "";
                }
                if (Thread.currentThread().isInterrupted()) {
                    inputThread.interrupt();
                    out.println(name + " left the chat room");
                    while (inputThread.isAlive()) {
                    }
                    System.out.println(inputThread.isAlive());
                    out.close();
                    in.close();
                    server.close();
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