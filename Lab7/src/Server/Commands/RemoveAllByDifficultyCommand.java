package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import Server.Utility.CollectionMain;
import General.Collection.Difficulty;
import Server.Utility.ResponseOutputer;

/**
 * Command "remove_all_by_difficulty" deletes the elements of the colelction, which difficulty is equal to the input difficulty.
 */
public class RemoveAllByDifficultyCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public RemoveAllByDifficultyCommand(CollectionMain collectionMain){
        super("remove_all_by_difficulty","<difficulty>", "удалить из коллекции все элементы, значение поля difficulty которого эквивалентно заданному");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg){
        try{
            if(str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            collectionMain.removeAllByDifficulty(Difficulty.values()[Integer.parseInt(str)-1]);
            ResponseOutputer.appendln("Удаление произошло успешно! Поздравляем!");
            return true;
        } catch (ArrayIndexOutOfBoundsException exception){
            ResponseOutputer.appenderror("Не указаны аргументы команды!");
        } catch (IllegalArgumentException exception){
            ResponseOutputer.appenderror("Выбранной сложности нет в перечне.");
            ResponseOutputer.appendln("Вот список всех сложностей:");
            ResponseOutputer.appendln(Difficulty.nameList());
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
