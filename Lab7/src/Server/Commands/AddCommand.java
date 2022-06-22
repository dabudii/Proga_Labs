package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import General.Interaction.Laba;
import Server.Utility.CollectionMain;
import General.Collection.LabWork;
import Server.Utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "add" adds new element to the collection.
 */
public class  AddCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public AddCommand(CollectionMain collectionMain){
        super("add", "{element}", "добавить новый элемент в коллекцию");
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
            Laba laba = (Laba) objArg;
            collectionMain.addToCollection(
                    new LabWork(collectionMain.generateNextId(),
                            laba.getName(), laba.getCoordinates(), LocalDateTime.now(),
                            laba.getMinimalPoint(), laba.getDifficulty(), laba.getDiscipline())
            );
            ResponseOutputer.appendln("Лабораторная работа успешно добавлена. Поздравляем!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
