package pis.hue2.server;

import javafx.application.Platform;
import pis.hue2.common.Misc;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ServerInput implements Runnable {

    private Socket client;
    private String name;
    private boolean interrupted;

    ServerInput(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            Scanner in;

            System.out.println(client.toString());
            System.out.println(LaunchServer.clients.size());

            try {
                in = new Scanner(client.getInputStream());

                String input = "";
                if (in.hasNext())
                    input = in.nextLine();
                ServerMain.serverController.textArea.appendText(Misc.getTime() + " " + input + "\n");

                String[] commandTemp = input.split(":");
                String command = commandTemp[0];
                String message = "";
                if (commandTemp.length > 1)
                    message = commandTemp[1];

                String output = "";

                boolean nameExists = false;

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                if (command.equals("connect")) {
                    for (int i = 0; i < LaunchServer.clientNames.size(); ++i)
                        if (LaunchServer.clientNames.get(i).equals(message))
                            nameExists = true;

                    if (commandTemp.length > 2 || message.length() > 30) {
                        out.println("refused:invalid_name");
                        interrupted = true;
                    } else if (LaunchServer.clientNames.size() == 3) {
                        out.println("refused:too_many_users");
                        interrupted = true;
                    } else if (nameExists) {
                        out.println("refused:name_in_use");
                        interrupted = true;
                    } else {
                        name = message;
                        out.println("connect:ok");
                        LaunchServer.clientNames.add(message);
                        output = updateNameList();
                        String finalOutput = output;
                        Platform.runLater(() -> ServerMain.serverController.updateNamelist(finalOutput.split(":")));
                    }

                } else if (command.equals("message"))
                    output = "message:" + name + ":" + message;

                else {
                    if (command.equals("disconnect"))
                        out.println("disconnect:ok");
                    else
                        out.println("disconnect:invalid_command");
                    LaunchServer.clientNames.remove(name);
                    output = updateNameList();
                    String finalOutput = output;
                    Platform.runLater(() -> ServerMain.serverController.updateNamelist(finalOutput.split(":")));
                    interrupted = true;
                }

                // broadcast
                for (ServerInput client : LaunchServer.clients) {
                    if (!input.equals("")) {
                        PrintWriter outB = new PrintWriter(client.client.getOutputStream(), true);
                        outB.println(output);
                    }
                }
                if (interrupted) {
                    LaunchServer.clients.remove(this);
                    in.close();
                    return;
                }
            } catch (IOException e) {
                System.err.println("IO Stream issue");
            }
        }
    }

    private String updateNameList() {
        String output = "namelist";

        for (int i = 0; i < LaunchServer.clientNames.size(); ++i) {
            output += ":" + LaunchServer.clientNames.get(i);
        }
        return output;
    }

    void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
}
