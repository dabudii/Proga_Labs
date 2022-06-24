package client;

import client.utility.SecurityHandler;
import client.utility.UserHandler;
import general.exceptions.ConnectionErrorException;
import general.exceptions.NotInLimitsException;
import general.interaction.Profile;
import general.interaction.Request;
import general.interaction.Response;
import general.interaction.ResponseCode;
import general.utility.Printer;

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
    private Profile profile;
    private SecurityHandler securityHandler;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler, SecurityHandler securityHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
        this.securityHandler = securityHandler;
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            while (true) {
                try {
                    connectToServer();
                    processAuthentication();
                    processRequestToServer();
                    break;
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

    private void processAuthentication(){
        Request requestToServer = null;
        Response responseServer = null;
        do{
            try{
                requestToServer = securityHandler.handle();
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                responseServer = (Response) serverReader.readObject();
                Printer.println(responseServer.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                Printer.printerror("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                Printer.printerror("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                Printer.printerror("Соединение с сервером разорвано!");
                try {
                    connectToServer();
                } catch (ConnectionErrorException | NotInLimitsException reconnectionException) {
                    Printer.println("Попробуйте повторить авторизацию позднее.");
                }
            }
        } while (responseServer == null || !responseServer.getResponseCode().equals(ResponseCode.OK));
        profile = requestToServer.getProfile();

    }

    /**
     * Server request process.
     */
    private boolean processRequestToServer() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode(), profile) :
                        userHandler.handle(null, profile);
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