package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import Server.Utility.CollectionMain;
import Server.Utility.ResponseOutputer;

/**
 * Command "show" writes all elements of collection.
 */
public class ShowCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public ShowCommand(CollectionMain collectionMain){
        super("show","", "вывести все элементы коллекции");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg) {
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            ResponseOutputer.appendln(collectionMain.showCollection());
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
