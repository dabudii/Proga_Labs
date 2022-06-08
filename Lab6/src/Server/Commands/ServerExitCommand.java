package Server.Commands;

import General.Exceptions.WrongNumberOfElementsException;
import Server.Utility.ResponseOutputer;

/**
 * Command 'server_exit'. Checks for wrong arguments then do nothing.
 */
public class ServerExitCommand extends MainCommand {

    public ServerExitCommand() {
        super("server_exit","", "завершить работу сервера");
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    public boolean execute(String str, Object objArg) {
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