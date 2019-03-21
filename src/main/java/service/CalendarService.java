package service;

import app.DBInfo;
import models.db_models.Calendar;

import java.sql.*;

public class CalendarService {
    private Connection connection;
    private ResultSet resultSet;

    private final String CREATE_TABLE_SQL = "CREATE TABLE Calendar (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(255) CHECK(length(\"comment\")>0 and length(\"comment\")<256),\n" +
            "    \"appuser_id\" bigint NOT NULL REFERENCES Appuser(\"id\")\n" +
            ");";
    private final String ADD_SQL = "INSERT INTO Calendar(\"name\", \"comment\") VALUES (?, ?)";
    private final String UPDATE_SQL = "UPDATE Calendar SET \"name\" = ?, \"comment\" = ? where \"id\" = ?";
    private final String DELETE_SQL = "DELETE FROM Calendar WHERE \"id\" = ?";

    private PreparedStatement CREATE_TABLE_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public CalendarService() throws SQLException {
        DBInfo dbInfo = new DBInfo();
        boolean tableExists = false;

        connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        resultSet = connection.getMetaData().getTables(null, null, null, null);
        while(resultSet.next())
            if("Calendar".equalsIgnoreCase(resultSet.getString("table_name"))) {
                tableExists = true;
                break;
            }
        if(!tableExists) {
            CREATE_TABLE_PSTM = connection.prepareStatement(CREATE_TABLE_SQL);
            CREATE_TABLE_PSTM.executeUpdate();
        }
    }

    public boolean add(Calendar calendar) {
        try {
            ADD_PSTM = connection.prepareStatement(ADD_SQL);

            ADD_PSTM.setString(1, calendar.getName());
            ADD_PSTM.setString(2, calendar.getComment());
            ADD_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Calendar calendar, String newName, String newComment) {
        try {
            UPDATE_PSTM = connection.prepareStatement(UPDATE_SQL);
            UPDATE_PSTM.setString(1, newName);
            UPDATE_PSTM.setString(2, newComment);
            UPDATE_PSTM.setLong(3, calendar.getId());
            UPDATE_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Calendar calendar) {
        try {
            DELETE_PSTM = connection.prepareStatement(DELETE_SQL);

            DELETE_PSTM.setLong(1, calendar.getId());
            DELETE_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
