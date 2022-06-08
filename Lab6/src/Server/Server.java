package Server;

import General.Exceptions.ClosingSocketException;
import General.Exceptions.ConnectionErrorException;
import General.Exceptions.OpeningServerSocketException;
import General.Interaction.Request;
import General.Interaction.Response;
import General.Interaction.ResponseCode;
import General.Utility.Printer;
import Server.Utility.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private int port;
    private int soTimeout;
    private ServerSocket serverSocket;
    private RequestHandler requestHandler;

    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
    }

    /**
     * Begins server operation.
     */
    public void run() {
        try {
            openServerSocket();
            boolean processingStatus = true;
            while (processingStatus) {
                try (Socket clientSocket = connectToClient()) {
                    processingStatus = processClientRequest(clientSocket);
                } catch (ConnectionErrorException | SocketTimeoutException exception) {
                    break;
                } catch (IOException exception) {
                    Printer.printerror("Произошла ошибка при попытке завершить соединение с клиентом!");
                }
            }
            stop();
        } catch (OpeningServerSocketException exception) {
            Printer.printerror("Сервер не может быть запущен!");
        }
    }

    /**
     * Finishes server operation.
     */
    private void stop() {
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
            serverSocket.setSoTimeout(soTimeout);
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
    private Socket connectToClient() throws ConnectionErrorException, SocketTimeoutException {
        try {
            Printer.println("Прослушивание порта '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Printer.println("Соединение с клиентом успешно установлено.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            Printer.printerror("Превышено время ожидания подключения!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            Printer.printerror("Произошла ошибка при соединении с клиентом!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * The process of receiving a request from a client.
     */
    private boolean processClientRequest(Socket clientSocket) {
        Request userRequest = null;
        Response responseToUser = null;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = requestHandler.handle(userRequest);
                Printer.println("Запрос '" + userRequest.getCommandName() + "' успешно обработан.");
                clientWriter.writeObject(responseToUser);
                clientWriter.flush();
            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;
        } catch (ClassNotFoundException exception) {
            Printer.printerror("Произошла ошибка при чтении полученных данных!");
        } catch (InvalidClassException | NotSerializableException exception) {
            Printer.printerror("Произошла ошибка при отправке данных на клиент!");
        } catch (IOException exception) {
            if (userRequest == null) {
                Printer.printerror("Непредвиденный разрыв соединения с клиентом!");
            } else {
                Printer.println("Клиент успешно отключен от сервера!");
            }
        }
        return true;
    }
}
