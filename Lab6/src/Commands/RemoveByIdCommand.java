package Commands;

import Exceptions.CollectionEmptyException;
import Exceptions.LabNotFoundException;
import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;
import Collection.LabWork;

/**
 * Command "remove_by_id" deletes the element of the collection, which id is equal to the input id.
 */
public class RemoveByIdCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public RemoveByIdCommand(CollectionMain collectionMain){
        super("remove_by_id <id>", "удалить элемент из коллекции по id");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str) {
        try{
            if(str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            if(collectionMain.collectionSize()==0){
                throw new CollectionEmptyException();
            }
            long id = Long.parseLong(str);
            LabWork labRemove = collectionMain.getById(id);
            if(labRemove == null){
                throw new LabNotFoundException();
            }
            collectionMain.removeFromCollection(labRemove);
            Console.println("Лабораторная успешно удалена! Поздравляю!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        } catch (CollectionEmptyException exception){
            Console.printerror("Коллекция пуста!");
        } catch (LabNotFoundException exception){
            Console.printerror("Лабораторной с таким ID не найдено!");
        } catch (NumberFormatException exception){
            Console.printerror("ID должен представляться числом!");
        }
        return false;
    }
}
