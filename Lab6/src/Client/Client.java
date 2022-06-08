package Client;

import Client.Utility.UserHandler;
import General.Exceptions.ConnectionErrorException;
import General.Exceptions.NotInLimitsException;
import General.Interaction.Request;
import General.Interaction.Response;
import General.Utility.Printer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Runs the client.
 */
public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processRequestToServer();
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        Printer.printerror("Превышено количество попыток подключения!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        Printer.printerror("Время ожидания подключения '" + reconnectionTimeout +
                                "' находится за пределами возможных значений!");
                        Printer.println("Повторное подключение будет произведено немедленно.");
                    } catch (Exception timeoutException) {
                        Printer.printerror("Произошла ошибка при попытке ожидания подключения!");
                        Printer.println("Повторное подключение будет произведено немедленно.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            Printer.println("Работа клиента успешно завершена.");
        } catch (NotInLimitsException exception){
          Printer.printerror("Клиент не может быть запущен!");
        } catch (IOException exception) {
            Printer.printerror("Произошла ошибка при попытке завершить соединение с сервером!");
        }
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInLimitsException {
        try {
            if (reconnectionAttempts >= 1) Printer.println("Повторное соединение с сервером...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            Printer.println("Соединение с сервером успешно установлено.");
            Printer.println("Ожидание разрешения на обмен данными...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            Printer.println("Разрешение на обмен данными получено.");
        } catch (IllegalArgumentException exception) {
            Printer.printerror("Адрес сервера введен некорректно!");
            throw new NotInLimitsException();
        } catch (IOException exception) {
            Printer.printerror("Произошла ошибка при соединении с сервером!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Server request process.
     */
    private boolean processRequestToServer() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
                        userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                Printer.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                Printer.printerror("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                Printer.printerror("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                Printer.printerror("Соединение с сервером разорвано!");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionErrorException | NotInLimitsException reconnectionException) {
                    if (requestToServer.getCommandName().equals("exit"))
                        Printer.println("Команда не будет зарегистрирована на сервере.");
                    else Printer.println("Попробуйте повторить команду позднее.");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }
}