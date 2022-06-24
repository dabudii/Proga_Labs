package server;

import general.exceptions.ClosingSocketException;
import general.exceptions.ConnectionErrorException;
import general.exceptions.OpeningServerSocketException;
import general.utility.Printer;
import server.utility.CommandManager;
import server.utility.MyThread;
import server.utility.MyThread;
import server.utility.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Server {
    private int port;
    private int soTimeout;
    private ServerSocket serverSocket;
    private CommandManager commandManager;
    private RequestHandler requestHandler;
    private Semaphore semaphore;
    private boolean processingStatus;

    public Server(int port, CommandManager commandManager, int maxClients) {
        this.port = port;
        this.commandManager = commandManager;
        this.semaphore = new Semaphore(maxClients);
    }

    /**
     * Begins server operation.
     */
    public void run() {
        try {
            openServerSocket();
            while (!processingStatusReturn()) {
                try {
                    acquireConnection();
                    if (processingStatusReturn()){
                        throw new ConnectionErrorException();
                    }
                    Socket clientSocket = connectToClient();
                    Thread thread = new Thread(new MyThread(this, clientSocket, commandManager));
                    thread.start();
                } catch (ConnectionErrorException exception) {
                    if (processingStatusReturn()) {
                        Printer.printerror("Произошла ошибка при соединении с клиентом!");
                    } else break;
                }
            }
            Printer.println("Работа сервера завершена.");
        } catch (OpeningServerSocketException exception) {
            Printer.printerror("Сервер не может быть запущен!");
        }
    }

    /**
     * Acquire connection.
     */
    public void acquireConnection() {
        try {
            semaphore.acquire();
            Printer.println("Разрешение на новое соединение полученно!");
        } catch (InterruptedException exception) {
            Printer.printerror("Произошла ошибка при получении разрешения на новое соединение!");
        }
    }

    private synchronized boolean processingStatusReturn(){
        return processingStatus;
    }
    /**
     * Release connection.
     */
    public void releaseConnection() {
        semaphore.release();
    }

    /**
     * Finishes server operation.
     */
    public synchronized void stop() {
        try {
            Printer.println("Завершение работы сервера...");
            if (serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            Printer.println("Работа сервера успешно завершена.");
        } catch (ClosingSocketException exception) {
            Printer.printerror("Невозможно завершить работу еще не запущенного сервера!");
        } catch (IOException exception) {
            Printer.printerror("Произошла ошибка при завершении работы сервера!");
        }
    }

    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try {
            Printer.println("Запуск сервера...");
            serverSocket = new ServerSocket(port);
            Printer.println("Сервер успешно запущен.");
        } catch (IllegalArgumentException exception) {
            Printer.printerror("Порт '" + port + "' находится за пределами возможных значений!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Printer.printerror("Произошла ошибка при попытке использовать порт '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }

    /**
     * Connecting to client.
     */
    private Socket connectToClient() throws ConnectionErrorException{
        try {
            Printer.println("Прослушивание порта '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Printer.println("Соединение с клиентом успешно установлено.");
            return clientSocket;
        }  catch (IOException exception) {
            Printer.printerror("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionErrorException();
        }
    }
}
