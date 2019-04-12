package service;

import app.DBInfo;
import models.db_models.User;

import java.sql.*;

public class UserService
{
    private Connection connection;

    private static PreparedStatement ADD_PSTM;
    private static PreparedStatement SELECT_BY_LOGIN_PSTM;
    private static PreparedStatement SELECT_BY_ID_PSTM;
    private static PreparedStatement UPDATE_PSTM;
    private static PreparedStatement DELETE_PSTM;

    private static String SQL_ADD = "INSERT INTO Appuser(\"username\",\"password\") VALUES(?,?)";
    private static String SQL_SELECT = "SELECT \"id\",\"username\",\"password\" FROM Appuser WHERE \"username\"=? and \"password\"=?  LIMIT 1";
    private static String SQL_SELECT_BY_ID = "SELECT \"id\",\"username\",\"password\" FROM Appuser WHERE \"id\"=?  LIMIT 1";
    private static String SQL_UPDATE = "UPDATE Appuser SET \"username\"=?,\"password\"=? WHERE \"id\"=?";
    private static String SQL_DELETE = "DELETE FROM Appuser WHERE \"id\"=?";
    public UserService()
    {
        DBInfo db = new DBInfo();

        try
        {
            connection = DriverManager.getConnection(db.get("jdbc_conn"), db.get("db_username"), db.get("db_password"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean add(User user)
    {
        try {
            if(ADD_PSTM ==null)
                ADD_PSTM =connection.prepareStatement(SQL_ADD);

            ADD_PSTM.setString(1,user.getUsername());
            ADD_PSTM.setString(2,user.getPassword_hash());

            int added = ADD_PSTM.executeUpdate();
            return added>0;

        } catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public User getByLogin(String username, String hash)
    {
        try {
            if(SELECT_BY_LOGIN_PSTM ==null)
                SELECT_BY_LOGIN_PSTM =connection.prepareStatement(SQL_SELECT);

            SELECT_BY_LOGIN_PSTM.setString(1,username);
            SELECT_BY_LOGIN_PSTM.setString(2,hash);

            ResultSet rs = SELECT_BY_LOGIN_PSTM.executeQuery();
            User u = null;

            if(rs.next())
            {
                u = new User(rs.getLong(1),rs.getString(2),rs.getString(3));
            }

            return u;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    public User getById(Long id)
    {
        try {
            if(SELECT_BY_ID_PSTM ==null)
                SELECT_BY_ID_PSTM =connection.prepareStatement(SQL_SELECT_BY_ID);

            SELECT_BY_ID_PSTM.setLong(1,id);

            ResultSet rs = SELECT_BY_ID_PSTM.executeQuery();
            User u = null;

            if(rs.next())
            {
                u = new User(rs.getLong(1),rs.getString(2),rs.getString(3));
            }

            return u;
        } catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(User user)
    {
        try {
            if(UPDATE_PSTM ==null)
                UPDATE_PSTM =connection.prepareStatement(SQL_UPDATE);

            UPDATE_PSTM.setString(1,user.getUsername());
            UPDATE_PSTM.setString(2,user.getPassword_hash());
            UPDATE_PSTM.setLong(3,user.getId());

            int updated = UPDATE_PSTM.executeUpdate();
            return updated>0;

        } catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id)
    {
        try {
            if(DELETE_PSTM ==null)
                DELETE_PSTM =connection.prepareStatement(SQL_DELETE);

            DELETE_PSTM.setLong(1,id);

            int deleted = DELETE_PSTM.executeUpdate();
            return deleted>0;

        } catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }
    }

}
