package Commands;

import Collection.LabWork;
import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;

import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Command "print_descending" writes the elements of the collection in descending order.
 */
public class PrintDescendingCommand extends MainCommand{
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public PrintDescendingCommand(CollectionMain collectionMain){
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str) {
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            NavigableSet<LabWork> copyCollection = new TreeSet<>(collectionMain.getLabcollection());
            for(LabWork lab : copyCollection.descendingSet()){
                Console.println(lab.toString());
            }
            Console.println("Показ окончен!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            Console.printerror("Нет аргументов в "+getName());
        }
        return false;
    }
}
