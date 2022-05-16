package org.example.bot1;

import vip.floatationdevice.guilded4j.G4JClient;

import java.util.Scanner;

/**
 * Example bot: scan all messages and delete them if they contain "sus".
 */
public class Main
{
    static G4JClient client; // client instance

    public static void main(String[] args)
    {
        System.out.print("Enter your token: ");
        client = new G4JClient(new Scanner(System.in).nextLine()); // ask for token and create client instance
        client.ws.eventBus.register(new GuildedEventListener()); // register event listener
        System.out.println("Connecting");
        client.ws.connect();
        // ws.connect() and further methods are in a separate thread
        // and the main thread will continue to run
    }
}
