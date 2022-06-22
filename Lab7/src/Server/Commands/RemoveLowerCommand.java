package Server.Commands;

import General.Exceptions.*;
import General.Interaction.Laba;
import General.Utility.Printer;
import Server.Utility.CollectionMain;
import General.Collection.LabWork;
import Server.Utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "remove_lower" deletes elements of the collection that lower than the input element.
 */
public class RemoveLowerCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public RemoveLowerCommand(CollectionMain collectionMain){
        super("remove_lower","{element}", "удалить из коллекции все элементы, меньшие, чем заданный");
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
            if(collectionMain.collectionSize()==0) {
                throw new CollectionEmptyException();
            }
            Laba laba = (Laba) objArg;
            LabWork labToFind = new LabWork(collectionMain.generateNextId(),
                    laba.getName(), laba.getCoordinates(), LocalDateTime.now(),
                    laba.getMinimalPoint(), laba.getDifficulty(), laba.getDiscipline());
            LabWork labCollection = collectionMain.getByValue(labToFind);
            if(labCollection == null){
                throw new LabNotFoundException();
            }
            collectionMain.removeLower(labCollection);
            ResponseOutputer.appendln("Лабораторные успешно удалены! Поздравляем!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (CollectionEmptyException exception){
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (LabNotFoundException exception){
            ResponseOutputer.appenderror("Лабораторной с такими характеристиками не найдено!");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
