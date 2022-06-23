package server.commands;

import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.ResponseOutputer;

/**
 * Command "history" writes the history of the used commands.
 */
public class HistoryCommand extends MainCommand {

    /**
     * Constructor of the class.
     */
    public HistoryCommand(){
        super("history","", "вывести историю использованных команд");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile){
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
