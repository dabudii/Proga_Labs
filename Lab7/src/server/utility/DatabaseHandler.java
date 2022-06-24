package server.utility;

import general.exceptions.DatabaseHandlingException;
import general.utility.Printer;

import java.sql.*;

/**
 * A class for handle database.
 */
public class DatabaseHandler {
    public static final String LABWORK_TABLE = "labwork";
    public static final String USER_TABLE = "my_user";
    public static final String COORDINATES_TABLE = "coordinates";
    public static final String DISCIPLINE_TABLE = "discipline";

    public static final String LABWORK_TABLE_ID_COLUMN = "id";
    public static final String LABWORK_TABLE_NAME_COLUMN = "name";
    public static final String LABWORK_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String LABWORK_TABLE_MINIMAL_POINT_COLUMN = "minimal_point";
    public static final String LABWORK_TABLE_DIFFICULTY_COLUMN = "difficulty";
    public static final String LABWORK_TABLE_DISCIPLINE_ID_COLUMN = "discipline_id";
    public static final String LABWORK_TABLE_USER_ID_COLUMN = "user_id";

    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";

    public static final String COORDINATES_TABLE_LABWORK_ID_COLUMN = "labwork_id";
    public static final String COORDINATES_TABLE_X_COLUMN = "x";
    public static final String COORDINATES_TABLE_Y_COLUMN = "y";

    public static final String DISCIPLINE_TABLE_ID_COLUMN = "id";
    public static final String DISCIPLINE_TABLE_NAME_COLUMN = "name";
    public static final String DISCIPLINE_TABLE_LECTURE_HOURS_COLUMN = "lecture_hours";

    private final String JDBC_DRIVER = "org.postgresql.Driver";

    private String url;
    private String profile;
    private String password;
    private Connection connection;

    public DatabaseHandler(String url, String profile, String password) {
        this.url = url;
        this.profile = profile;
        this.password = password;

        connectToDataBase();
    }

    /**
     * A class for connect to database.
     */
    private void connectToDataBase() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, profile, password);
            Printer.println("Соединение с базой данных установлено.");
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при подключении к базе данных!");
        } catch (ClassNotFoundException exception) {
            Printer.printerror("Драйвер управления базой данных не найден!");
        }
    }

    /**
     * @param sqlStatement SQL statement to be prepared.
     * @param generateKeys Is keys needed to be generated.
     * @return Pprepared statement.
     * @throws SQLException When there's exception inside.
     */
    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) throws SQLException {
        PreparedStatement preparedStatement;
        try {
            if (connection == null) {
                throw new SQLException();
            }
            int autoGeneratedKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGeneratedKeys);
            return preparedStatement;
        } catch (SQLException exception) {
            if (connection == null)
            {
                Printer.printerror("Соединение с базой данных не установлено!");
            }
            throw new SQLException(exception);
        }
    }

    /**
     * Close prepared statement.
     *
     * @param sqlStatement SQL statement to be closed.
     */
    public void closePreparedStatement(PreparedStatement sqlStatement) {
        if (sqlStatement == null) return;
        try {
            sqlStatement.close();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при закрытии SQL запроса '" + sqlStatement + "'.");
        }
    }

    /**
     * Close connection to database.
     */
    public void closeConnection() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
            Printer.println("Соединение с базой данных разорвано.");
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при разрыве соединения с базой данных!");
        }
    }

    /**
     * Set commit mode of database.
     */
    public void setCommitMode() {
        try {
            if (connection == null) {
                throw new SQLException();
            }
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при установлении режима транзакции базы данных!");
        }
    }

    /**
     * Set normal mode of database.
     */
    public void setNormalMode() {
        try {
            if (connection == null) {
                throw new SQLException();
            }
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при установлении нормального режима базы данных!");
        }
    }

    /**
     * Commit database status.
     */
    public void commit() {
        try {
            if (connection == null) {
                throw new SQLException();
            }
            connection.commit();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при подтверждении нового состояния базы данных!");
        }
    }

    /**
     * Roll back database status.
     */
    public void rollback() {
        try {
            if (connection == null) {
                throw new SQLException();
            }
            connection.rollback();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при возврате исходного состояния базы данных!");
        }
    }

    /**
     * Set save point of database.
     */
    public void setSavepoint() {
        try {
            if (connection == null) {
                throw new SQLException();
            }
            connection.setSavepoint();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при сохранении состояния базы данных!");
        }
    }
}