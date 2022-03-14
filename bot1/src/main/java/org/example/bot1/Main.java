package org.example.bot1;

import vip.floatationdevice.guilded4j.G4JClient;

import java.util.Scanner;

/**
 * Example bot: scan all messages and delete them if they contain "sus".
 */
public class Main
{
    static String token; // ask every time. change the code to make it able to be stored in a file if you want
    static G4JClient client; // client instance
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.print("Enter your token: ");
        token = scanner.nextLine();
        client = new G4JClient(token);
        client.ws.eventBus.register(new GuildedEventListener()); // register event listener
        System.out.println("Connecting");
        client.ws.connect();
        // ws.connect() is async. WS client is in a separate thread so you can do other stuff while waiting for it to connect

        for(;;) // this simulates an interactive shell
        {
            // commands:
            // - exit: disconnect and exit the program
            // - reconnect: disconnect and reconnect
            // - test: check if the main thread is OK
            String input = scanner.nextLine().toLowerCase();
            switch(input)
            {
                case "exit":
                    System.out.println("Exiting");
                    client.ws.close();
                    System.exit(0);
                    // ws.close() is also async so you may not see the disconnect message
                    break;
                case "reconnect":
                    System.out.println("Reconnecting");
                    client.ws.reconnect();
                    break;
                case "test":
                    System.out.println("OK");
                    break;
                default:
                    System.out.println("Available commands: exit, reconnect, test");
            }
        }
    }
}
