package Server;
/**
 * @author Моторин Гордей
 * @version 1.0
 */

import General.Utility.Printer;
import Server.Commands.*;
import Server.Utility.*;

import java.util.Scanner;

public class Start {
    public static final int PORT = 2022;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;

    public static void main(String[] args) {
        try(Scanner userScanner = new Scanner(System.in)){
        Printer.println("Введите путь или название файла:");
        boolean proverkaImeni = false;
        String vvod;
        vvod = userScanner.nextLine();
        while (!proverkaImeni) {
            FileManager fileManager = new FileManager(vvod);
            proverkaImeni = fileManager.readCollectionProv();
            if (!proverkaImeni) {
                vvod = userScanner.nextLine();
            }
        }
        XMLParser xmlParser = new XMLParser();
        FileManager fileManager = new FileManager(vvod);
        CollectionMain collectionMain = new CollectionMain(fileManager, xmlParser);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionMain),
                new ShowCommand(collectionMain),
                new AddCommand(collectionMain),
                new UpdateCommand(collectionMain),
                new RemoveByIdCommand(collectionMain),
                new ClearCommand(collectionMain),
                new SaveCommand(collectionMain),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new AddIfMinCommand(collectionMain),
                new RemoveLowerCommand(collectionMain),
                new HistoryCommand(),
                new RemoveAllByDifficultyCommand(collectionMain),
                new FilterStartsWithNameCommand(collectionMain),
                new PrintDescendingCommand(collectionMain),
                new ServerExitCommand()
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
        }
    }
}