package Commands;

import Collection.Difficulty;
import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;

/**
 * Command "remove_all_by_difficulty" deletes the elements of the colelction, which difficulty is equal to the input difficulty.
 */
public class RemoveAllByDifficultyCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public RemoveAllByDifficultyCommand(CollectionMain collectionMain){
        super("remove_all_by_difficulty", "удалить из коллекции все элементы, значение поля difficulty которого эквивалентно заданному");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str){
        try{
            if(str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            collectionMain.removeAllByDifficulty(Difficulty.values()[Integer.parseInt(str)-1]);
            Console.println("Удаление произошло успешно! Поздравляем!");
            return true;
        } catch (ArrayIndexOutOfBoundsException exception){
            Console.printerror("Не указаны аргументы команды!");
        } catch (IllegalArgumentException exception){
            Console.printerror("Выбранной сложности нет в перечне.");
            Console.println("Вот список всех сложностей:");
            Console.println(Difficulty.nameList());
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return false;
    }
}
