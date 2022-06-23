package server.commands;

import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import general.collection.LabWork;
import server.utility.ResponseOutputer;

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
    public boolean execute(String str, Object objArg, Profile profile) {
        try{
            if(!str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            TreeSet<LabWork> copyCollection = new TreeSet<>(collectionMain.getLabcollection());
            for(LabWork lab : copyCollection){
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
