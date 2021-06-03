package com.lethalflame.bot.commands;

import com.lethalflame.bot.Utlis.CommandExecutor;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;


public class blacklistCommand implements CommandExecutor {


    @Override
    public boolean exectue(String[] args, MessageReceivedEvent event) {

        // Makes a list of all the guilds (Change guild ids to your servers and add more lines if needed
        ArrayList<Guild> serverList = new ArrayList<>();
        serverList.add(event.getJDA().getGuildById(826071787736793158L)); // Frisky's Server
        serverList.add(event.getJDA().getGuildById(849792511085641758L)); // Flame's Server

        String author = event.getAuthor().getName();
        MessageChannel channel = event.getChannel();
        String userID;
        String reason;

        channel.sendMessage("Attempting blacklist...").queue();
        try{
            // Sets userID and reason
            userID = args[0];
            reason = args[1];

        }catch(Exception exception) {
            exception.printStackTrace();
            channel.sendMessage("An error happened!").queue();
            return true;
        }

        // Sets log channel by ID
        MessageChannel logChannel = (MessageChannel) event.getJDA().getGuildChannelById(826074366352818236L);

        // Sets i to 0 and sets the amount of guilds
        int i = 0;
        int amtOfGuilds = serverList.size();

        for(Guild guildlist : serverList) {
            if (i == 0) {
                channel.sendMessage("Starting blacklist...").queue();
            }
            i++;
            if (i == amtOfGuilds) {
                logChannel.sendMessage("A blacklist occurred:" + "\nBlacklisted User: " + userID + "\nReason: " + reason + "\nStaff Member: " + author).queue();
            }
            guildlist.ban(userID, 0, reason).queue();
        }

        return true;
    }

    @Override
    public String name() {
        return "blacklist";
    }

    @Override
    public String description() {
        return "Blacklist players from the IJRP community.";
    }

    @Override
    public String[] aliases() {

        String[] aliases = new String[1];
        aliases[0] = "bl";

        return aliases;
    }
}
