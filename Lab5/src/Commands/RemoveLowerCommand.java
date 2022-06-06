package Commands;

import Exceptions.CollectionEmptyException;
import Exceptions.LabNotFoundException;
import Exceptions.WrongInputInScriptException;
import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;
import Collection.LabWork;
import Managers.McPolite;

import java.time.LocalDateTime;

/**
 * Command "remove_lower" deletes elements of the collection that lower than the input element.
 */
public class RemoveLowerCommand extends MainCommand{
    private CollectionMain collectionMain;
    private McPolite mcPolite;

    /**
     * Constructor of the class.
     */
    public RemoveLowerCommand(CollectionMain collectionMain, McPolite mcPolite){
        super("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionMain = collectionMain;
        this.mcPolite = mcPolite;
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
            if(collectionMain.collectionSize()==0) {
                throw new CollectionEmptyException();
            }
            LabWork labToFind = new LabWork(collectionMain.generateNextId(),
                    mcPolite.askName(), mcPolite.askCoordinates(), LocalDateTime.now(),
                    mcPolite.askMinimalPoint(), mcPolite.askDifficulty(), mcPolite.askDiscipline());
            LabWork labCollection = collectionMain.getByValue(labToFind);
            if(labCollection == null){
                throw new LabNotFoundException();
            }
            collectionMain.removeLower(labCollection);
            Console.println("Лабораторные успешно удалены! Поздравляем!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        } catch (CollectionEmptyException exception){
            Console.printerror("Коллекция пуста!");
        } catch (LabNotFoundException exception){
            Console.printerror("Лабораторной с такими характеристиками не найдено!");
        } catch (WrongInputInScriptException exception) {
        }
        return false;
    }
}
