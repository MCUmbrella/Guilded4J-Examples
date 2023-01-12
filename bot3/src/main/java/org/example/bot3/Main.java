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
    final static G4JClient client = new G4JClient("YOUR BOT TOKEN HERE"); // Remember to replace this!

    /**
     * Handles chat messages. This method is called when a chat message is created.
     * The IDE may complain about this method being unused, but it's actually used.
     * @param event The event that was fired.
     */
    @Subscribe
    public void onMessage(ChatMessageCreatedEvent event)
    {
        ChatMessage message = event.getChatMessage();
        if(message.getContent().equals("/ping")) // If someone sends '/ping'
        {
            client.getChatMessageManager().createChannelMessage(
                    message.getChannelId(), // channelId: the channel ID to send the message to
                    "pong", // content: the content of the message. This can be formatted using markdown (not fully supported yet)
                    null, // embeds[]: the embed objects. null means no embeds
                    new String[]{message.getId()}, // replyMessageIds[]: the message IDs to reply to. null means no replies
                    null, // isPrivate: whether to reply privately. null means default (false)
                    null // isSilent: whether to mention the author. null means default (true)
            );
            // note: you can choose not to send 'pong' as a reply by setting the last 3 arguments to null
        }
        else if(message.getContent().equals("/exit")) // If someone sends '/exit'
        {
            // Exit the program
            System.out.println("Closing WebSocket connection");
            client.disconnectWebSocket(true); // pass true to not let the WebSocket connection close asynchronously
            System.out.println("Bot is shutting down");
            System.exit(0);
        }
    }

    /**
     * Starts the bot.
     * The program won't exit until:
     *   - the connection is closed (you called client.disconnectWebSocket() or some network error occurred. Such as a timeout)
     *   - the program is terminated (by Ctrl+C, task manager, etc)
     *   - an exception is thrown (such as can't send 'pong' due to a network error or lack of permissions)
     *   - someone sends '/exit'
     */
    public static void main(String[] args)
    {
        client.registerEventListener(new Main()); // Register event handler
        client.connectWebSocket(); // Connect to the websocket and start listening for events
    }
}
