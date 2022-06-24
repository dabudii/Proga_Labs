package server.utility;

import general.exceptions.DatabaseHandlingException;
import general.interaction.Profile;
import general.utility.Printer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A manager of user database.
 */
public class DatabaseCommandManager {
    private final String SELECT_USER_BY_ID = "SELECT * FROM " + DatabaseHandler.USER_TABLE +
            " WHERE " + DatabaseHandler.USER_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + DatabaseHandler.USER_TABLE +
            " WHERE " + DatabaseHandler.USER_TABLE_USERNAME_COLUMN + " = ?";
    private final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " AND " +
            DatabaseHandler.USER_TABLE_PASSWORD_COLUMN + " = ?";
    private final String INSERT_USER = "INSERT INTO " +
            DatabaseHandler.USER_TABLE + " (" +
            DatabaseHandler.USER_TABLE_USERNAME_COLUMN + ", " +
            DatabaseHandler.USER_TABLE_PASSWORD_COLUMN + ") VALUES (?, ?)";

    private DatabaseHandler databaseHandler;

    public DatabaseCommandManager(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public Profile getUserById(long userId) throws SQLException {
        Profile profile;
        PreparedStatement preparedSelectUserByIdStatement = null;
        try {
            preparedSelectUserByIdStatement =
                    databaseHandler.getPreparedStatement(SELECT_USER_BY_ID, false);
            preparedSelectUserByIdStatement.setLong(1, userId);
            ResultSet resultSet = preparedSelectUserByIdStatement.executeQuery();
            if (resultSet.next()) {
                profile = new Profile(
                        resultSet.getString(DatabaseHandler.USER_TABLE_USERNAME_COLUMN),
                        resultSet.getString(DatabaseHandler.USER_TABLE_PASSWORD_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса SELECT_USER_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByIdStatement);
        }
        return profile;
    }

    public boolean checkUserByUsernameAndPassword(Profile profile) throws DatabaseHandlingException {
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            preparedSelectUserByUsernameAndPasswordStatement =
                    databaseHandler.getPreparedStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD, false);
            preparedSelectUserByUsernameAndPasswordStatement.setString(1, profile.getUsername());
            preparedSelectUserByUsernameAndPasswordStatement.setString(2, profile.getPassword());
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME_AND_PASSWORD!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
    }

    public long getUserIdByUsername(Profile profile) throws DatabaseHandlingException {
        long userId;
        PreparedStatement preparedSelectUserByUsernameStatement = null;
        try {
            preparedSelectUserByUsernameStatement = databaseHandler.getPreparedStatement(SELECT_USER_BY_USERNAME, false);
            preparedSelectUserByUsernameStatement.setString(1, profile.getUsername());
            ResultSet resultSet = preparedSelectUserByUsernameStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getLong(DatabaseHandler.USER_TABLE_ID_COLUMN);
            } else userId = -1;
            return userId;
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса SELECT_USER_BY_USERNAME!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectUserByUsernameStatement);
        }
    }

    /**
     * Insert user.
     *
     * @param user User.
     * @return Status of insert.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public boolean insertUser(Profile user) throws DatabaseHandlingException {
        PreparedStatement preparedInsertUserStatement = null;
        try {
            if (getUserIdByUsername(user) != -1) return false;
            preparedInsertUserStatement =
                    databaseHandler.getPreparedStatement(INSERT_USER, false);
            preparedInsertUserStatement.setString(1, user.getUsername());
            preparedInsertUserStatement.setString(2, user.getPassword());
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            return true;
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса INSERT_USER!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertUserStatement);
        }
    }
}