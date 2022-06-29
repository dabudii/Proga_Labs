package server.utility;

import general.collection.*;
import general.exceptions.DatabaseHandlingException;
import general.interaction.Laba;
import general.interaction.Profile;
import general.utility.Printer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TreeSet;

/**
 * Operates the database collection itself.
 */
public class DatabaseCollectionMain {

    private final String SELECT_ALL_LABWORK = "SELECT * FROM " + DatabaseHandler.LABWORK_TABLE;
    private final String SELECT_LABWORK_BY_ID = SELECT_ALL_LABWORK + " WHERE " +
            DatabaseHandler.LABWORK_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_LABWORK_BY_ID_AND_USER_ID = SELECT_LABWORK_BY_ID + " AND " +
            DatabaseHandler.LABWORK_TABLE_USER_ID_COLUMN + " = ?";
    private final String INSERT_LABWORK = "INSERT INTO " +
            DatabaseHandler.LABWORK_TABLE + " (" +
            DatabaseHandler.LABWORK_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.LABWORK_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseHandler.LABWORK_TABLE_MINIMAL_POINT_COLUMN + ", " +
            DatabaseHandler.LABWORK_TABLE_DIFFICULTY_COLUMN + ", " +
            DatabaseHandler.LABWORK_TABLE_DISCIPLINE_ID_COLUMN + ", " +
            DatabaseHandler.LABWORK_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?)";
    private final String DELETE_LABWORK_BY_ID = "DELETE FROM " + DatabaseHandler.LABWORK_TABLE +
            " WHERE " + DatabaseHandler.LABWORK_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_LABWORK_NAME_BY_ID = "UPDATE " + DatabaseHandler.LABWORK_TABLE + " SET " +
            DatabaseHandler.LABWORK_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.LABWORK_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_LABWORK_MINIMAL_POINT_BY_ID = "UPDATE " + DatabaseHandler.LABWORK_TABLE + " SET " +
            DatabaseHandler.LABWORK_TABLE_MINIMAL_POINT_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.LABWORK_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_LABWORK_DIFFICULTY_BY_ID = "UPDATE " + DatabaseHandler.LABWORK_TABLE + " SET " +
            DatabaseHandler.LABWORK_TABLE_DIFFICULTY_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.LABWORK_TABLE_ID_COLUMN + " = ?";

    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + DatabaseHandler.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_LABWORK_ID = SELECT_ALL_COORDINATES +
            " WHERE " + DatabaseHandler.COORDINATES_TABLE_LABWORK_ID_COLUMN + " = ?";
    private final String INSERT_COORDINATES = "INSERT INTO " +
            DatabaseHandler.COORDINATES_TABLE + " (" +
            DatabaseHandler.COORDINATES_TABLE_LABWORK_ID_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + ", " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?, ?)";
    private final String UPDATE_COORDINATES_BY_LABWORK_ID = "UPDATE " + DatabaseHandler.COORDINATES_TABLE + " SET " +
            DatabaseHandler.COORDINATES_TABLE_X_COLUMN + " = ?, " +
            DatabaseHandler.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.COORDINATES_TABLE_LABWORK_ID_COLUMN + " = ?";
    private final String DELETE_COORDINATES_BY_ID = "DELETE FROM " + DatabaseHandler.COORDINATES_TABLE +
            " WHERE " + DatabaseHandler.COORDINATES_TABLE_LABWORK_ID_COLUMN + " = ?";

    private final String SELECT_ALL_DISCIPLINE = "SELECT * FROM " + DatabaseHandler.DISCIPLINE_TABLE;
    private final String SELECT_DISCIPLINE_BY_ID = SELECT_ALL_DISCIPLINE +
            " WHERE " + DatabaseHandler.DISCIPLINE_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_DISCIPLINE = "INSERT INTO " +
            DatabaseHandler.DISCIPLINE_TABLE + " (" +
            DatabaseHandler.DISCIPLINE_TABLE_NAME_COLUMN + ", " +
            DatabaseHandler.DISCIPLINE_TABLE_LECTURE_HOURS_COLUMN + ") VALUES (?, ?)";
    private final String UPDATE_DISCIPLINE_BY_ID = "UPDATE " + DatabaseHandler.DISCIPLINE_TABLE + " SET " +
            DatabaseHandler.DISCIPLINE_TABLE_NAME_COLUMN + " = ?, " +
            DatabaseHandler.DISCIPLINE_TABLE_LECTURE_HOURS_COLUMN + " = ?" + " WHERE " +
            DatabaseHandler.DISCIPLINE_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_DISCIPLINE_BY_ID = "DELETE FROM " + DatabaseHandler.DISCIPLINE_TABLE +
            " WHERE " + DatabaseHandler.DISCIPLINE_TABLE_ID_COLUMN + " = ?";

    private DatabaseHandler databaseHandler;
    private DatabaseCommandManager databaseCommandManager;

    public DatabaseCollectionMain(DatabaseHandler databaseHandler, DatabaseCommandManager databaseCommandManager) {
        this.databaseHandler = databaseHandler;
        this.databaseCommandManager = databaseCommandManager;
        create();
    }

    private void create(){
        String create = "CREATE TABLE IF NOT EXISTS labwork (\n" +
                "  id SERIAL PRIMARY KEY CHECK ( id > 0 ),\n" +
                "  name TEXT NOT NULL CHECK (name <> ''),\n" +
                "  creation_date TIMESTAMP,\n" +
                "  minimal_point FLOAt NOT NULL CHECK(minimal_point > 0),\n" +
                "  difficulty TEXT NOT NULL,\n" +
                "  discipline_id INT CHECK (discipline_id > 0),\n" +
                "  user_id INT CHECK (user_id > 0)\n" +
                "  );\n" +
                " CREATE TABLE IF NOT EXISTS my_user (\n" +
                "  id SERIAL PRIMARY KEY CHECK ( id > 0 ),\n" +
                "  username TEXT NOT NULL CHECK (username <> ''),\n" +
                "  password TEXT NOT NULL\n" +
                "  );\n" +
                " CREATE TABLE IF NOT EXISTS coordinates (\n" +
                "  labwork_id INT PRIMARY KEY CHECK (labwork_id > 0 ),\n" +
                "  x INT NOT NULL CHECK(x < 802),\n" +
                "  y FLOAT NOT NULL\n" +
                "  );\n" +
                " CREATE TABLE IF NOT EXISTS discipline (\n" +
                "  id SERIAL PRIMARY KEY CHECK ( id > 0 ),\n" +
                "  name TEXT NOT NULL CHECK (name <> ''),\n" +
                "  lecture_hours int NOT NULL\n" +
                "  );";
        try(PreparedStatement createStatement = databaseHandler.getPreparedStatement(create, false)) {
            createStatement.execute();
        } catch (SQLException e) {
            Printer.printerror("Произошла ошибка при выполнении группы запросов на добавление таблиц!");
        }
    }
    private LabWork createLabwork(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DatabaseHandler.LABWORK_TABLE_ID_COLUMN);
        String name = resultSet.getString(DatabaseHandler.LABWORK_TABLE_NAME_COLUMN);
        LocalDateTime creationDate = resultSet.getTimestamp(DatabaseHandler.LABWORK_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
        Coordinates coordinates = getCoordinatesByLabworkId(id);
        Float minimalPoint = resultSet.getFloat(DatabaseHandler.LABWORK_TABLE_MINIMAL_POINT_COLUMN);
        Difficulty difficulty = Difficulty.valueOf(resultSet.getString(DatabaseHandler.LABWORK_TABLE_DIFFICULTY_COLUMN));
        Discipline discipline = getDisciplineById(resultSet.getLong(DatabaseHandler.LABWORK_TABLE_DISCIPLINE_ID_COLUMN));
        Profile owner = databaseCommandManager.getUserById(resultSet.getLong(DatabaseHandler.LABWORK_TABLE_USER_ID_COLUMN));
        return new LabWork(
                id,
                name,
                coordinates,
                creationDate,
                minimalPoint,
                difficulty,
                discipline,
                owner
        );
    }

    public TreeSet<LabWork> getCollection() throws DatabaseHandlingException {
        TreeSet<LabWork> labList = new TreeSet<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = databaseHandler.getPreparedStatement(SELECT_ALL_LABWORK, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
                labList.add(createLabwork(resultSet));
            }
        } catch (SQLException exception) {
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectAllStatement);
        }
        return labList;
    }

    public LabWork insertLab(Laba laba, Profile profile) throws DatabaseHandlingException {
        LabWork labWork;
        PreparedStatement preparedInsertLabworkStatement = null;
        PreparedStatement preparedInsertCoordinatesStatement = null;
        PreparedStatement preparedInsertDisciplineStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            LocalDateTime creationTime = LocalDateTime.now();

            preparedInsertLabworkStatement = databaseHandler.getPreparedStatement(INSERT_LABWORK, true);
            preparedInsertCoordinatesStatement = databaseHandler.getPreparedStatement(INSERT_COORDINATES, true);
            preparedInsertDisciplineStatement = databaseHandler.getPreparedStatement(INSERT_DISCIPLINE, true);

            preparedInsertDisciplineStatement.setString(1, laba.getDiscipline().getName());
            preparedInsertDisciplineStatement.setLong(2, laba.getDiscipline().getLectureHours());
            if (preparedInsertDisciplineStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedDisciplineKeys = preparedInsertDisciplineStatement.getGeneratedKeys();
            long disciplineId;
            if (generatedDisciplineKeys.next()) {
                disciplineId = generatedDisciplineKeys.getLong(1);
            } else throw new SQLException();
            Printer.println("Выполнен запрос INSERT_DISCIPLINE!");

            preparedInsertLabworkStatement.setString(1, laba.getName());
            preparedInsertLabworkStatement.setTimestamp(2, Timestamp.valueOf(creationTime));
            preparedInsertLabworkStatement.setFloat(3, laba.getMinimalPoint());
            preparedInsertLabworkStatement.setString(4, laba.getDifficulty().toString());
            preparedInsertLabworkStatement.setLong(5, disciplineId);
            preparedInsertLabworkStatement.setLong(6, databaseCommandManager.getUserIdByUsername(profile));
            if (preparedInsertLabworkStatement.executeUpdate() == 0) {
                throw new SQLException();
            }
            ResultSet generatedLabworkKeys = preparedInsertLabworkStatement.getGeneratedKeys();
            long labworkId;
            if (generatedLabworkKeys.next()) {
                labworkId = generatedLabworkKeys.getLong(1);
            } else throw new SQLException();
            Printer.println("Выполнен запрос INSERT_LABWORK!");

            preparedInsertCoordinatesStatement.setLong(1, labworkId);
            preparedInsertCoordinatesStatement.setInt(2, laba.getCoordinates().getX());
            preparedInsertCoordinatesStatement.setFloat(3, laba.getCoordinates().getY());
            if (preparedInsertCoordinatesStatement.executeUpdate() == 0) {
                throw new SQLException();
            }
            Printer.println("Выполнен запрос INSERT_COORDINATES!");

            labWork = new LabWork(
                    labworkId,
                    laba.getName(),
                    laba.getCoordinates(),
                    creationTime,
                    laba.getMinimalPoint(),
                    laba.getDifficulty(),
                    laba.getDiscipline(),
                    profile
            );

            databaseHandler.commit();
            return labWork;
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении группы запросов на добавление нового объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedInsertLabworkStatement);
            databaseHandler.closePreparedStatement(preparedInsertCoordinatesStatement);
            databaseHandler.closePreparedStatement(preparedInsertDisciplineStatement);
            databaseHandler.setNormalMode();
        }
    }

    private Coordinates getCoordinatesByLabworkId(long labworkId) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedSelectCoordinatesByLabworkIdStatement = null;
        try {
            preparedSelectCoordinatesByLabworkIdStatement = databaseHandler.getPreparedStatement(SELECT_COORDINATES_BY_LABWORK_ID, false);
            preparedSelectCoordinatesByLabworkIdStatement.setLong(1, labworkId);
            ResultSet resultSet = preparedSelectCoordinatesByLabworkIdStatement.executeQuery();
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getInt(DatabaseHandler.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseHandler.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_LABWORK_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectCoordinatesByLabworkIdStatement);
        }
        return coordinates;
    }

    private Discipline getDisciplineById(long disciplineId) throws SQLException {
        Discipline discipline;
        PreparedStatement preparedSelectDisciplineByIdStatement = null;
        try {
            preparedSelectDisciplineByIdStatement = databaseHandler.getPreparedStatement(SELECT_DISCIPLINE_BY_ID, false);
            preparedSelectDisciplineByIdStatement.setLong(1, disciplineId);
            ResultSet resultSet = preparedSelectDisciplineByIdStatement.executeQuery();
            if (resultSet.next()) {
                discipline = new Discipline(
                        resultSet.getString(DatabaseHandler.DISCIPLINE_TABLE_NAME_COLUMN),
                        resultSet.getInt(DatabaseHandler.DISCIPLINE_TABLE_LECTURE_HOURS_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса SELECT_DISCIPLINE_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectDisciplineByIdStatement);
        }
        return discipline;
    }

    private long getDisciplineIdByLabworkId(long labworkId) throws SQLException {
        long disciplineId;
        PreparedStatement preparedSelectLabworkByIdStatement = null;
        try {
            preparedSelectLabworkByIdStatement = databaseHandler.getPreparedStatement(SELECT_LABWORK_BY_ID, false);
            preparedSelectLabworkByIdStatement.setLong(1, labworkId);
            ResultSet resultSet = preparedSelectLabworkByIdStatement.executeQuery();
            if (resultSet.next()) {
                disciplineId = resultSet.getLong(DatabaseHandler.LABWORK_TABLE_DISCIPLINE_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса SELECT_LABWORK_BY_ID!");
            throw new SQLException(exception);
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectLabworkByIdStatement);
        }
        return disciplineId;
    }


    public void updateLabworkById(long labworkId, Laba laba) throws DatabaseHandlingException {
        PreparedStatement preparedUpdateLabworkNameByIdStatement = null;
        PreparedStatement preparedUpdateLabworkMinimalPointByIdStatement = null;
        PreparedStatement preparedUpdateLabworkDifficultyByIdStatement = null;
        PreparedStatement preparedUpdateCoordinatesByLabworkIdStatement = null;
        PreparedStatement preparedUpdateDisciplineByIdStatement = null;
        try {
            databaseHandler.setCommitMode();
            databaseHandler.setSavepoint();

            preparedUpdateLabworkNameByIdStatement = databaseHandler.getPreparedStatement(UPDATE_LABWORK_NAME_BY_ID, false);
            preparedUpdateCoordinatesByLabworkIdStatement = databaseHandler.getPreparedStatement(UPDATE_COORDINATES_BY_LABWORK_ID, false);
            preparedUpdateLabworkMinimalPointByIdStatement = databaseHandler.getPreparedStatement(UPDATE_LABWORK_MINIMAL_POINT_BY_ID, false);
            preparedUpdateLabworkDifficultyByIdStatement = databaseHandler.getPreparedStatement(UPDATE_LABWORK_DIFFICULTY_BY_ID, false);
            preparedUpdateDisciplineByIdStatement = databaseHandler.getPreparedStatement(UPDATE_DISCIPLINE_BY_ID, false);

            if (laba.getName() != null) {
                preparedUpdateLabworkNameByIdStatement.setString(1, laba.getName());
                preparedUpdateLabworkNameByIdStatement.setLong(2, labworkId);
                if (preparedUpdateLabworkNameByIdStatement.executeUpdate() == 0) throw new SQLException();
                Printer.println("Выполнен запрос UPDATE_LABWORK_NAME_BY_ID");
            }
            if (laba.getCoordinates() != null) {
                preparedUpdateCoordinatesByLabworkIdStatement.setInt(1, laba.getCoordinates().getX());
                preparedUpdateCoordinatesByLabworkIdStatement.setFloat(2, laba.getCoordinates().getY());
                preparedUpdateCoordinatesByLabworkIdStatement.setLong(3, labworkId);
                if (preparedUpdateCoordinatesByLabworkIdStatement.executeUpdate() == 0) throw new SQLException();
                Printer.println("Выполнен запрос UPDATE_COORDINATES_BY_LABWORK_ID");
            }
            if (laba.getMinimalPoint() > 0) {
                preparedUpdateLabworkMinimalPointByIdStatement.setFloat(1, laba.getMinimalPoint());
                preparedUpdateLabworkMinimalPointByIdStatement.setLong(2, labworkId);
                if (preparedUpdateLabworkMinimalPointByIdStatement.executeUpdate() == 0) throw new SQLException();
                Printer.println("Выполнен запрос UPDATE_LABWORK_MINIMAL_POINT_BY_ID");
            }
            if (laba.getDifficulty() != null) {
                preparedUpdateLabworkDifficultyByIdStatement.setString(1, laba.getDifficulty().toString());
                preparedUpdateLabworkDifficultyByIdStatement.setLong(2, labworkId);
                if (preparedUpdateLabworkDifficultyByIdStatement.executeUpdate() == 0) throw new SQLException();
                Printer.println("Выполнен запрос UPDATE_LABWORK_DIFFICULTY_BY_ID");
            }
            if (laba.getDiscipline() != null) {
                preparedUpdateDisciplineByIdStatement.setString(1, laba.getDiscipline().getName());
                preparedUpdateDisciplineByIdStatement.setInt(2, laba.getDiscipline().getLectureHours());
                preparedUpdateDisciplineByIdStatement.setLong(3, getDisciplineIdByLabworkId(labworkId));
                if (preparedUpdateDisciplineByIdStatement.executeUpdate() == 0) throw new SQLException();
                Printer.println("Выполнен запрос UPDATE_DISCIPLINE_BY_ID");
            }

            databaseHandler.commit();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении группы запросов на обновление объекта!");
            databaseHandler.rollback();
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedUpdateLabworkNameByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateLabworkMinimalPointByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateLabworkDifficultyByIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateCoordinatesByLabworkIdStatement);
            databaseHandler.closePreparedStatement(preparedUpdateDisciplineByIdStatement);
            databaseHandler.setNormalMode();
        }
    }

    public void deleteLabworkById(long labworkId) throws DatabaseHandlingException {
        PreparedStatement preparedDeleteLabworkByIdStatement = null;
        try {
            preparedDeleteLabworkByIdStatement = databaseHandler.getPreparedStatement(DELETE_LABWORK_BY_ID, false);
            preparedDeleteLabworkByIdStatement.setLong(1, labworkId);
            if (preparedDeleteLabworkByIdStatement.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            Printer.printerror("Произошла ошибка при выполнении запроса DELETE_LABWORK_BY_ID!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedDeleteLabworkByIdStatement);
        }
    }

    public boolean checkLabworkUserId(long oldlabworkId, Profile profile) throws DatabaseHandlingException {
        PreparedStatement preparedSelectLabworkByIdAndUserIdStatement = null;
        try {
            preparedSelectLabworkByIdAndUserIdStatement = databaseHandler.getPreparedStatement(SELECT_LABWORK_BY_ID_AND_USER_ID, false);
            preparedSelectLabworkByIdAndUserIdStatement.setLong(1, oldlabworkId);
            preparedSelectLabworkByIdAndUserIdStatement.setLong(2, databaseCommandManager.getUserIdByUsername(profile));
            ResultSet resultSet = preparedSelectLabworkByIdAndUserIdStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            Printer.printerror("Произошла ошибка при выполнении запроса SELECT_LABWORK_BY_ID_AND_USER_ID!");
            throw new DatabaseHandlingException();
        } finally {
            databaseHandler.closePreparedStatement(preparedSelectLabworkByIdAndUserIdStatement);
        }
    }

    public void clearCollection() throws DatabaseHandlingException {
        TreeSet<LabWork> labCollection = getCollection();
        for (LabWork lab : labCollection) {
            deleteLabworkById(lab.getId());
        }
    }
}

