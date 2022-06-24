package server.utility;

import general.interaction.Profile;
import server.commands.Command;
import general.exceptions.HistoryEmptyException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    private Command executeScriptCommand;
    private Command exitCommand;
    private Command addIfMinCommand;
    private Command removeLowerCommand;
    private Command historyCommand;
    private Command removeAllByDifficultyCommand;
    private Command filterStartsWithNameCommand;
    private Command printDescendingCommand;
    private Command serverSaveCommand;
    private Command loginCommand;
    private Command registerCommand;
    private Command serverExitCommand;
    private ReadWriteLock locker = new ReentrantReadWriteLock();
    /**
     * Constructor of the class.
     */
    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand,
                          Command updateCommand, Command removeByIdCommand, Command clearCommand, Command executeScriptCommand,
                          Command exitCommand, Command addIfMinCommand, Command removeLowerCommand, Command historyCommand,
                          Command removeAllByDifficultyCommand, Command filterStartsWithNameCommand,
                          Command printDescendingCommand, Command loginCommand, Command registerCommand,Command serverExitCommand){
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.historyCommand = historyCommand;
        this.removeAllByDifficultyCommand = removeAllByDifficultyCommand;
        this.filterStartsWithNameCommand = filterStartsWithNameCommand;
        this.printDescendingCommand = printDescendingCommand;
        this.loginCommand = loginCommand;
        this.registerCommand = registerCommand;
        this.serverExitCommand = serverExitCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
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
    public void addToHistory(String commandStore, Profile profile){
        locker.writeLock().lock();
        try{
        for(Command command : commands){
            if(command.getName().split(" ")[0].equals(commandStore)){
                for(int i=COMMAND_HISTORY_SIZE-1; i>0;i--){
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandStore;
            }
        }
        } finally {
            locker.writeLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean help(String str, Object objArg, Profile profile){
        locker.readLock().lock();
        try{
        if(helpCommand.execute(str,objArg, profile)){
            for(Command command : commands){
                if(!command.getName().equals("server_save")){
                    ResponseOutputer.appendtable(command.getName()+" "+command.getUsage(),command.getDescription());
                }
            }
            return true;
        }
        else return false;
        } finally {
            locker.readLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean info(String str, Object objArg, Profile profile){
        locker.readLock().lock();
        try {
            return infoCommand.execute(str, objArg, profile);
        } finally {
            locker.readLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean show(String str,Object objArg, Profile profile){
        locker.readLock().lock();
        try {
            return showCommand.execute(str, objArg, profile);
        } finally {
            locker.readLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean add(String str,Object objArg, Profile profile){
        locker.writeLock().lock();
        try {
            return addCommand.execute(str, objArg, profile);
        } finally {
            locker.writeLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean update(String str,Object objArg, Profile profile){
        locker.writeLock().lock();
        try {
        return updateCommand.execute(str,objArg, profile);
    } finally {
        locker.writeLock().unlock();
    }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeById(String str,Object objArg, Profile profile){
        locker.writeLock().lock();
        try {
            return removeByIdCommand.execute(str, objArg, profile);
        } finally {
            locker.writeLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean clear(String str,Object objArg, Profile profile){
        locker.writeLock().lock();
        try {
            return clearCommand.execute(str, objArg, profile);
        } finally {
            locker.writeLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String str,Object objArg, Profile profile){
        return executeScriptCommand.execute(str,objArg, profile);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean exit(String str,Object objArg, Profile profile){
        return exitCommand.execute(str,objArg, profile);
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean addIfMin(String str,Object objArg, Profile profile){
        locker.writeLock().lock();
        try {
            return addIfMinCommand.execute(str, objArg, profile);
        } finally {
            locker.writeLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeLower(String str,Object objArg, Profile profile){
        locker.writeLock().lock();
        try {
            return removeLowerCommand.execute(str, objArg, profile);
        } finally {
            locker.writeLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean history(String str, Object objArg, Profile profile){
        locker.readLock().lock();
        try {
            if (historyCommand.execute(str, objArg, profile)) {
                try {
                    if (commandHistory.length == 0) {
                        throw new HistoryEmptyException();
                    }
                    for (String command : commandHistory) {
                        if (command != null) {
                            ResponseOutputer.appendln(" " + command);
                        }
                    }
                    return true;
                } catch (HistoryEmptyException exception) {
                    ResponseOutputer.appendln("Ни одна команда еще не использовалась!");
                }
            }
            return false;
        } finally {
            locker.readLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean removeAllByDifficulty(String str,Object objArg, Profile profile){
        locker.writeLock().lock();
        try {
            return removeAllByDifficultyCommand.execute(str, objArg, profile);
        } finally {
            locker.writeLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean filterStartsWithName(String str,Object objArg, Profile profile){
        locker.readLock().lock();
        try {
            return filterStartsWithNameCommand.execute(str, objArg, profile);
        } finally {
            locker.readLock().unlock();
        }
    }

    /**
     * @param str Its argument.
     * @return Command exit status.
     */
    public boolean printDescending(String str,Object objArg, Profile profile){
        locker.readLock().lock();
        try {
            return printDescendingCommand.execute(str, objArg, profile);
        } finally {
            locker.readLock().unlock();
        }
    }

    public boolean login(String stringArgument, Object objectArgument, Profile profile) {
        return loginCommand.execute(stringArgument, objectArgument, profile);
    }

    public boolean register(String stringArgument, Object objectArgument, Profile profile) {
        return registerCommand.execute(stringArgument, objectArgument, profile);
    }

    public boolean serverExit(String stringArgument, Object objectArgument, Profile profile) {
        return serverExitCommand.execute(stringArgument, objectArgument, profile);
    }
}
