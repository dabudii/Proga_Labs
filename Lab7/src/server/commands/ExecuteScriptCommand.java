package server.commands;

import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.ResponseOutputer;

/**
 * Command "execute_script" executes script from a file.
 */
public class ExecuteScriptCommand extends MainCommand {

    /**
     * Constructor of the class.
     */
    public ExecuteScriptCommand(){
        super("execute_script", "<file_name>", "считать и исполнить скрипт из указанного файла");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile){
        try{
            if(str.isEmpty() || objArg!=null){
                throw new WrongNumberOfElementsException();
            }
            ResponseOutputer.appendln("Выполняю скрипт " + str + ".");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
