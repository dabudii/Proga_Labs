    package Start;
/**
 * @author Моторин Гордей
 * @version 1.0
 */

import Commands.*;
import Managers.*;
import Managers.Console;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner userScanner = new Scanner(System.in)) {
            Console.println("Введите путь или название файла:");
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
            FileManager fileManager = new FileManager(vvod);
            McPolite mcPolite = new McPolite(userScanner);
            XMLParser xmlParser = new XMLParser();
            CollectionMain collectionMain = new CollectionMain(fileManager, xmlParser);
            CommandManager commandManager = new CommandManager(
                    new HelpCommand(),
                    new InfoCommand(collectionMain),
                    new ShowCommand(collectionMain),
                    new AddCommand(collectionMain, mcPolite),
                    new UpdateCommand(collectionMain, mcPolite),
                    new RemoveByIdCommand(collectionMain),
                    new ClearCommand(collectionMain),
                    new SaveCommand(collectionMain),
                    new ExecuteScriptCommand(),
                    new ExitCommand(),
                    new AddIfMinCommand(collectionMain, mcPolite),
                    new RemoveLowerCommand(collectionMain, mcPolite),
                    new HistoryCommand(),
                    new RemoveAllByDifficultyCommand(collectionMain),
                    new FilterStartsWithNameCommand(collectionMain),
                    new PrintDescendingCommand(collectionMain)
            );

            Console console = new Console(commandManager, userScanner, mcPolite);

            console.interactiveMode();
        } catch (NoSuchElementException exception){
            Console.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
            Console.println("Выводов до отключения: 3");
            Console.println("Выводов до отключения: 2");
            Console.println("Выводов до отключения: 1");
        }
    }
}