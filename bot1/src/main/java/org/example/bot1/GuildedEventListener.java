package org.example.bot1;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.ChatMessageCreatedEvent;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketClosedEvent;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketInitializedEvent;
import vip.floatationdevice.guilded4j.object.ChatMessage;

import static org.example.bot1.Main.client;

/**
 * Used to listen for events from Guilded4J WS client.
 */
public class GuildedEventListener
{
    /**
     * Automatically called when the Guilded4J WS client is successfully connected.
     */
    @Subscribe
    public void onConnected(GuildedWebSocketInitializedEvent event)
    {
        System.out.println("Connected to Guilded server");
    }

    /**
     * Automatically called when the Guilded4J WS client is disconnected.
     */
    @Subscribe
    public void onDisconnected(GuildedWebSocketClosedEvent event)
    {
        System.out.println("Disconnected from Guilded\n -> Code: " + event.getCode() + "\n -> Reason: " + event.getReason());
    }

    /**
     * Automatically called when a message is received from Guilded4J WS client.
     * Checks if the message contains "sus" and if so, deletes the message.
     */
    @Subscribe
    public void onMessage(ChatMessageCreatedEvent event)
    {
        ChatMessage message = event.getChatMessageObject();
        if(message.getContent().contains("sus"))
        {
            try
            {
                client.deleteChannelMessage(message.getChannelId(), message.getId());
                System.out.println("Deleted message at " + message.getCreationTime() + " (sender ID: " + message.getCreatorId() + ")");
            }
            catch(Exception e)
            {
                System.err.println("Failed to delete message:\n -> " + e.getMessage());
            }
        }
    }
}
