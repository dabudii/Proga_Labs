package Commands;

import Exceptions.WrongInputInScriptException;
import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;
import Collection.LabWork;
import Managers.McPolite;

import java.time.LocalDateTime;

/**
 * Command "add" adds new element to the collection.
 */
public class AddCommand extends MainCommand {
    private CollectionMain collectionMain;
    private McPolite mcPolite;

    /**
     * Constructor of the class.
     */
    public AddCommand(CollectionMain collectionMain, McPolite mcPolite){
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionMain = collectionMain;
        this.mcPolite = mcPolite;
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
            collectionMain.addToCollection(
                    new LabWork(collectionMain.generateNextId(),
                    mcPolite.askName(), mcPolite.askCoordinates(), LocalDateTime.now(),
                    mcPolite.askMinimalPoint(), mcPolite.askDifficulty(), mcPolite.askDiscipline())
            );
            Console.println("Лабораторная работа успешно добавлена. Поздравляем!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        } catch (WrongInputInScriptException exception){

        }
        return false;
    }
}
