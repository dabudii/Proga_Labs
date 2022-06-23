package general.interaction;

import java.io.Serializable;

/**
 * Class for get request value.
 */
public class Request implements Serializable {
    private String commandName;
    private String commandStringArgument;
    private Serializable commandObjectArgument;
    private Profile profile;

    public Request(String commandName, String commandStringArgument, Serializable commandObjectArgument, Profile profile) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.commandObjectArgument = commandObjectArgument;
        this.profile = profile;
    }

    public Request(String commandName, String commandStringArgument, Profile profile) {
        this(commandName, commandStringArgument, null, profile);
    }

    public Request(Profile profile) {
        this("", "", profile);
    }

    /**
     * @return Command name.
     */
    public String getCommandName() {
        return commandName;
    }

    public void setCommand(String str){
        this.commandName = commandName;
    }

    /**
     * @return Command string argument.
     */
    public String getCommandStringArgument() {
        return commandStringArgument;
    }

    /**
     * @return Command object argument.
     */
    public Object getCommandObjectArgument() {
        return commandObjectArgument;
    }

    /**
     * @return User of command.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @return Is this request empty.
     */
    public boolean isEmpty() {
        return commandName.isEmpty() && commandStringArgument.isEmpty() && commandObjectArgument == null && profile == null;
    }

    @Override
    public String toString() {
        return "Request[" + commandName + ", " + commandStringArgument + ", " + commandObjectArgument + ", " + profile + "]";
    }
}