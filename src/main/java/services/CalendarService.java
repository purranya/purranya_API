package services;

import app.DBInfo;
import models.db_models.Calendar;

import java.sql.*;
import java.util.ArrayList;

import static services.ServiceOptions.logErrors;

public class CalendarService {
    private Connection connection;

    private PreparedStatement SELECT_BY_ID_PSTM = null;
    private PreparedStatement SELECT_BY_USER_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    private static String SQL_SELECT_BY_ID = "SELECT \"id\",\"name\",\"comment\",\"appuser_id\" FROM Calendar WHERE \"id\" = ? LIMIT 1";
    private static String SQL_SELECT_BY_USER = "SELECT \"id\",\"name\",\"comment\",\"appuser_id\" FROM Calendar WHERE \"appuser_id\" = ?";
    private static String SQL_ADD = "INSERT INTO Calendar(\"name\",\"comment\",\"appuser_id\") VALUES(?,?,?)";
    private static String SQL_UPDATE = "UPDATE Calendar SET \"name\"=?,\"comment\"=?,\"appuser_id\"=? WHERE \"id\"=?";
    private static String SQL_DELETE = "DELETE FROM Calendar WHERE \"id\"=?";

    public CalendarService() {
        DBInfo dbInfo = new DBInfo();

        try
        {
            connection = DriverManager.getConnection(dbInfo.get("jdbc_conn"), dbInfo.get("db_username"), dbInfo.get("db_password"));
        } catch (Exception e)
        {
            if(logErrors())
                e.printStackTrace();
        }
    }

    public CalendarService(Connection connection) {
        this.connection = connection;
    }

    public Calendar getById(Long id) {
        try {
            if(SELECT_BY_ID_PSTM==null)
                SELECT_BY_ID_PSTM = connection.prepareStatement(SQL_SELECT_BY_ID);

            SELECT_BY_ID_PSTM.setLong(1, id);
            ResultSet rs = SELECT_BY_ID_PSTM.executeQuery();

            if(rs.next())
            {
                Calendar c = new Calendar();
                c.setId(rs.getLong(1));
                c.setName(rs.getString(2));
                c.setComment(rs.getString(3));
                c.setUser_id(rs.getLong(4));
                return c;
            }
            else
                return null;
        } catch (Exception e) {
            if(logErrors())
                e.printStackTrace();
            return null;
        }
    }

    public boolean add(Calendar calendar) {
        try {
            if(ADD_PSTM==null)
                ADD_PSTM = connection.prepareStatement(SQL_ADD);

            ADD_PSTM.setString(1, calendar.getName());
            ADD_PSTM.setString(2, calendar.getComment());
            ADD_PSTM.setLong(3, calendar.getUser_id());
            int addedCount = ADD_PSTM.executeUpdate();
            return addedCount>0;
        } catch (Exception e) {
            if(logErrors())
                e.printStackTrace();
            return false;
        }
    }

    public boolean update(Calendar calendar) {
        try {
            if(UPDATE_PSTM==null)
                UPDATE_PSTM = connection.prepareStatement(SQL_UPDATE);

            UPDATE_PSTM.setString(1, calendar.getName());
            UPDATE_PSTM.setString(2, calendar.getComment());
            UPDATE_PSTM.setLong(3, calendar.getUser_id());
            UPDATE_PSTM.setLong(4, calendar.getId());
            int updatedCount = UPDATE_PSTM.executeUpdate();
            return updatedCount>0;
        } catch (Exception e) {
            if(logErrors())
                e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        try {
            if(DELETE_PSTM==null)
            DELETE_PSTM = connection.prepareStatement(SQL_DELETE);
            DELETE_PSTM.setLong(1, id);
            int deletedCount= DELETE_PSTM.executeUpdate();
            return deletedCount>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Calendar> getByUserId(Long userId)
    {
        try {
            if(SELECT_BY_USER_PSTM==null)
                SELECT_BY_USER_PSTM = connection.prepareStatement(SQL_SELECT_BY_USER);

            SELECT_BY_USER_PSTM.setLong(1, userId);
            ResultSet rs = SELECT_BY_USER_PSTM.executeQuery();
            ArrayList<Calendar> calendars = new ArrayList<>();
            while(rs.next())
            {
                Calendar c = new Calendar();
                c.setId(rs.getLong(1));
                c.setName(rs.getString(2));
                c.setComment(rs.getString(3));
                c.setUser_id(rs.getLong(4));
                calendars.add(c);
            }
            return calendars;
        } catch (Exception e) {
            if(logErrors())
                e.printStackTrace();
            return null;
        }
    }
}