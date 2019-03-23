package service;

import app.DBInfo;
import models.db_models.Calendar;
import models.db_models.User;

import java.sql.*;

public class CalendarService {
    private Connection connection;
    private ResultSet resultSet;

    private final String SELECT_SQL = "SELECT \"id\", \"name\", \"comment\", \"appuser_id\" FROM Calendar " +
            "WHERE \"name\" = ? AND \"comment\" = ? AND \"appuser_id\" = ?";
    private final String ADD_SQL = "INSERT INTO Calendar(\"name\", \"comment\",\"appuser_id\") VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE Calendar SET \"name\" = ?, \"comment\" = ? WHERE \"id\" = ?";
    private final String DELETE_SQL = "DELETE FROM Calendar WHERE \"id\" = ?";

    private PreparedStatement SELECT_PSTM = null;
    private PreparedStatement CREATE_TABLE_PSTM = null;
    private PreparedStatement ADD_PSTM = null;
    private PreparedStatement UPDATE_PSTM = null;
    private PreparedStatement DELETE_PSTM = null;

    public CalendarService() {
        DBInfo db = new DBInfo();

        try
        {
            connection = DriverManager.getConnection(db.get("jdbc_conn"), db.get("db_username"), db.get("db_password"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean select(Calendar calendar, User user) {
        try {
            if(SELECT_PSTM==null)
                SELECT_PSTM = connection.prepareStatement(SELECT_SQL);

            SELECT_PSTM.setString(1, calendar.getName());
            SELECT_PSTM.setString(2, calendar.getComment());
            SELECT_PSTM.setLong(3, user.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(Calendar calendar, User user) {
        try {
            if(ADD_PSTM==null)
                ADD_PSTM = connection.prepareStatement(ADD_SQL);

            ADD_PSTM.setString(1, calendar.getName());
            ADD_PSTM.setString(2, calendar.getComment());
            ADD_PSTM.setLong(3, user.getId());
            ADD_PSTM.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Calendar calendar, String newName, String newComment) {
        try {
            if(UPDATE_PSTM==null)
                UPDATE_PSTM = connection.prepareStatement(UPDATE_SQL);

            UPDATE_PSTM.setString(1, newName);
            UPDATE_PSTM.setString(2, newComment);
            UPDATE_PSTM.setLong(3, calendar.getId());
            UPDATE_PSTM.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Calendar calendar) {
        try {
            if(DELETE_PSTM==null)
            DELETE_PSTM = connection.prepareStatement(DELETE_SQL);

            DELETE_PSTM.setLong(1, calendar.getId());
            DELETE_PSTM.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
