package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.Console;

/**
 * Command "exit" closes the programme without saving.
 */
public class ExitCommand extends MainCommand{

    /**
     * Constructor of the class.
     */
    public ExitCommand(){
        super("exit", "завершить программу(без сохранения в файл)");
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
        } catch(WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return false;
    }
}
