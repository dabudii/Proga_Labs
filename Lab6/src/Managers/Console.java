package Managers;

import Exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * The class that operates command input.
 */
public class Console {
    private CommandManager commandManager;
    private Scanner userScanner;
    private McPolite mcPolite;
    private Stack<String> scriptStack = new Stack<>();

    /**
     * Constructor of the class.
     */
    public Console(CommandManager commandManager, Scanner userScanner, McPolite mcPolite){
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.mcPolite = mcPolite;
    }

    /**
     * Mode for catching command from script.
     * @param arg Its argument.
     * @return Exit code.
     */
    public int scriptMode(String arg){
        String[] userCommand;
        int commandStatus;
        scriptStack.add(arg);
        try(Scanner scriptScanner = new Scanner(new File(arg))){
            if(!scriptScanner.hasNext())
            {
                throw new NoSuchElementException();
            }

            Scanner scannerGet = mcPolite.getUserScanner();
            mcPolite.setUserScanner(scriptScanner);
            mcPolite.setFileMode();

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while(userCommand[0].isEmpty()&&scriptScanner.hasNextLine()){
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                if(userCommand[0].equals("execute_script")){
                    for(String script : scriptStack){
                        if(userCommand[1].equals(script)){
                            throw new ScriptRecursionException();
                        }
                    }
                }
                commandStatus = launchCommand(userCommand);
                if((commandStatus == 1 || commandStatus == 3) && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())){
                    Console.println("Проверьте скрипт на корректность данных!");
                }
                commandStatus = 0;
            } while (scriptScanner.hasNextLine());

            mcPolite.setUserScanner(scannerGet);
            mcPolite.setUserMode();

            return commandStatus;
        } catch (NoSuchElementException exception){
            Console.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
            Console.println("Выводов до отключения: 3");
            Console.println("Выводов до отключения: 2");
            Console.println("Выводов до отключения: 1");
            System.exit(1);
        } catch (ScriptRecursionException exception){
            Console.printerror("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception){
            Console.printerror("Непредвиденная ошибка!");
            System.exit(0);
        } catch (FileNotFoundException exception) {
            Console.printerror("Файл с данным именем не найден!");
        } finally {
            scriptStack.remove(scriptStack.size()-1);
        }
        return 1;
    }

    /**
     * Mode for catching the user input.
     */
    public void interactiveMode(){
        String[] userCommand = {"",""};
        int commandStatus;
        try{
            do{
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandManager.addToHistory(userCommand[0]);
                commandStatus = launchCommand(userCommand);
                if(commandStatus == 1){
                    Console.printerror("Ошибка при выполнении команды!");
                }
                if(commandStatus == 3){
                    Console.printerror("Неправильный ввод команды!");
                }
            } while (commandStatus!=2);
        } catch (NoSuchElementException exception){
            Console.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
            Console.println("Выводов до отключения: 3");
            Console.println("Выводов до отключения: 2");
            Console.println("Выводов до отключения: 1");
            System.exit(1);
        } catch (IllegalStateException exception){
            Console.printerror("Непредвиденная ошибка! Обратитесь в поддержку!");
        }
    }

    /**
     * @param userCommand Command to launch.
     * @return Exit code.
     */
    private int launchCommand(String[] userCommand){
        switch (userCommand[0]){
            case "":
                break;
            case "help":
                if(!commandManager.help(userCommand[1])){
                    return 1;
                }
                break;
            case "info":
                if(!commandManager.info(userCommand[1])){
                    return 1;
                }
                break;
            case "show":
                if(!commandManager.show(userCommand[1])){
                    return 1;
                }
                break;
            case "add":
                if(!commandManager.add(userCommand[1])){
                    return 1;
                }
                break;
            case "update":
                if(!commandManager.update(userCommand[1])){
                    return 1;
                }
                break;
            case "remove_by_id":
                if(!commandManager.removeById(userCommand[1])){
                    return 1;
                }
                break;
            case "clear":
                if(!commandManager.clear(userCommand[1])){
                    return 1;
                }
                break;
            case "save":
                if(!commandManager.save(userCommand[1])){
                    return 1;
                }
                break;
            case "execute_script":
                if(!commandManager.executeScript(userCommand[1])){
                    return 1;
                } else return scriptMode(userCommand[1]);
            case "exit":
                if(!commandManager.exit(userCommand[1])){
                    return 1;
                }
                else return 2;
            case "add_if_min":
                if(!commandManager.addIfMin(userCommand[1])){
                    return 1;
                }
                break;
            case "remove_lower":
                if(!commandManager.removeLower(userCommand[1])){
                    return 1;
                }
                break;
            case "history":
                if(!commandManager.history(userCommand[1])){
                    return 1;
                }
                break;
            case "remove_all_by_difficulty":
                if(!commandManager.removeAllByDifficulty(userCommand[1])){
                    return 1;
                }
                break;
            case "filter_starts_with_name":
                if(!commandManager.filterStartsWithName(userCommand[1])){
                    return 1;
                }
                break;
            case "print_descending":
                if(!commandManager.printDescending(userCommand[1])){
                    return 1;
                }
                break;
            default:
                if(!commandManager.noCommand(userCommand[0])){
                    return 3;
                }
        }
        return 0;
    }

    /**
     * @param out Object to print.
     */
    public static void print(Object out){
        System.out.print(out);
    }

    /**
     * @param out Object to print.
     */
    public static void println(Object out){
        System.out.println(out);
    }

    /**
     * @param first Left element of the row.
     * @param second Right element of the row.
     */
    public static void printable(Object first, Object second){
        System.out.printf("%-37s%-1s%n", first, second);
    }

    /**
     * @param out Error to  print.
     */
    public static void printerror(Object out){
        System.out.println(" Error: "+out);
    }
}
