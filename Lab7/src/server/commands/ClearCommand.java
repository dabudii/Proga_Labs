package server.commands;

import general.collection.LabWork;
import general.exceptions.DatabaseHandlingException;
import general.exceptions.PermissionDeniedException;
import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import server.utility.DatabaseCollectionMain;
import server.utility.ResponseOutputer;

/**
 * Command "clear" clears collection.
 */
public class ClearCommand extends MainCommand {
    private CollectionMain collectionMain;
    private DatabaseCollectionMain databaseCollectionMain;

    /**
     * Constructor of the class.
     */
    public ClearCommand(CollectionMain collectionMain, DatabaseCollectionMain databaseCollectionMain){
        super("clear", " ","очистить коллекцию");
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
            for(LabWork lab : collectionMain.getLabcollection()){
                if(!lab.getOwner().equals(profile)){
                    throw new PermissionDeniedException();
                }
                if(!databaseCollectionMain.checkLabworkUserId(lab.getId(),profile)){
                    throw new DatabaseHandlingException();
                }
            }
            databaseCollectionMain.clearCollection();
            collectionMain.clearCollection();
            ResponseOutputer.appendln("Коллекция очищена!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("Недостаточно прав для выполнения данной команды!");
        }
        return true;
    }
}
