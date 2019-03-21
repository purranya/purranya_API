package service;

import app.DBInfo;
import models.db_models.Calendar;
import models.db_models.Event;
import models.db_models.Label;
import org.joda.time.DateTime;

import java.sql.*;

public class EventService {
    private Connection connection;
    private ResultSet resultSet;

    private static String CREATE_TABLE_SQL = "CREATE TABLE Event (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(255) CHECK(length(\"comment\")>0 and length(\"comment\")<256),\n" +
            "    \"start_date\" timestamp NOT NULL,\n" +
            "    \"end_date\" timestamp NOT NULL,\n" +
            "    \"calendar_id\" bigint NOT NULL REFERENCES Calendar(\"id\"),\n" +
            "    \"label_id\" bigint REFERENCES Label(\"id\")\n" +
            ");";
    private static String SELECT_SQL = "SELECT \"id\", \"name\", \"comment\", \"start_date\", \"end_date\", \"calendar_id\", \"label_id\" FROM Event " +
            "WHERE \"name\" = ? AND \"comment\" = ? AND \"start_date\" = ? AND \"end_date\" = ? AND \"calendar_id\" = ? AND \"label_id\" = ? LIMIT 1";
    private static String ADD_SQL = "INSERT INTO Event(\"id\", \"name\", \"comment\", \"start_date\", \"end_date\", \"calendar_id\", \"label_id\") VALUES (?, ?, ?, ?, ?, ?)";
    private static String UPDATE_SQL = "UPDATE Event SET \"name\" = ? AND \"comment\" = ? AND \"start_date\" = ? AND \"end_date\" = ? AND \"calendar_id\" = ? AND \"label_id\" = ? WHERE \"id\" = ?";
    private static String DELETE_SQL = "DELETE FROM Event WHERE \"id\" = ?";

    private PreparedStatement CREATE_TABLE_PSTM = null;
    private PreparedStatement SELECT_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public EventService() throws SQLException {
        DBInfo dbInfo = new DBInfo();
        boolean tableExists = false;

        connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        resultSet = connection.getMetaData().getTables(null, null, null, null);
        while(resultSet.next())
            if("Event".equalsIgnoreCase(resultSet.getString("table_name"))) {
                tableExists = true;
                break;
            }
        if(!tableExists) {
            CREATE_TABLE_PSTM = connection.prepareStatement(CREATE_TABLE_SQL);
            CREATE_TABLE_PSTM.executeUpdate();
        }
    }

    public boolean select(Calendar calendar, Label label, Event event) {
        try {
            SELECT_PSTM = connection.prepareStatement(SELECT_SQL);

            SELECT_PSTM.setString(1, event.getName());
            SELECT_PSTM.setString(2, event.getComment());
            SELECT_PSTM.setString(3, event.getStartDate().toString());
            SELECT_PSTM.setString(4, event.getEndDate().toString());
            SELECT_PSTM.setLong(5, calendar.getId());
            SELECT_PSTM.setLong(6, label.getId());
            SELECT_PSTM.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Calendar calendar, Label label, Event event) {
        try {
            ADD_PSTM = connection.prepareStatement(ADD_SQL);

            ADD_PSTM.setString(1, event.getName());
            ADD_PSTM.setString(2, event.getComment());
            ADD_PSTM.setString(3, event.getStartDate().toString());
            ADD_PSTM.setString(4, event.getEndDate().toString());
            ADD_PSTM.setLong(5, calendar.getId());
            ADD_PSTM.setLong(6, label.getId());
            ADD_PSTM.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Event event, Calendar calendar, Label label, String newName, String newComment, DateTime newStartDate, DateTime newEndDate, Long newLabelId) {
        try {
            UPDATE_PSTM = connection.prepareStatement(UPDATE_SQL);

            UPDATE_PSTM.setString(1, newName);
            UPDATE_PSTM.setString(2, newComment);
            UPDATE_PSTM.setString(3, newStartDate.toString());
            UPDATE_PSTM.setString(4, newEndDate.toString());
            UPDATE_PSTM.setLong(5, calendar.getId());
            UPDATE_PSTM.setLong(6, label.getId());
            UPDATE_PSTM.setLong(7, event.getId());
            UPDATE_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Event event) {
        try {
            DELETE_PSTM = connection.prepareStatement(DELETE_SQL);

            DELETE_PSTM.setLong(1, event.getId());
            UPDATE_PSTM.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
