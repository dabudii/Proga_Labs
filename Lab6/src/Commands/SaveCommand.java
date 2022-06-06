package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;

/**
 * Command "save" saves collection to the current file.
 */
public class SaveCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public SaveCommand(CollectionMain collectionMain){
        super("save", "сохранить коллекцию в файл");
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
            collectionMain.saveCollection();
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return false;
    }
}
