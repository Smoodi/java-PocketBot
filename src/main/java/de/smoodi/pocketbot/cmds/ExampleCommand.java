package de.smoodi.pocketbot.cmds;

import de.smoodi.botutils.AbstractCommand;
import de.smoodi.botutils.BOTUTILSCONFIG;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ExampleCommand extends AbstractCommand {

    @Override
    public void execute(MessageReceivedEvent ev, String[] args) {
        ev.getMessage().getChannel().sendMessage("We answered our command.").queue();
    }

    @Override
    public String[] getAliases() {
        return new String[] {
                "test",
                "coolcommand"
        };
    }

    @Override
    public String getShortDescription() {
        return "A short test command ;)";
    }

    @Override
    public String getUsage() {
        return "Just use " + BOTUTILSCONFIG.getBotPrefix() + "test!";
    }

    @Override
    public String getDescription() {
        return "An amazing command, isn't it?";
    }
}
