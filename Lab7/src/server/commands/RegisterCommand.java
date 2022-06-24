package server.commands;

import general.exceptions.DatabaseHandlingException;
import general.exceptions.UserAlreadyExists;
import general.exceptions.WrongNumberOfElementsException;
import general.interaction.Profile;
import server.utility.DatabaseCommandManager;
import server.utility.ResponseOutputer;

/**
 * Command 'register'. Allows the user to register.
 */
public class RegisterCommand extends MainCommand {
    private DatabaseCommandManager databaseCommandManager;

    public RegisterCommand(DatabaseCommandManager databaseCommandManager) {
        super("register", "", "внутренняя команда");
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
            if (!stringArgument.isEmpty() || objectArgument != null) {
                throw new WrongNumberOfElementsException();
            }
            if (databaseCommandManager.insertUser(profile)) {
                ResponseOutputer.appendln("Пользователь " + profile.getUsername() + " зарегистрирован.");
            }
            else throw new UserAlreadyExists();
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '"+getName()+" "+getUsage()+"'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        } catch (UserAlreadyExists exception) {
            ResponseOutputer.appenderror("Пользователь " + profile.getUsername() + " уже существует!");
        }
        return false;
    }
}