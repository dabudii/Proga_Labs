package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;

/**
 * Command "show" writes all elements of collection.
 */
public class ShowCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public ShowCommand(CollectionMain collectionMain){
        super("show", "вывести все элементы коллекции");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str) {
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            collectionMain.showCollection();
            Console.println("Показ окончен!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return false;
    }
}
