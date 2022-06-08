package Client.Utility;

import General.Collection.*;
import General.Exceptions.*;
import General.Interaction.*;
import General.Utility.Printer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Receives user requests.
 */
public class UserHandler {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Receives user input.
     *
     * @param serverResponseCode Last server's response code.
     * @return New request to server.
     */
    public Request handle(ResponseCode serverResponseCode) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.ERROR ||
                            serverResponseCode == ResponseCode.SERVER_EXIT))
                        throw new WrongInputInScriptException();
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        Printer.println("Возвращаюсь к скрипту '" + scriptStack.pop().getName() + "'...");
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            Printer.println(userInput);
                        }
                    } else {
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Printer.println();
                    Printer.printerror("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Printer.printerror("Превышено количество попыток ввода!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR))
                    throw new WrongInputInScriptException();
                switch (processingCode) {
                    case OBJECT:
                        Laba marineAddRaw = generateLabWork();
                        return new Request(userCommand[0], userCommand[1], marineAddRaw);
                    case UPDATE_OBJECT:
                        Laba labaUpdate = generateLabWorkUpdate();
                        return new Request(userCommand[0], userCommand[1], labaUpdate);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Printer.println("Выполняю скрипт '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
                Printer.printerror("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                Printer.printerror("Скрипты не могут вызываться рекурсивно!");
                throw new WrongInputInScriptException();
            }
        } catch (WrongInputInScriptException exception) {
            Printer.printerror("Выполнение скрипта прервано!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "remove_all_by_difficulty":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<difficulty>");
                    break;
                case "":
                    return ProcessingCode.ERROR;
                case "help":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "save":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add_if_min":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "history":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "print_descending":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "filter_starts_with_name":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("<name>");
                    break;
                case "server_exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                default:
                    Printer.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Printer.println("Использование: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }

    private Laba generateLabWork() throws WrongInputInScriptException {
        McPolite mcPolite = new McPolite(userScanner);
        if (fileMode()) mcPolite.setFileMode();
        return new Laba(
                mcPolite.askName(),
                mcPolite.askCoordinates(),
                mcPolite.askMinimalPoint(),
                mcPolite.askDifficulty(),
                mcPolite.askDiscipline()
        );
    }

    private Laba generateLabWorkUpdate() throws WrongInputInScriptException {
        McPolite mcPolite = new McPolite(userScanner);
        if (fileMode()) mcPolite.setFileMode();
        String name = mcPolite.ask("Хотите изменить имя лабораторной?") ?
                mcPolite.askName() : null;
        Coordinates coordinates = mcPolite.ask("Хотите изменить координаты лабораторной?") ?
                mcPolite.askCoordinates() : null;
        Float minimalPoint = mcPolite.ask("Хотите изменить минимальное количество баллов у лабораторной?") ?
                mcPolite.askMinimalPoint() : 0;
        Difficulty difficulty = mcPolite.ask("Хотите изменить сложность у лабораторной?") ?
                mcPolite.askDifficulty() : null;
        Discipline discipline = mcPolite.ask("Хотите изменить дисциплину у лабораторной?") ?
                mcPolite.askDiscipline() : null;
        return new Laba(
                name,
                coordinates,
                minimalPoint,
                difficulty,
                discipline
        );
    }

    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}