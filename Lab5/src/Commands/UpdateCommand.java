package Commands;

import Collection.*;
import Exceptions.CollectionEmptyException;
import Exceptions.LabNotFoundException;
import Exceptions.WrongInputInScriptException;
import Exceptions.WrongNumberOfElementsException;
import Managers.CollectionMain;
import Managers.Console;
import Managers.McPolite;

import java.time.LocalDateTime;

/**
 * Command "update" updates element of the collection, which id is equal to the input id.
 */
public class UpdateCommand extends MainCommand{
    private CollectionMain collectionMain;
    private McPolite mcPolite;

    /**
     * Constructor of the class.
     */
    public UpdateCommand(CollectionMain collectionMain, McPolite mcPolite){
        super("update <ID>", "обновить значение элемента коллекции по ID");
        this.collectionMain = collectionMain;
        this.mcPolite = mcPolite;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str){
        try{
            if(str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            if(collectionMain.collectionSize()==0){
                throw new CollectionEmptyException();
            }
            long id = Long.parseLong(str);
            LabWork oldLab = collectionMain.getById(id);
            if(oldLab == null){
                throw new LabNotFoundException();
            }

            String name = oldLab.getName();
            Coordinates coordinates = oldLab.getCoordinates();
            LocalDateTime creationDate = oldLab.getCreationDate();
            Float minimalPoint = oldLab.getMinimalPoint();
            Difficulty difficulty = oldLab.getDifficulty();
            Discipline discipline = oldLab.getDiscipline();

            collectionMain.removeFromCollection(oldLab);

            if(mcPolite.ask("Хотите изменить имя у лабораторной?")){
                name = mcPolite.askName();
            }
            if(mcPolite.ask("Хотите изменить координаты лабораторной?")){
                coordinates = mcPolite.askCoordinates();
            }
            if(mcPolite.ask("Хотите изменить минимальное количество баллов за лабораторную?")){
                minimalPoint = mcPolite.askMinimalPoint();
            }
            if(mcPolite.ask("Хотите изменить сложность лабораторной?")){
                difficulty = mcPolite.askDifficulty();
            }
            if(mcPolite.ask("Хотите изменить название дисциплины и количество лекционных часов у лабораторной?")){
                discipline = mcPolite.askDiscipline();
            }

            collectionMain.addToCollection(new LabWork(id,name,coordinates,creationDate,minimalPoint,difficulty,discipline));
            Console.println("Лаборатораня успешно изменена.");
            return true;
        } catch (LabNotFoundException exception){
            Console.printerror("Лабораторной с таким ID не найдено!");
        } catch(WrongNumberOfElementsException exception){
            Console.println("Использование: '"+getName()+"'");
        } catch (CollectionEmptyException exception){
            Console.printerror("Коллекция пуста!");
        } catch (NumberFormatException exception){
            Console.printerror("ID должен быть представляться числом!");
        } catch (WrongInputInScriptException exception){

        }
        return false;
    }
}
