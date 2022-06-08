package Server.Utility;

import Server.Commands.Command;
import General.Exceptions.HistoryEmptyException;

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

    private Command serverExitCommand;

    /**
     * Constructor of the class.
     */
    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand, Command removeByIdCommand, Command clearCommand, Command saveCommand, Command executeScriptCommand, Command exitCommand, Command addIfMinCommand, Command removeLowerCommand, Command historyCommand, Command removeAllByDifficultyCommand, Command filterStartsWithNameCommand, Command printDescendingCommand, Command serverExitCommand){
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
        this.serverExitCommand = serverExitCommand;

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
        commands.add(serverExitCommand);
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
    public boolean help(String str, Object objArg){
        if(helpCommand.execute(str,objArg)){
            for(Command command : commands){
                ResponseOutputer.appendtable(command.getName()+" "+command.getUsage(),command.getDescription());
            }
            return true;
        }
        else return false;
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean info(String str, Object objArg){
        return infoCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean show(String str,Object objArg){
        return showCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean add(String str,Object objArg){
        return addCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean update(String str,Object objArg){
        return updateCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeById(String str,Object objArg){
        return removeByIdCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean clear(String str,Object objArg){
        return clearCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean save(String str,Object objArg){
        return saveCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String str,Object objArg){
        return executeScriptCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean exit(String str,Object objArg){
        return exitCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean addIfMin(String str,Object objArg){
        return addIfMinCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeLower(String str,Object objArg){
        return removeLowerCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean history(String str, Object objArg){
        if(historyCommand.execute(str,objArg)){
            try{
                if(commandHistory.length == 0) {
                    throw new HistoryEmptyException();
                }
                for(String command : commandHistory)
                {
                    if(command!=null){
                        ResponseOutputer.appendln(" " + command);
                    }
                }
                return true;
            } catch (HistoryEmptyException exception){
                ResponseOutputer.appendln("Ни одна команда еще не использовалась!");
            }
        }
        return false;
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeAllByDifficulty(String str,Object objArg){
        return removeAllByDifficultyCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean filterStartsWithName(String str,Object objArg){
        return filterStartsWithNameCommand.execute(str,objArg);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean printDescending(String str,Object objArg){
        return printDescendingCommand.execute(str,objArg);
    }

    /**
     * Executes needed command.
     *
     * @param str Its string argument.
     * @return Command exit status.
     */
    public boolean serverExit(String str,Object objArg) {
        return serverExitCommand.execute(str,objArg);
    }
}
