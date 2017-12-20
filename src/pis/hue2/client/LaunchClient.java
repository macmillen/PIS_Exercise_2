package pis.hue2.client;

import java.awt.*;
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


                    String[] commandTemp = in.nextLine().split(":");
                    String command = commandTemp[0];
                    String nameSender = commandTemp[1];

                    if (command.equals("refused"))
                        ClientMain.clientThread.interrupt();


                    else if (command.equals("message"))
                        ClientController.chatStatic.appendText(nameSender + ": " + commandTemp[2] + "\n");

                    else if (command.equals("namelist"))
                        ClientController.updateNamelist(commandTemp);




                }
            });
            inputThread.start();

            out.println("connect:" + name);

            while (true) {
                if (!newMessage.equals("")) {
                    out.println("message:" + newMessage);
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