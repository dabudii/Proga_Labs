package server.commands;

import general.exceptions.DatabaseHandlingException;
import general.exceptions.WrongNumberOfElementsException;
import general.exceptions.CollectionEmptyException;
import general.exceptions.LabNotFoundException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import general.collection.LabWork;
import server.utility.DatabaseCollectionMain;
import server.utility.ResponseOutputer;

/**
 * Command "remove_by_id" deletes the element of the collection, which id is equal to the input id.
 */
public class RemoveByIdCommand extends MainCommand {
    private CollectionMain collectionMain;
    private DatabaseCollectionMain databaseCollectionMain;

    /**
     * Constructor of the class.
     */
    public RemoveByIdCommand(CollectionMain collectionMain, DatabaseCollectionMain databaseCollectionMain){
        super("remove_by_id", "<id>", "удалить элемент из коллекции по id");
        this.collectionMain = collectionMain;
        this.databaseCollectionMain = databaseCollectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile) {
        try{
            if(str.isEmpty()||objArg!=null){
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
            databaseCollectionMain.deleteLabworkById(id);
            ResponseOutputer.appendln("Лабораторная успешно удалена! Поздравляю!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (CollectionEmptyException exception){
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (LabNotFoundException exception){
            ResponseOutputer.appenderror("Лабораторной с таким ID не найдено!");
        } catch (NumberFormatException exception){
            ResponseOutputer.appenderror("ID должен представляться числом!");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException e) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        }
        return false;
    }
}
