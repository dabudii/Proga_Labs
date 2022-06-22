package Client;

import Client.Utility.UserHandler;
import General.Exceptions.NotInLimitsException;
import General.Exceptions.WrongNumberOfElementsException;
import General.Utility.Printer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main client class. Creates all client instances.
 * @author Моторин Гордей
 * @version 1.0
 */
public class Start {
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    private static String host;
    private static int port;

    private static boolean initializeConnectionAddress(String name, int numPort) {
        try {
            if (name.isEmpty() || numPort==0) throw new WrongNumberOfElementsException();
            host = name;
            port = numPort;
            if (port < 0) throw new NotInLimitsException();
            return true;
        } catch (WrongNumberOfElementsException exception) {
            String jarName = new java.io.File(Start.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Printer.println("Использование: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            Printer.printerror("Порт должен быть представлен числом!");
        } catch (NotInLimitsException exception) {
            Printer.printerror("Порт не может быть отрицательным!");
        }
        return false;
    }

    public static void main(String[] args) {
        try (Scanner userScanner = new Scanner(System.in)) {
            String name;
            int numPort;
            Printer.println("Введите адрес сервера:");
            System.out.println(InetAddress.getLocalHost());
            name = userScanner.nextLine();
            Printer.println("Введите номер порта:");
            numPort = Integer.parseInt(userScanner.nextLine());
            if (!initializeConnectionAddress(name,numPort)) return;
            UserHandler userHandler = new UserHandler(userScanner);
            Client client = new Client(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, userHandler);
            client.run();
        } catch (NoSuchElementException exception){
            Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
            Printer.println("Выводов до отключения: 3");
            Printer.println("Выводов до отключения: 2");
            Printer.println("Выводов до отключения: 1");
        } catch (UnknownHostException exception) {
            throw new RuntimeException(exception);
        }
    }
}