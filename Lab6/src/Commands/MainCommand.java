package Commands;

/**
 * Start.Main Command Object methods, name and description.
 */
public abstract class MainCommand implements Command{
    private String name;
    private String description;

    /**
     * Constructor of the class.
     * @param name
     * @param description
     */
    public MainCommand(String name, String description){
        this.name = name;
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
}
