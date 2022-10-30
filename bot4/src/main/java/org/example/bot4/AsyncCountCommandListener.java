package org.example.bot4;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.ChatMessageCreatedEvent;

import static org.example.bot4.Main.cmm;

/**
 * The listener for the "/count async" command.
 *
 * This command is asynchronous, meaning that it will not block the further execution of the bot, and
 * it's way faster than the "/count" command. However, this doesn't guarantee that the numbers sent to
 * the server are in order from 1-10.
 */
public class AsyncCountCommandListener
{
    @Subscribe
    public void onChat(ChatMessageCreatedEvent event)
    {
        if(event.getChatMessage().getContent().equals("/count async")) // If the message is "/count async"
        {
            for(int i = 1; i <= 10; i++)
                new Thread(String.valueOf(i)) // Create a new thread for each number. The thread name is the number.
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            cmm.createChannelMessage( // Send a message with the thread name (the number)
                                    event.getChatMessage().getChannelId(),
                                    this.getName(),
                                    null,
                                    null,
                                    null,
                                    null
                            );
                        }
                        catch(Exception e) {e.printStackTrace();}
                    }
                }.start(); // Start the thread
            System.out.println("Counted async");
        }
    }
}
