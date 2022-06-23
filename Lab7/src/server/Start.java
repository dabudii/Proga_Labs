package server;
/**
 * @author Моторин Гордей
 * @version 1.0
 */

import server.commands.*;
import server.utility.*;

public class Start {
    private static String databaseUserName = "s335085";
    private static String databaseHost = "pg";
    private static String databasePassword = "hyp191";
    private static String databaseAddress;
    public static final int PORT = 2023;
    private static final int MAX_CLIENTS = 100;

    public static void main(String[] args) {

            databaseAddress = "jdbc:postgresql://" + databaseHost + ":5432/studs";
            DatabaseHandler databaseHandler = new DatabaseHandler(databaseAddress,databaseUserName,databasePassword);
            DatabaseCommandManager databaseCommandManager = new DatabaseCommandManager(databaseHandler);
            DatabaseCollectionMain databaseCollectionMain = new DatabaseCollectionMain(databaseHandler,databaseCommandManager);
            CollectionMain collectionMain = new CollectionMain(databaseCollectionMain);
            CommandManager commandManager = new CommandManager(
                    new HelpCommand(),
                    new InfoCommand(collectionMain),
                    new ShowCommand(collectionMain),
                    new AddCommand(collectionMain),
                    new UpdateCommand(collectionMain),
                    new RemoveByIdCommand(collectionMain),
                    new ClearCommand(collectionMain),
                    new ExecuteScriptCommand(),
                    new ExitCommand(collectionMain),
                    new AddIfMinCommand(collectionMain),
                    new RemoveLowerCommand(collectionMain),
                    new HistoryCommand(),
                    new RemoveAllByDifficultyCommand(collectionMain),
                    new FilterStartsWithNameCommand(collectionMain),
                    new PrintDescendingCommand(collectionMain),
                    new ServerExitCommand(collectionMain),
                    new LoginCommand(databaseCommandManager),
                    new RegisterCommand(databaseCommandManager)
            );

            Server server = new Server(PORT,commandManager,MAX_CLIENTS);

            server.run();
    }
}