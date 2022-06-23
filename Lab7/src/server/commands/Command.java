package server.commands;

import general.interaction.Profile;

/**
 * Interface for all commands.
 */
public interface Command {
    String getDescription();
    String getUsage();
    String getName();
    boolean execute(String str, Object objArg, Profile profile);
}
