package Commands;

import Exceptions.WrongInputInScriptException;
import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;
import Collection.LabWork;
import Managers.McPolite;

import java.time.LocalDateTime;

/**
 * Command "add" adds new element to the collection, if it is min.
 */
public class AddIfMinCommand extends MainCommand{
    private CollectionMain collectionMain;
    private McPolite mcPolite;

    /**
     * Constructor of the class.
     */
    public AddIfMinCommand(CollectionMain collectionMain, McPolite mcPolite){
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
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
            LabWork labAdd = new LabWork(collectionMain.generateNextId(),
                    mcPolite.askName(), mcPolite.askCoordinates(), LocalDateTime.now(),
                    mcPolite.askMinimalPoint(), mcPolite.askDifficulty(), mcPolite.askDiscipline());
            if(collectionMain.collectionSize()==0)
            {
                collectionMain.addToCollection(labAdd);
                Console.println("Лабораторная работа успешно добавлена. Поздравляем!");
                return true;
            }
            else Console.printerror("Значение лабораторной больше, чем значение наименьшей из лабораторных!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        } catch (WrongInputInScriptException exception){

        }
        return false;
    }
}
