package service;

import app.DBInfo;
import models.db_models.User;

import java.sql.*;

public class UserService
{
    private Connection connection;

    private static PreparedStatement ADD_PSTM;
    private static PreparedStatement SELECT_PSTM;

    private static String ADD_SQL = "INSERT INTO Appuser(\"username\",\"password\") VALUES(?,?)";
    private static String SELECT_SQL = "SELECT \"id\",\"username\",\"password\" FROM Appuser WHERE \"username\"=? and \"password\"=?  LIMIT 1";

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

    public boolean addUser(User user)
    {
        try {
            if(ADD_PSTM ==null)
                ADD_PSTM =connection.prepareStatement(ADD_SQL);

            ADD_PSTM.setString(1,user.getUsername());
            ADD_PSTM.setString(2,user.getPassword_hash());

            ADD_PSTM.execute();
            return true;

        } catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public User getUser(String username, String hash)
    {
        try {
            if(SELECT_PSTM ==null)
                SELECT_PSTM =connection.prepareStatement(SELECT_SQL);

            SELECT_PSTM.setString(1,username);
            SELECT_PSTM.setString(2,hash);

            ResultSet rs = SELECT_PSTM.executeQuery();
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
}
