package de.smoodi.botutils;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class CommandHandler {


    private static Map<String, AbstractCommand> commandsExecs = new HashMap<String, AbstractCommand>();
    private static List<AbstractCommand> commands = new LinkedList<AbstractCommand>();
    private static Map<String, CommandWhitelist> whitelists = new HashMap<String, CommandWhitelist>();


    /**
     * Parses a message for commands.
     * @param e
     */
    public static void parseMessageCommand(MessageReceivedEvent e) {
        String msg = e.getMessage().getContentRaw();

        //Parsing the command.
        if (msg.startsWith(BOTUTILSCONFIG.getBotPrefix())) {
            int i = msg.indexOf(" ");
            if (i == -1) i = msg.length();

            String cmd = msg.substring(BOTUTILSCONFIG.getBotPrefix().length(), Math.max(BOTUTILSCONFIG.getBotPrefix().length(), i));

            final int min = Math.min(i + 1, msg.length());
            int argc = 0;
            String[] args = new String[0];
            if (msg.substring(i).indexOf(" ") != -1) {
                args = msg.substring(min).split(" ");
                argc = args.length;
            }

            System.out.println("We received (COMMAND): " + cmd + " and " + argc + " arguments: ");
            for (int ii = 0; ii < argc; ii++) {
                System.out.println(" - " + args[ii]);
            }

            CommandHandler.executeCommand(cmd, e, args);
        }
    }


    public CommandHandler() {
        createWhitelist("DEFAULT");
    }

    /**
     * Returns the command class of a command requested. Aliases accounted.
     * @param command
     * @return
     */
    public static AbstractCommand getCommand(String command) {
        if(!commandsExecs.containsKey(command.toLowerCase())) return null;
        else return commandsExecs.get(command.toLowerCase());
    }

    public static Collection<AbstractCommand> getCommands() {
        return commands;
    }

    /**
     * Selects and executes the appropiate command. Default command help.
     * @param cmd the cmd string.
     * @param event the event passed along.
     * @param args the arguments provided.
     */
    public static void executeCommand(String cmd, MessageReceivedEvent event, String[] args) {
        AbstractCommand _cmd = getCommand(cmd);
        if(_cmd != null) {
            if(_cmd.canBeExecuted(event))
                _cmd.execute(event, args);
        }
    }


    /**
     * Registers a new text command to the command handling.
     * @param exec the Command executioner.
     */
    public static void registerTextCommand(AbstractCommand exec) {
        boolean couldadd = false;
        for(String cmd : exec.getAliases()) {
            if (!commandsExecs.containsKey(cmd.toLowerCase())) {
                commandsExecs.put(cmd.toLowerCase(), exec);
                couldadd = true;
            }
            else {
                System.err.println("DUPLICATE Command! " + exec.getAliases()[0] + " has already been claimed by another command.");
            }
        }
        if(couldadd) commands.add(exec);
    }

    /**
     * Creates a new command whitelist that allows for further use. Certain commands can then check if they are executable in certain channels.
     * Might fail if there already exists a whitelist with the given name.
     * @param whitelist_referral_name
     * @return True if the whitelist could be created, Otherwise false.
     */
    public static boolean createWhitelist(String whitelist_referral_name) {
        if(whitelists.containsKey(whitelist_referral_name)) return false;
        else whitelists.put(whitelist_referral_name, new CommandWhitelist());
        return true;
    }

    /**
     * Receives the default whitelislt
     */
    public static CommandWhitelist getDefaultWhitelist() {
        return whitelists.get("DEFAULT");
    }

    /**
     * Whitelists a certain channel. Note that this might be null if the whitelist does not exist.
     * @param whitelist
     */
    public static CommandWhitelist getWhitelist(String whitelist) {
        return whitelists.get(whitelist);
    }
}
