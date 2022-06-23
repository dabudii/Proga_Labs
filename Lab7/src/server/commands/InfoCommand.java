package server.commands;

import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "info" writes the info about the collection.
 */
public class InfoCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public InfoCommand(CollectionMain collectionMain){
        super("info","", "вывести информацию о коллекции");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile) {
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

            ResponseOutputer.appendln("Сведения о коллекции:");
            ResponseOutputer.appendln("Тип " + collectionMain.collectionType());
            ResponseOutputer.appendln("Количество элементов: " + collectionMain.collectionSize());
            ResponseOutputer.appendln("Последнее время инициализации: " +lastInitTimeString);
            ResponseOutputer.appendln("Последнее время сохранения: " +lastSaveTimeString);
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+""+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
