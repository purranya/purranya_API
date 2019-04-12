package service;

import app.DBInfo;
import models.db_models.Event;
import org.joda.time.DateTime;

import java.sql.*;
import java.util.ArrayList;

public class EventService {
    private Connection connection;

    private static String SQL_SELECT_BY_ID = "SELECT \"id\",\"name\",\"comment\",\"start_date\",\"end_date\",\"calendar_id\",\"label_id\" FROM Event WHERE \"id\"=? LIMIT 1";
    private static String SQL_ADD = "INSERT INTO Event(\"id\", \"name\", \"comment\", \"start_date\", \"end_date\", \"calendar_id\", \"label_id\") VALUES (?, ?, ?, ?, ?, ?)";
    private static String SQL_UPDATE = "UPDATE Event SET \"name\" = ? AND \"comment\" = ? AND \"start_date\" = ? AND \"end_date\" = ? AND \"calendar_id\" = ? AND \"label_id\" = ? WHERE \"id\" = ?";
    private static String SQL_DELETE = "DELETE FROM Event WHERE \"id\" = ?";
    private static String SQL_SELECT_BY_CALENDAR = "SELECT \"id\",\"name\",\"comment\",\"start_date\",\"end_date\",\"calendar_id\",\"label_id\" FROM Event WHERE \"calendar_id\"=?";

    private PreparedStatement SELECT_BY_ID_PSTM = null;
    private PreparedStatement SELECT_BY_CALENDAR_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public EventService() throws SQLException {
        DBInfo dbInfo = new DBInfo();

        connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        try
        {
            connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Event getById(Long id){
        try {
            if(SELECT_BY_ID_PSTM==null)
                SELECT_BY_ID_PSTM = connection.prepareStatement(SQL_SELECT_BY_ID);

            SELECT_BY_ID_PSTM.setLong(1, id);
            ResultSet rs = SELECT_BY_ID_PSTM.executeQuery();

            if(rs.next())
            {
                Event e = new Event();
                e.setId(rs.getLong(1));
                e.setName(rs.getString(2));
                e.setComment(rs.getString(3));
                e.setStartDate(new DateTime(rs.getString(4)));
                e.setEndDate(new DateTime(rs.getString(5)));
                e.setCalendar_id(rs.getLong(6));
                e.setLabel_id(rs.getLong(7));

                return e;
            }
            else
                return null;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Event event) {
        try {
            if(ADD_PSTM==null)
                ADD_PSTM = connection.prepareStatement(SQL_ADD);

            ADD_PSTM.setString(1, event.getName());
            ADD_PSTM.setString(2, event.getComment());
            ADD_PSTM.setString(3, event.getStartDate().toString());
            ADD_PSTM.setString(4, event.getEndDate().toString());
            ADD_PSTM.setLong(5, event.getCalendar_id());
            ADD_PSTM.setLong(6, event.getLabel_id());
            int added = ADD_PSTM.executeUpdate();
            return added>0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Event event) {
        try {
            if(UPDATE_PSTM==null)
                UPDATE_PSTM = connection.prepareStatement(SQL_UPDATE);

            UPDATE_PSTM.setString(1, event.getName());
            UPDATE_PSTM.setString(2, event.getComment());
            UPDATE_PSTM.setString(3, event.getStartDate().toString());
            UPDATE_PSTM.setString(4, event.getEndDate().toString());
            UPDATE_PSTM.setLong(5, event.getCalendar_id());
            UPDATE_PSTM.setLong(6, event.getLabel_id());
            UPDATE_PSTM.setLong(7, event.getId());
            int updated = UPDATE_PSTM.executeUpdate();
            return updated>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        try {
            if(DELETE_PSTM==null)
                DELETE_PSTM = connection.prepareStatement(SQL_DELETE);

            DELETE_PSTM.setLong(1, id);
            int deleted = UPDATE_PSTM.executeUpdate();
            return deleted>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Event> getByCalendar(Long id)
    {
        try {
            if(SELECT_BY_CALENDAR_PSTM==null)
                SELECT_BY_CALENDAR_PSTM = connection.prepareStatement(SQL_SELECT_BY_CALENDAR);

            SELECT_BY_CALENDAR_PSTM.setLong(1, id);
            ResultSet rs = SELECT_BY_ID_PSTM.executeQuery();
            ArrayList<Event> events = new ArrayList<>();
            while(rs.next())
            {
                Event e = new Event();
                e.setId(rs.getLong(1));
                e.setName(rs.getString(2));
                e.setComment(rs.getString(3));
                e.setStartDate(new DateTime(rs.getString(4)));
                e.setEndDate(new DateTime(rs.getString(5)));
                e.setCalendar_id(rs.getLong(6));
                e.setLabel_id(rs.getLong(7));
                events.add(e);
            }
            return events;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
