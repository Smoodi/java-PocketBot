package de.smoodi.pocketbot;

import de.smoodi.botutils.BOTUTILSCONFIG;
import de.smoodi.botutils.BotStatusMessage;
import de.smoodi.botutils.CommandHandler;
import de.smoodi.botutils.DefaultHelpCommand;
import de.smoodi.pocketbot.cmds.ExampleCommand;
import de.smoodi.pocketbot.listeners.MessageListener;
import de.smoodi.pocketbot.util.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.FileNotFoundException;
import java.util.Random;

public class Main {

    public static Config config;

    public static void main(String[] args){

        //We first load our config from a config file.
        config = new Config();

        try {
            if(!config.load("config.yml")) {
                System.err.println("Error loading config.");
                return;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }

        //We now initialise our bot utils config.
        new BOTUTILSCONFIG(config.getBotPrefix());

        JDABuilder builder = JDABuilder.createDefault(config.getBotToken());
        //We set basic settings.
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setAutoReconnect(true);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        //We choose a random but cool text.
        BotStatusMessage[] statuses = { new BotStatusMessage(BotStatusMessage.ACTIVITY_WATCHING, "out of the deity's pocket."),
                new BotStatusMessage(BotStatusMessage.ACTIVITY_WATCHING, "tv shows."),
                new BotStatusMessage(BotStatusMessage.ACTIVITY_LISTENING, "to the \"Meet me in the Woods\" on repeat."),
                new BotStatusMessage(BotStatusMessage.ACTIVITY_PLAYING, "Hide and seek."),
                new BotStatusMessage(BotStatusMessage.ACTIVITY_WATCHING, "nothing")};

        builder.setActivity(statuses[new Random().nextInt(statuses.length)].compile());

        //Building JDA.
        JDA jda;
        try {
            jda = builder.build();
            jda.awaitReady();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //Registering commands.
        //For the init.
        new CommandHandler();

        CommandHandler.registerTextCommand(new DefaultHelpCommand());
        CommandHandler.registerTextCommand(new ExampleCommand());

        //For now we don't want to use my whitelist system.
        CommandHandler.getDefaultWhitelist().setEnabled(false);

        //Dumping listeners.
        jda.addEventListener(new MessageListener());
    }

}
