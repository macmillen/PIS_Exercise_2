package pis.hue2.client;

import javafx.application.Platform;
import pis.hue2.common.Misc;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LaunchClient implements Runnable {


    private String name = "";
    private String newMessage = "";
    private PrintWriter out;

    @Override
    public void run() {
        try {
            Socket server = new Socket("localhost", 3141);
            Scanner in = new Scanner(server.getInputStream());
            out = new PrintWriter(server.getOutputStream(), true);

            Thread inputThread = new Thread(() -> {
                while (in.hasNext()) {
                    String in_ = in.nextLine();
                    String[] commandTemp = in_.split(":");
                    String command = commandTemp[0];
                    String displayText = "";

                    if (command.equals("connect"))
                        displayText = "Successfully connected";
                    if (command.equals("refused")) {
                        ClientMain.clientThread.interrupt();
                        if (commandTemp[1].equals("too_many_users"))
                            displayText = "Refused: too many users";
                        if (commandTemp[1].equals("name_in_use"))
                            displayText = "Refused: name in use";
                        if (commandTemp[1].equals("invalid_name"))
                            displayText = "Refused: invalid name";
                    } else if (command.equals("message"))
                        displayText = commandTemp[1] + ": " + commandTemp[2];

                    else if (command.equals("namelist"))
                        Platform.runLater(() -> ClientMain.clientController.updateNamelist(commandTemp));

                    else if (command.equals("disconnect")) {
                        if (commandTemp[1].equals("ok"))
                            displayText = "Disconnected: ok";
                        if (commandTemp[1].equals("invalid_command"))
                            displayText = "Disconnected: Invalid command";
                        ClientMain.clientThread.interrupt();
                    }
                    if (!displayText.equals(""))
                        ClientMain.clientController.chat.appendText(Misc.getTime() + " " + displayText + "\n");
                    if (ClientMain.clientThread.isInterrupted())
                        return;
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
                    out.close();
                    in.close();
                    server.close();
                    return;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host");
        } catch (IOException e) {
            ClientMain.clientController.chat.appendText(Misc.getTime() + " Server offline" + "\n");
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

    PrintWriter getOutputStream() {
        return out;
    }
}