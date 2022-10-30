package org.example.bot4;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.ChatMessageCreatedEvent;

import static org.example.bot4.Main.cmm;

/**
 * The listener for the "/count" command.
 *
 * This command ensures that the numbers sent to the server are sorted from 1-10.
 * If a number fails to be sent, then subsequent numbers will not be sent.
 */
public class CountCommandListener
{
    @Subscribe
    public void onChat(ChatMessageCreatedEvent event)
    {
        if(event.getChatMessage().getContent().equals("/count")) // If the message is "/count"
            try
            {
                for(int i = 1; i <= 10; i++)
                    cmm.createChannelMessage( // Send a message with the number
                            event.getChatMessage().getChannelId(),
                            String.valueOf(i),
                            null,
                            null,
                            null,
                            null
                    );
                System.out.println("Counted");
            }
            catch(Exception e) {e.printStackTrace();}
    }
}
