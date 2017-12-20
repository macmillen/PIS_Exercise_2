package pis.hue2.client;

import java.util.Scanner;

public class ClientInput implements Runnable {

    private Scanner in;

    ClientInput(Scanner in) {
        this.in = in;
    }

    public void run() {
        while (in.hasNext())
            ClientController.chatStatic.appendText(in.nextLine() + "\n");
    }
}