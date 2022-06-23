package server.commands;

import general.exceptions.DatabaseHandlingException;
import general.exceptions.UserIsNotFoundException;
import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.DatabaseCommandManager;
import server.utility.ResponseOutputer;

/**
 * Command 'login'. Allows the user to login.
 */
public class LoginCommand extends MainCommand {
    private DatabaseCommandManager databaseCommandManager;

    public LoginCommand(DatabaseCommandManager databaseCommandManager) {
        super("login", "", "внутренняя команда");
        this.databaseCommandManager = databaseCommandManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, Profile profile) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongNumberOfElementsException();
            if (databaseCommandManager.checkUserByUsernameAndPassword(profile)) {
                ResponseOutputer.appendln("Пользователь " + profile.getUsername() + " авторизован.");
            }
            else throw new UserIsNotFoundException();
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        } catch (UserIsNotFoundException exception) {
            ResponseOutputer.appenderror("Неправильные имя пользователя или пароль!");
        }
        return false;
    }
}