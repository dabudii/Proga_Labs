package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.Console;

/**
 * Command "execute_script" executes script from a file.
 */
public class ExecuteScriptCommand extends MainCommand{

    /**
     * Constructor of the class.
     */
    public ExecuteScriptCommand(){
        super("execute_script <file_name>", "считать и исполнить скрипт из указанного файла");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str){
        try{
            if(str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            Console.println("Выполняю скрипт " + str + ".");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return false;
    }
}
