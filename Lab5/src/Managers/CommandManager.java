package Managers;

import Commands.Command;
import Exceptions.HistoryEmptyException;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that operates the commands.
 */
public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 5;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private List<Command> commands = new ArrayList<>();
    private Command helpCommand;
    private Command infoCommand;
    private Command showCommand;
    private Command addCommand;
    private Command updateCommand;
    private Command removeByIdCommand;
    private Command clearCommand;
    private Command saveCommand;
    private Command executeScriptCommand;
    private Command exitCommand;
    private Command addIfMinCommand;
    private Command removeLowerCommand;
    private Command historyCommand;
    private Command removeAllByDifficultyCommand;
    private Command filterStartsWithNameCommand;
    private Command printDescendingCommand;

    /**
     * Constructor of the class.
     */
    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand, Command removeByIdCommand, Command clearCommand, Command saveCommand, Command executeScriptCommand, Command exitCommand, Command addIfMinCommand, Command removeLowerCommand, Command historyCommand, Command removeAllByDifficultyCommand, Command filterStartsWithNameCommand, Command printDescendingCommand){
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.historyCommand = historyCommand;
        this.removeAllByDifficultyCommand = removeAllByDifficultyCommand;
        this.filterStartsWithNameCommand = filterStartsWithNameCommand;
        this.printDescendingCommand = printDescendingCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(executeScriptCommand);
        commands.add(exitCommand);
        commands.add(addIfMinCommand);
        commands.add(removeLowerCommand);
        commands.add(historyCommand);
        commands.add(removeAllByDifficultyCommand);
        commands.add(filterStartsWithNameCommand);
        commands.add(printDescendingCommand);
    }

    /**
     * @return The command history.
     */
    public String[] getCommandHistory(){
        return commandHistory;
    }

    /**
     * @return List of the commands.
     */
    public List<Command> getCommands(){
        return commands;
    }

    /**
     * @param commandStore Command to add.
     */
    public void addToHistory(String commandStore){
        for(Command command : commands){
            if(command.getName().split(" ")[0].equals(commandStore)){
                for(int i=COMMAND_HISTORY_SIZE-1; i>0;i--){
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandStore;
            }
        }
    }

    /**
     * @param command Command, which is not found.
     * @return Command exit status.
     */
    public boolean noCommand(String command){
        return false;
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean help(String str){
        if(helpCommand.execute(str)){
            for(Command command : commands){
                Console.printable(command.getName(), command.getDescription());
            }
            return true;
        }
        else return false;
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean info(String str){
        return infoCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean show(String str){
        return showCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean add(String str){
        return addCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean update(String str){
        return updateCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeById(String str){
        return removeByIdCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean clear(String str){
        return clearCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean save(String str){
        return saveCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String str){
        return executeScriptCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean exit(String str){
        return exitCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean addIfMin(String str){
        return addIfMinCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeLower(String str){
        return removeLowerCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean history(String str){
        if(historyCommand.execute(str)){
            try{
                if(commandHistory.length == 0) {
                    throw new HistoryEmptyException();
                }
                Console.println("");
                for(int i=0; i<commandHistory.length;i++)
                {
                    if(commandHistory[i]!=null){
                        Console.println(" " + commandHistory[i]);
                    }
                }
                return true;
            } catch (HistoryEmptyException exception){
                Console.println("");
            }
        }
        return false;
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeAllByDifficulty(String str){
        return removeAllByDifficultyCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean filterStartsWithName(String str){
        return filterStartsWithNameCommand.execute(str);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean printDescending(String str){
        return printDescendingCommand.execute(str);
    }
}
