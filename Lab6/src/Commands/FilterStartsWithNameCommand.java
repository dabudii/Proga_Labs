package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;

/**
 * Command "filter_starts_with_name" writes the elements, which names are equal to the input name`.
 */
public class FilterStartsWithNameCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public FilterStartsWithNameCommand(CollectionMain collectionMain){
        super("filter_starts_with_name","вывести элементы, значение поля name которых начинается с заданной подстроки");
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
            collectionMain.filterStartsWithName(str);
            Console.println("Элементы выведены!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        } catch (ArrayIndexOutOfBoundsException exception){
            Console.printerror("Не указаны аргументы команды!");
        } catch (IllegalArgumentException exception){
            Console.printerror("Выбранного имени нет в перечне.");
        }
        return false;
    }
}
