package server.utility;

import general.interaction.Request;
import general.interaction.Response;
import general.interaction.ResponseCode;
import general.utility.Printer;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Handles user connection.
 */
public class MyThread implements Runnable {
    private Server server;
    private Socket clientSocket;
    private CommandManager commandManager;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);


    public MyThread(Server server, Socket clientSocket, CommandManager commandManager) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.commandManager = commandManager;
    }

    /**
     * Main handling cycle.
     */
    @Override
    public void run() {
        Request userRequest = null;
        Response responseToUser = null;
        boolean stopFlag = false;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = forkJoinPool.invoke(new RequestHandler(userRequest, commandManager));
                Response finalResponseToUser = responseToUser;
                if (!fixedThreadPool.submit(() -> {
                    try {
                        clientWriter.writeObject(finalResponseToUser);
                        clientWriter.flush();
                        return true;
                    } catch (IOException exception) {
                        Printer.printerror("Произошла ошибка при отправке данных на клиент!");
                    }
                    return false;
                }).get()) break;
            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT &&
                    responseToUser.getResponseCode() != ResponseCode.CLIENT_EXIT);
            if (responseToUser.getResponseCode() == ResponseCode.SERVER_EXIT)
            {
                stopFlag = true;
            }
        } catch (ClassNotFoundException exception) {
            Printer.printerror("Произошла ошибка при чтении полученных данных!");
        } catch (CancellationException | ExecutionException | InterruptedException exception) {
            Printer.println("При обработке запроса произошла ошибка многопоточности!");
        } catch (IOException exception) {
            Printer.printerror("Непредвиденный разрыв соединения с клиентом!");
        } finally {
            try {
                fixedThreadPool.shutdown();
                clientSocket.close();
                Printer.println("Клиент отключен от сервера.");
            } catch (IOException exception) {
                Printer.printerror("Произошла ошибка при попытке завершить соединение с клиентом!");
            }
            if (stopFlag){
                server.stop();
            }
            server.releaseConnection();
        }
    }
}