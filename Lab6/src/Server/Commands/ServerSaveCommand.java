package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import Server.Utility.CollectionMain;
import Server.Utility.ResponseOutputer;

/**
 * Command "save" saves collection to the current file.
 */
public class ServerSaveCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public ServerSaveCommand(CollectionMain collectionMain){
        super("server_save","", "сохранить коллекцию в файл");
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
            ResponseOutputer.appendln("Сохранение прошло успешно!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
