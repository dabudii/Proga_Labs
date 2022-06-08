package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import Server.Utility.CollectionMain;
import Server.Utility.ResponseOutputer;

/**
 * Command "save" saves collection to the current file.
 */
public class SaveCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public SaveCommand(CollectionMain collectionMain){
        super("save","", "сохранить коллекцию в файл");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg){
        try{
            if(!str.isEmpty()||objArg!=null){
                throw new WrongNumberOfElementsException();
            }
            collectionMain.saveCollection();
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
