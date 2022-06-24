package server.commands;

import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import server.utility.ResponseOutputer;

/**
 * Command "filter_starts_with_name" writes the elements, which names are equal to the input name`.
 */
public class FilterStartsWithNameCommand extends MainCommand {
    private CollectionMain collectionMain;

    /**
     * Constructor of the class.
     */
    public FilterStartsWithNameCommand(CollectionMain collectionMain){
        super("filter_starts_with_name","<name>","вывести элементы, значение поля name которых начинается с заданной подстроки");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile) {
        try{
            System.out.println(str);
            if(str.isEmpty()){
                throw new WrongNumberOfElementsException();
            }
            String filterinfo = collectionMain.filterStartsWithName(str);
            if(!filterinfo.isEmpty()){
                ResponseOutputer.appendln(filterinfo);
            }
            else ResponseOutputer.appendln("Нет лабораторных с данным именем!");
            return true;
        } catch (WrongNumberOfElementsException exception){
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ArrayIndexOutOfBoundsException exception){
            ResponseOutputer.appenderror("Не указаны аргументы команды!");
        } catch (IllegalArgumentException exception){
            ResponseOutputer.appenderror("Выбранного имени нет в перечне.");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
