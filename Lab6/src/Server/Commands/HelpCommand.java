package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import Server.Utility.ResponseOutputer;

/**
 * Command "help" writes list with the commands and their description.
 */
public class HelpCommand extends MainCommand {

    /**
     * Constructor of the class.
     */
    public HelpCommand(){
        super("help","", "вывести справку по доступным коммандам");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg){
        try{
            if(!str.isEmpty()||objArg!=null){
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
