package service;

import models.db_models.Calendar;

import java.sql.*;

/** TODO wpisać URL!! */
public class CalendarService {
    private final String url = "";
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement = null;

    private final String CREATE_TABLE_SQL = "CREATE TABLE Calendar (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(255) CHECK(length(\"comment\")>0 and length(\"comment\")<256),\n" +
            "    \"appuser_id\" bigint NOT NULL REFERENCES Appuser(\"id\")\n" +
            ");";
    private final String ADD_SQL = "INSERT INTO Calendar(name, comment) VALUES (?, ?)";
    private final String UPDATE_SQL = "UPDATE Calendar SET name = ?, comment = ? where id = ?";
    private final String UPDATE_NAME_SQL = "UPDATE Calendar SET name = ? where id = ?";
    private final String UPDATE_COMMENT_SQL = "UPDATE Calendar SET comment = ? where id = ?";
    private final String DELETE_SQL = "DELETE FROM Calendar WHERE id = ?";


    public CalendarService() throws SQLException {
        boolean tableExists = false;

        connection = DriverManager.getConnection(url);
        resultSet = connection.getMetaData().getTables(null, null, null, null);
        while(resultSet.next())
            if("Calendar".equalsIgnoreCase(resultSet.getString("table_name"))) {
                tableExists = true;
                break;
            }
        if(!tableExists) {
            preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL);
            preparedStatement.executeUpdate();
        }

        preparedStatement = null;
    }

    public void add(Calendar calendar) throws SQLException {
        preparedStatement = connection.prepareStatement(ADD_SQL);

        preparedStatement.setString(1, calendar.getName());
        preparedStatement.setString(2, calendar.getComment());
        preparedStatement.executeUpdate();

        preparedStatement = null;
    }

    public void update(Calendar calendar, String newName, String newComment) throws SQLException {
        preparedStatement = connection.prepareStatement(UPDATE_SQL);

        preparedStatement.setString(1, newName);
        preparedStatement.setString(2, newComment);
        preparedStatement.setLong(3, calendar.getId());
        preparedStatement.executeUpdate();

        preparedStatement = null;
    }

    public void updateName(Calendar calendar, String newName) throws SQLException {
        preparedStatement = connection.prepareStatement(UPDATE_NAME_SQL);

        preparedStatement.setString(1, newName);
        preparedStatement.setLong(2, calendar.getId());
        preparedStatement.executeUpdate();

        preparedStatement = null;
    }

    public void updateComment(Calendar calendar, String newComment) throws SQLException {
        preparedStatement = connection.prepareStatement(UPDATE_COMMENT_SQL);

        preparedStatement.setString(1, newComment);
        preparedStatement.setLong(2, calendar.getId());
        preparedStatement.executeUpdate();

        preparedStatement = null;
    }

    public void delete(Calendar calendar) throws SQLException {
        preparedStatement = connection.prepareStatement(DELETE_SQL);

        preparedStatement.setLong(1, calendar.getId());
        preparedStatement.executeUpdate();

        preparedStatement = null;
    }
}
