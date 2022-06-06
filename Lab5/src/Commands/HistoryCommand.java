package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.Console;

/**
 * Command "history" writes the history of the used commands.
 */
public class HistoryCommand extends MainCommand{

    /**
     * Constructor of the class.
     */
    public HistoryCommand(){
        super("history", "вывести историю использованных команд");
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
