package services;

import app.DBInfoTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTests {

    static Connection connection;

    static String tables_string = "CREATE TABLE Appuser (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"username\" varchar(50) CHECK(length(\"username\")>1 and length(\"username\")<51) NOT NULL UNIQUE,\n" +
            "    \"password\" varchar(70) CHECK(length(\"password\")>1 and length(\"password\")<71) NOT NULL \n" +
            ");";

    static String drop = "DROP TABLE Appuser;";

    UserService userService;


    @BeforeAll
    static void containerSetup()
    {
        DBInfoTest dbinfo = new DBInfoTest();
        try {
            System.err.println("Trying Travis conf...");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/purranya_test", "postgres", "");
        } catch ( SQLException e)
        {
            try {
                System.err.println("Travis conf failed, trying env conf...");
                connection = DriverManager.getConnection(dbinfo.get("jdbc_conn"), dbinfo.get("db_username"), dbinfo.get("db_password"));
            } catch ( SQLException e2) {
                e.printStackTrace();
                System.err.println("Env conf failed, throwing an exception");
                throw new RuntimeException();
            }
        }
    }

    @BeforeEach
    void tableSetup() throws SQLException
    {
        userService = new UserService(connection);
        PreparedStatement tables = connection.prepareStatement(tables_string);
        tables.executeUpdate();
    }

    @AfterEach
    void dropTables() throws SQLException
    {
        userService = null;
        PreparedStatement dropTables = connection.prepareStatement(drop);
        dropTables.executeUpdate();
    }

    @Test
    void test()
    {
        assertThat(true).isEqualTo(true);
    }
}
