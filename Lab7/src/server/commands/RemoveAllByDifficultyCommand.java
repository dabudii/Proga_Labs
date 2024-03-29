package server.commands;

import general.exceptions.DatabaseHandlingException;
import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import general.collection.Difficulty;
import server.utility.DatabaseCollectionMain;
import server.utility.ResponseOutputer;

/**
 * Command "remove_all_by_difficulty" deletes the elements of the colelction, which difficulty is equal to the input difficulty.
 */
public class RemoveAllByDifficultyCommand extends MainCommand {
    private CollectionMain collectionMain;
    private DatabaseCollectionMain databaseCollectionMain;

    /**
     * Constructor of the class.
     */
    public RemoveAllByDifficultyCommand(CollectionMain collectionMain, DatabaseCollectionMain databaseCollectionMain){
        super("remove_all_by_difficulty","<difficulty>", "удалить из коллекции все элементы, значение поля difficulty которого эквивалентно заданному");
        this.collectionMain = collectionMain;
        this.databaseCollectionMain = databaseCollectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile){
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
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        }
        return false;
    }
}
