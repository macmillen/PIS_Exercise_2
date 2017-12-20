package pis.hue2.server;

import pis.hue2.client.LaunchClient;

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
            System.out.println(client.toString());

            System.out.println(LaunchServer.clients.size());

            try {
                Scanner in = new Scanner(client.getInputStream());
                String input = "";
                if (in.hasNext())
                    input = in.nextLine();

                String[] commandTemp = input.split(":");
                String command = commandTemp[0];
                String message = commandTemp[1];

                String output = "";

                boolean nameExists = false;

                if (command.equals("connect")) {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                    for (int i = 0; i < LaunchServer.clientNames.size(); ++i)
                        if (LaunchServer.clientNames.get(i).equals(message))
                            nameExists = true;

                    if (commandTemp.length > 2 || message.length() > 30)
                        out.println("refused:invalid_name");

                    else if (LaunchServer.clientNames.size() == 3) {
                        out.println("refused:too_many_users");
                        LaunchServer.clients.remove(LaunchServer.clients.size() - 1);
                    } else if (nameExists)
                        out.println("refused:name_in_use");

                    else {
                        name = message;
                        out.println("connect:ok");
                        LaunchServer.clientNames.add(message);
                        output = "namelist";

                        for (int i = 0; i < LaunchServer.clientNames.size(); ++i) {
                            output += ":" + LaunchServer.clientNames.get(i);
                        }
                    }

                } else if (command.equals("message"))
                    output = "message:" + name + ":" + message;

                else if (command.equals("disconnect"))
                    output = "disconnect:ok";

                else
                    output = "disconnect:invalid_command";

                // broadcast
                for (ServerInput client : LaunchServer.clients) {
                    if (!input.equals("")) {
                        PrintWriter out = new PrintWriter(client.client.getOutputStream(), true);
                        out.println(output);
                    }
                }

                if (input.equals("")) {
                    interrupted = true;
                    for (int i = 0; i < LaunchServer.clients.size(); ++i) {
                        if (LaunchServer.clients.get(i).interrupted) {
                            LaunchServer.clients.remove(i);
                        }
                    }
                    return;
                }
            } catch (IOException e) {
                System.err.println("IO Stream issue");
            }
            if (ServerMain.serverThread.isInterrupted()) {
                System.out.println("ServerInput closed");
                return;
            }
        }
    }
}