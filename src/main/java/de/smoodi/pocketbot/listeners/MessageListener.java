package de.smoodi.pocketbot.listeners;

import de.smoodi.botutils.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    /**
     * Fired when a message is received.
     * @param e
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        /**
         * Message handling. We don't have to do much because this is where my bot utils package comes in handy.
         * We just tell it to deal with all registered commands.
         */
        CommandHandler.parseMessageCommand(e);
    }

}
