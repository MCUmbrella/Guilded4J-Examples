package org.example.bot2;

import cn.hutool.core.date.DateUtil; // you can replace this with anything you like
import vip.floatationdevice.guilded4j.G4JClient;

import java.io.*;
import java.util.UUID;

import static org.example.bot2.StatusUtil.getStatusMessageText;

/**
 * Show your system's status in a channel.
 * This is a write-only bot, which means the event manager will not be used.
 */
public class Main
{
    static String token;
    static String statusChannelId;
    static int statusMessageUpdateInterval;
    static boolean printUpdateResults;
    static UUID statusMessageId;

    public static void main(String[] args) throws InterruptedException
    {
        // load config from config.properties. create if not exists
        System.out.println("Loading config");
        try
        {
            ConfigUtil.loadConfig();
        }
        catch(ConfigUtil.ConfigCreationException e)
        {
            System.out.println("Failed to create config file. The program will exit");
            e.printStackTrace();
            System.exit(-1);
        }
        catch(ConfigUtil.InvalidConfigException e)
        {
            System.out.println("Failed to load config file. The program will exit");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Config loaded");

        // load message ID from 'statusMessage.uuid'. skip if the file not exists
        try
        {
            File sessionFile = new File("statusMessage.uuid");
            if(sessionFile.exists())
            {
                System.out.println("Restoring saved message ID");
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sessionFile));
                statusMessageId = (UUID) ois.readObject();
                ois.close();
                System.out.println("Message ID: " + statusMessageId);
            }
        }
        catch(Exception e)
        {
            System.err.println("Failed to restore message ID. The program will exit.");
            e.printStackTrace();
            System.exit(-1);
        }

        // initialize bot instance
        System.out.println("Bot is starting");
        G4JClient bot = new G4JClient(token);

        // check the status message. create if not exists
        if(statusMessageId == null)
        {
            System.out.println("Creating status message");
            try
            {
                statusMessageId = UUID.fromString(bot.createChannelMessage(statusChannelId, getStatusMessageText(), null, false).getMsgId());
                //save message ID to 'statusMessage.uuid'
                System.out.println("Saving message ID");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("statusMessage.uuid"));
                oos.writeObject(statusMessageId);
                oos.close();
                System.out.println("Message ID saved to 'statusMessage.uuid'");
                System.out.println("Status message created. Message ID: " + statusMessageId);
            }
            catch(Exception e)
            {
                System.err.println("Failed to create status message. The program will exit.");
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // start the updater loop
        System.out.println("Status message updater loop started");
        for(;;)
        {
            try
            {
                // update status massage
                long startTime = System.currentTimeMillis();
                bot.updateChannelMessage(statusChannelId, statusMessageId.toString(), getStatusMessageText());
                if(printUpdateResults)
                    System.out.println("Status message updated at " + DateUtil.date() + ". Took " + (System.currentTimeMillis() - startTime) + "ms");
            }
            catch(Exception e)
            {
                if(printUpdateResults) System.err.println("Updater loop failed at " + DateUtil.date() + " (" + e + ")");
            }
            Thread.sleep(statusMessageUpdateInterval * 1000L);
        }
    }
}
