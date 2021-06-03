package com.lethalflame.bot;

import com.lethalflame.bot.Utlis.ClassUtlis;
import com.lethalflame.bot.Utlis.CommandExecutor;
import com.lethalflame.bot.listeners.messageListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;

public class bot {

    public static JDABuilder builder;

    public static HashMap<String, CommandExecutor> COMMANDS = new HashMap<>();
    public static HashMap<String, CommandExecutor> ALIASES = new HashMap<>();

    public static String b_Prefix = "!";

    public static void main(String[] args) throws LoginException, IOException {

        String token = ""; // Please insert bot token here
        builder = JDABuilder.createDefault(token);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        builder.setActivity(Activity.watching("Bot made by: Flame"));

        builder.addEventListeners(new messageListener());
        builder.addEventListeners(new ListenerAdapter() {

            @Override
            public void onReady(@NotNull ReadyEvent event) {

                System.out.println("Blacklist Bot Loaded.");
            }
        });

        ClassUtlis.registerAllCommands();

        for(CommandExecutor commandExecutor : COMMANDS.values()) {
            if (commandExecutor.aliases() != null) {
                for(String alias : commandExecutor.aliases()) {
                    ALIASES.put(alias.toLowerCase(), commandExecutor);
                }
            }
        }
        builder.build();

    }

    public static void executeCommand(String prefix, MessageReceivedEvent event) throws IOException {

        String chatMessage = event.getMessage().getContentRaw();
        String[] splitCommand = chatMessage.split(" ");
        String command = splitCommand[0].replaceFirst("[" + prefix + "]", "");
        String commandArgs = event.getMessage().getContentRaw().replace(prefix + command, "");
        commandArgs = commandArgs.replaceFirst(" ", "");
        String[] args = commandArgs.split(" ");

        if (args.length == 1) {
            if (args[0].isEmpty() || args[0].equals(" ") || args[0].equals(prefix + command) || args[0].equals(prefix)) {
                args = new String[0];
            }
        }

        if (COMMANDS.get(command.toLowerCase()) != null) {
            if(!COMMANDS.get(command.toLowerCase()).exectue(args, event)) {
                event.getChannel().sendMessage("This command is disabled").queue();
            }
        } else if (ALIASES.get(command.toLowerCase()) != null) {
            if (!ALIASES.get(command.toLowerCase()).exectue(args, event)) {
                event.getChannel().sendMessage("This command is disabled").queue();

            }
        }
    }
}