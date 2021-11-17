package de.smoodi.botutils;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

public class DefaultHelpCommand extends AbstractCommand {

    @Override
    public String[] getAliases() {
        return new String[] {
                "help",
                "?"
        };
    }

    @Override
    public String getShortDescription() {
        return "Used to receive help about the bot or its commands.";
    }

    @Override
    public String getUsage() {
        return "\nUse `" + BOTUTILSCONFIG.getBotPrefix() + "help <command-alias>` to view information about a specific command" +
                "\nUse `" + BOTUTILSCONFIG.getBotPrefix() + "help` to view all available commands";
    }

    @Override
    public String getDescription() {
        return "This command will allow you to get further information about any command usable.";
    }

    /**
     * Basic help command.
     * @param ev
     * @param args
     */
    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {
        StringBuilder sbConceptual = new StringBuilder();

        String ebIcon = ev.getGuild().getIconUrl();
        Color ebColor = new Color(0,153,255);
        String ebAuthor = ev.getAuthor().getAvatarUrl();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(ebColor);
        eb.setThumbnail(ebIcon);
        eb.setTimestamp(Instant.now());
        eb.setFooter(BOTUTILSCONFIG.getRandomMessage(),ebAuthor);

        if (args.length < 1) {
            eb.setTitle("Commands List");

            for (AbstractCommand cmd : CommandHandler.getCommands()) {
                eb.addField(BOTUTILSCONFIG.getBotPrefix()+cmd.getAliases()[0], cmd.getShortDescription(), false);
            }
        } else {
            eb.setTitle("Command Specifics: "+BOTUTILSCONFIG.getBotPrefix()+args[0]);
            StringBuilder disabledProperties = new StringBuilder();
            AbstractCommand cmd = CommandHandler.getCommand(args[0]);

            if(cmd != null) {
                String[] cmdAliases = cmd.getAliases();
                StringBuilder sbAliases = new StringBuilder();
                for(int i = 1; i < cmdAliases.length; i++) {
                    sbAliases.append(cmdAliases[i]);
                    if(i != cmdAliases.length - 1) sbAliases.append(", ");
                }

                if (sbAliases.length() > 0) {
                    eb.addField("Aliases", sbAliases.toString(), false);
                } else {
                    if (disabledProperties.length() > 0) disabledProperties.append(", ");
                    disabledProperties.append("Aliases");
                }
                if (cmd.getCooldown() > 0) {
                    eb.addField("Cooldown", cmd.getCooldown() + " seconds", false);
                } else {
                    if (disabledProperties.length() > 0) disabledProperties.append(", ");
                    disabledProperties.append("Cooldown");
                }

                eb.addField("Description", cmd.getDescription(), false);
                eb.addField("Usage", cmd.getUsage(), false);
                if (disabledProperties.length() > 0) eb.addField("Disabled Properties", disabledProperties.toString(),false);

            } else {
                eb.addField("Command not found", "This command does not exist/has not been implemented yet", false);
            }
        }

        if (sbConceptual.length() > 0) {
            ev.getChannel().sendMessage(sbConceptual.toString()).queue();
        } else {
            ev.getChannel().sendMessage(eb.build()).queue();
        }
    }

}