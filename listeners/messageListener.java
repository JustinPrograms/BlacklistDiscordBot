package com.lethalflame.bot.listeners;

import com.lethalflame.bot.bot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class messageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            System.out.println(event.getMember() + " has tried to run the blacklist command");
            return;
        }

        if (event.getMessage().getContentRaw().startsWith(bot.b_Prefix))
            if (event.getChannelType().equals(ChannelType.TEXT)) {
                try{
                    bot.executeCommand(bot.b_Prefix, event);
                }catch(IOException exception){
                    System.out.println("An error occurred while trying to execute this command: ");
                    exception.printStackTrace();
                }

            }

    }

}
