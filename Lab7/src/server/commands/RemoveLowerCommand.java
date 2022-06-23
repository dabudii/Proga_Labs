package server.commands;

import general.exceptions.*;
import general.interaction.Laba;
import general.interaction.Profile;
import server.utility.CollectionMain;
import general.collection.LabWork;
import server.utility.DatabaseCollectionMain;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "remove_lower" deletes elements of the collection that lower than the input element.
 */
public class RemoveLowerCommand extends MainCommand {
    private CollectionMain collectionMain;
    private DatabaseCollectionMain databaseCollectionMain;
    /**
     * Constructor of the class.
     */
    public RemoveLowerCommand(CollectionMain collectionMain){
        super("remove_lower","{element}", "удалить из коллекции все элементы, меньшие, чем заданный");
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
            if(collectionMain.collectionSize()==0) {
                throw new CollectionEmptyException();
            }
            Laba laba = (Laba) objArg;
            LabWork labCollection = collectionMain.getByValue(databaseCollectionMain.insertLab(laba, profile));
            if(labCollection == null){
                throw new LabNotFoundException();
            }
            collectionMain.removeLower(labCollection);
            ResponseOutputer.appendln("Лабораторные успешно удалены! Поздравляем!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (CollectionEmptyException exception){
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (LabNotFoundException exception){
            ResponseOutputer.appenderror("Лабораторной с такими характеристиками не найдено!");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        }
        return false;
    }
}
