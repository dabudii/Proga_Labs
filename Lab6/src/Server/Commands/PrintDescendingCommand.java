package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import General.Interaction.Laba;
import Server.Utility.CollectionMain;
import General.Collection.LabWork;
import Server.Utility.ResponseOutputer;

import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Command "print_descending" writes the elements of the collection in descending order.
 */
public class PrintDescendingCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public PrintDescendingCommand(CollectionMain collectionMain){
        super("print_descending","", "вывести элементы коллекции в порядке убывания");
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
            NavigableSet<LabWork> copyCollection = new TreeSet<>(collectionMain.getLabcollection());
            for(LabWork lab : copyCollection.descendingSet()){
                ResponseOutputer.appendln(lab.toString());
            }
            ResponseOutputer.appendln("Показ окончен!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appenderror("Нет аргументов в "+getName());
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
