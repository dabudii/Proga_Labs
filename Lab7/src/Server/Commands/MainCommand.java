package Server.Commands;

/**
 * Start.Main Command Object methods, name and description.
 */
public abstract class MainCommand implements Command {
    private String name;
    private String usage;
    private String description;

    /**
     * Constructor of the class.
     * @param name
     * @param usage
     * @param description
     */
    public MainCommand(String name, String usage, String description){
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    /**
     * @return Name of the command.
     */
    public String getName(){
        return name;
    }

    /**
     * @return Description of the command.
     */
    public String getDescription(){
        return description;
    }

    /**
     * @return Usage of the command.
     */
    public String getUsage() {
        return usage;
    }
}
