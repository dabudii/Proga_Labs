package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.Console;

/**
 * Command "help" writes list with the commands and their description.
 */
public class HelpCommand extends MainCommand {

    /**
     * Constructor of the class.
     */
    public HelpCommand(){
        super("help", "вывести справку по доступным коммандам");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str){
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return false;
    }
}
