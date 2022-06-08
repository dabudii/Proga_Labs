package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import Server.Utility.ResponseOutputer;

/**
 * Command "exit" closes the programme without saving.
 */
public class ExitCommand extends MainCommand {

    /**
     * Constructor of the class.
     */
    public ExitCommand(){
        super("exit"," ", "завершить программу(без сохранения в файл)");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg){
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            return true;
        } catch(WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
