package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import General.Interaction.Laba;
import Server.Utility.CollectionMain;
import General.Collection.LabWork;
import Server.Utility.ResponseOutputer;

/**
 * Command "clear" clears collection.
 */
public class ClearCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public ClearCommand(CollectionMain collectionMain){
        super("clear", " ","очистить коллекцию");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg){
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            collectionMain.clearCollection();
            ResponseOutputer.appendln("Коллекция очищена!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return true;
    }
}
