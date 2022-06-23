package server.commands;

import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.CollectionMain;
import server.utility.ResponseOutputer;

/**
 * Command 'server_exit'. Checks for wrong arguments then do nothing.
 */
public class ServerExitCommand extends MainCommand {

    private CollectionMain collectionMain;
    public ServerExitCommand(CollectionMain collectionMain) {
        super("server_exit","", "завершить работу сервера");
        this.collectionMain = collectionMain;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg, Profile profile) {
        try {
            if (!str.isEmpty()) throw new WrongNumberOfElementsException();
            ResponseOutputer.appendln("Работа сервера успешно завершена!");
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}