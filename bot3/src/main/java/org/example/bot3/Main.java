package org.example.bot3;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.G4JClient;
import vip.floatationdevice.guilded4j.event.ChatMessageCreatedEvent;
import vip.floatationdevice.guilded4j.object.ChatMessage;


/**
 * If someone send '/ping', reply with 'pong'.
 */
public class Main
{
    final static G4JClient client = new G4JClient("YOUR BOT TOKEN HERE");

    /**
     * The event handling is quite Bukkit-like but has some little differences.
     * If you have developed a Bukkit plugin before, you should be able to adapt quickly.
     */
    final static class EventListener
    {
        @Subscribe
        public void onMessage(ChatMessageCreatedEvent event) // the IDE may complain about this method being unused, but it's actually used
        {
            ChatMessage message = event.getChatMessageObject();
            if(message.getContent().equals("/ping"))
            {
                client.createChannelMessage(message.getChannelId(), "pong", new String[]{message.getMsgId()}, false);
                // you can choose not to send 'pong' as a reply by setting the last 2 arguments to null
            }
        }
    }

    public static void main(String[] args)
    {
        client.ws.eventBus.register(new EventListener()); // Register event handler
        client.ws.connect(); // Connect to the websocket and start listening for events
        /*
         The program won't exit until:
         - the connection is closed (you called client.ws.close() or some network error occurred. Such as a timeout)
         - the program is terminated (Ctrl+C)
         - an exception is thrown (such as can't send 'pong' due to a network error or lack of permissions)
        */
    }
}
