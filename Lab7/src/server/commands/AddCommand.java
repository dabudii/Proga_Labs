package server.commands;

import general.exceptions.DatabaseHandlingException;
import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Laba;
import general.interaction.Profile;
import server.utility.CollectionMain;
import general.collection.LabWork;
import server.utility.DatabaseCollectionMain;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "add" adds new element to the collection.
 */
public class  AddCommand extends MainCommand {
    private CollectionMain collectionMain;
    private DatabaseCollectionMain databaseCollectionMain;

    /**
     * Constructor of the class.
     */
    public AddCommand(CollectionMain collectionMain, DatabaseCollectionMain databaseCollectionMain){
        super("add", "{element}", "добавить новый элемент в коллекцию");
        this.collectionMain = collectionMain;
        this.databaseCollectionMain = databaseCollectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile){
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            Laba laba = (Laba) objArg;
            collectionMain.addToCollection(databaseCollectionMain.insertLab(laba, profile));
            ResponseOutputer.appendln("Лабораторная работа успешно добавлена. Поздравляем!");
            return true;
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
