package server.commands;

import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import server.utility.ResponseOutputer;

/**
 * Command "exit" closes the programme without saving.
 */
public class ExitCommand extends MainCommand {

    private CollectionMain collectionMain;
    /**
     * Constructor of the class.
     */
    public ExitCommand(CollectionMain collectionMain){
        super("exit"," ", "завершить программу(без сохранения в файл)");
        this.collectionMain = collectionMain;
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
        } catch(WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
