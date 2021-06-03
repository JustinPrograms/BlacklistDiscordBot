package com.lethalflame.bot.Utlis;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandExecutor {

    boolean exectue(String[] args, MessageReceivedEvent event);

    String name();
    String description();
    String[] aliases();
}
