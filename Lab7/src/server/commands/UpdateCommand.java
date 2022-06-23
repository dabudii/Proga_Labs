package server.commands;

import general.exceptions.*;
import general.interaction.Laba;
import general.interaction.Profile;
import server.utility.CollectionMain;
import general.collection.*;
import server.utility.DatabaseCollectionMain;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "update" updates element of the collection, which id is equal to the input id.
 */
public class UpdateCommand extends MainCommand {
    private CollectionMain collectionMain;
    private DatabaseCollectionMain databaseCollectionMain;

    /**
     * Constructor of the class.
     */
    public UpdateCommand(CollectionMain collectionMain){
        super("update", "<ID>", "обновить значение элемента коллекции по ID");
        this.collectionMain = collectionMain;
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
            if(collectionMain.collectionSize()==0){
                throw new CollectionEmptyException();
            }
            long id = Long.parseLong(str);
            LabWork oldLab = collectionMain.getById(id);
            if (!databaseCollectionMain.checkLabworkUserId(oldLab.getId(), profile)) {
                throw new ManualDatabaseEditException();
            }
            if(oldLab == null){
                throw new LabNotFoundException();
            }
            Laba laba = (Laba) objArg;
            databaseCollectionMain.updateLabworkById(id, laba);

            String name = laba.getName()==null ? oldLab.getName() : laba.getName();
            Coordinates coordinates = laba.getCoordinates()==null ? oldLab.getCoordinates() : laba.getCoordinates();
            LocalDateTime creationDate = oldLab.getCreationDate();
            Float minimalPoint = laba.getMinimalPoint()<=0 ? oldLab.getMinimalPoint() : laba.getMinimalPoint();
            Difficulty difficulty = laba.getDifficulty()==null ? oldLab.getDifficulty() : laba.getDifficulty();
            Discipline discipline = laba.getDiscipline()==null ? oldLab.getDiscipline() : laba.getDiscipline();

            collectionMain.removeFromCollection(oldLab);
            collectionMain.addToCollection(new LabWork(
                    id, name,
                    coordinates, creationDate,
                    minimalPoint,
                    difficulty,
                    discipline,
                    profile));

            ResponseOutputer.appendln("Лаборатораня успешно изменена.");
            return true;
        } catch (LabNotFoundException exception){
            ResponseOutputer.appenderror("Лабораторной с таким ID не найдено!");
        } catch(WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (CollectionEmptyException exception){
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представляться числом!");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("Произошло прямое изменение базы данных!");
            ResponseOutputer.appendln("Перезапустите клиент для избежания возможных ошибок.");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        }
        return false;
    }
}
