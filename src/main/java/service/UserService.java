package service;

import app.DBInfo;
import models.db_models.User;

import java.sql.*;

public class UserService
{
    private Connection connection;
    private ResultSet resultSet;
    private static PreparedStatement ADD_USER_PSTM;
    private static PreparedStatement CREATE_TABLE_PSTM;
    private static PreparedStatement GET_USER_PSTM;

    private static String ADD_USER_SQL = "INSERT INTO Appuser(\"username\",\"password\") VALUES(?,?)";
    private static String GET_USER_SQL = "SELECT \"id\",\"username\",\"password\" FROM Appuser WHERE \"username\"=? and \"password\"=?  LIMIT 1";

    private final String CREATE_TABLE_SQL="CREATE TABLE Appuser (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"username\" varchar(50) CHECK(length(\"username\")>1 and length(\"username\")<51) NOT NULL UNIQUE,\n" +
            "    \"password\" varchar(70) CHECK(length(\"password\")>1 and length(\"password\")<71) NOT NULL UNIQUE  \n" +
            ");";

    public UserService()
    {
        DBInfo db = new DBInfo();

        boolean tableExists = false;

        try
        {
            connection = DriverManager.getConnection(db.get("jdbc_conn"), db.get("db_username"), db.get("db_password"));

            resultSet = connection.getMetaData().getTables(null, null, null, null);
            while (resultSet.next())
                if ("Appuser".equalsIgnoreCase(resultSet.getString("table_name")))
                {
                    tableExists = true;
                    break;
                }

            if (!tableExists)
            {
                CREATE_TABLE_PSTM = connection.prepareStatement(CREATE_TABLE_SQL);
                CREATE_TABLE_PSTM.executeUpdate();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user)
    {
        try {
            if(ADD_USER_PSTM==null)
                ADD_USER_PSTM=connection.prepareStatement(ADD_USER_SQL);

            ADD_USER_PSTM.setString(1,user.getUsername());
            ADD_USER_PSTM.setString(2,user.getPassword_hash());

            ADD_USER_PSTM.execute();
            return true;

        } catch ( SQLException e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public User getUser(String username, String hash)
    {
        try {
            if(GET_USER_PSTM==null)
                GET_USER_PSTM=connection.prepareStatement(GET_USER_SQL);

            GET_USER_PSTM.setString(1,username);
            GET_USER_PSTM.setString(2,hash);

            ResultSet rs = GET_USER_PSTM.executeQuery();
            User u = null;

            if(rs.next())
            {
                u = new User(rs.getLong(1),rs.getString(2),rs.getString(3));
            }

            return u;
        } catch ( SQLException e )
        {
            e.printStackTrace();
            return null;
        }
    }
}