package org.example.bot4;

import vip.floatationdevice.guilded4j.G4JWebSocketClient;
import vip.floatationdevice.guilded4j.rest.ChatMessageManager;

import java.util.Scanner;

/**
 * This bot is meant to show how Guilded4J sends HTTP requests.
 * <p>
 * By default, Guilded4J send HTTP requests in the current thread. You cannot do anything until the HTTP requests are done.
 * In this example we created a "/count" command that will make the bot count from 1 to 10.
 * The bot has two ways to send the number:
 * by sending each number in one single thread (/count) and
 * by sending each number in a new thread (/count async).
 * <p>
 * Also, in this example, instead of using G4JClient, we use G4JWebSocketClient and ChatMessageManager separately.
 * This shows that each component of Guilded4J can be used separately, and
 * is more useful in scenarios that do not require high bot functionality.
 */
public class Main
{
    static String TOKEN;
    static ChatMessageManager cmm;

    public static void main(String[] args)
    {
        System.out.print("Enter your bot token: ");
        TOKEN = new Scanner(System.in).nextLine(); // ask for the token
        cmm = new ChatMessageManager(TOKEN); // create the chat message manager with the token
        G4JWebSocketClient ws = new G4JWebSocketClient(TOKEN); // create the web socket client with the token
        ws.eventBus.register(new CountCommandListener()); // register the "/count" command
        ws.eventBus.register(new AsyncCountCommandListener()); // register the "/count async" command
        ws.connect(); // connect to the web socket and start listening for events
    }
}
