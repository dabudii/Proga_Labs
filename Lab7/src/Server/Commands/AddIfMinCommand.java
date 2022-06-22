package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import General.Interaction.Laba;
import Server.Utility.CollectionMain;
import General.Collection.LabWork;
import Server.Utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "add" adds new element to the collection, if it is min.
 */
public class AddIfMinCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public AddIfMinCommand(CollectionMain collectionMain){
        super("add_if_min", "{element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
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
            LabWork labAdd = new LabWork(collectionMain.generateNextId(),
                    laba.getName(), laba.getCoordinates(), LocalDateTime.now(),
                    laba.getMinimalPoint(), laba.getDifficulty(), laba.getDiscipline());
            if(collectionMain.collectionSize()==0)
            {
                collectionMain.addToCollection(labAdd);
                ResponseOutputer.appendln("Лабораторная работа успешно добавлена. Поздравляем!");
                return true;
            }
            else ResponseOutputer.appenderror("Значение лабораторной больше, чем значение наименьшей из лабораторных!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
