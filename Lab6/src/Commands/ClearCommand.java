package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;

/**
 * Command "clear" clears collection.
 */
public class ClearCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public ClearCommand(CollectionMain collectionMain){
        super("clear", "очистить коллекцию");
        this.collectionMain = collectionMain;
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
            collectionMain.clearCollection();
            Console.println("Коллекция очищена!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return true;
    }
}
