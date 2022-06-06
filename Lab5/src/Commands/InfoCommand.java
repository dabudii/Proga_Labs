package Commands;

import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;

import java.time.LocalDateTime;

/**
 * Command "info" writes the info about the collection.
 */
public class InfoCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public InfoCommand(CollectionMain collectionMain){
        super("info", "вывести информацию о коллекции");
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

            LocalDateTime lastInitTime = collectionMain.getLastInitTime();
            String lastInitTimeString;
            if(lastInitTime == null){
                lastInitTimeString = "в данной сессии инициализации еще не происходило";
            }
            else lastInitTimeString = lastInitTime.toLocalDate().toString()+" "+lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionMain.getLastSaveTime();
            String lastSaveTimeString;
            if(lastSaveTime == null){
                 lastSaveTimeString = "в данной сессии сохранения еще не происходило";
            }
            else lastSaveTimeString = lastSaveTime.toLocalDate().toString()+" "+lastSaveTime.toLocalTime().toString();

            Console.println("Сведения о коллекции:");
            Console.println("Тип " + collectionMain.collectionType());
            Console.println("Количество элементов: " + collectionMain.collectionSize());
            Console.println("Последнее время инициализации: " +lastInitTimeString);
            Console.println("Последнее время сохранения: " +lastSaveTimeString);
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        }
        return false;
    }
}
