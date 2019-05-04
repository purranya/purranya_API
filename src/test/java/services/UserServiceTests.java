package services;

import app.DBInfoTest;
import models.db_models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {

    static Connection connection;

    static String tables_string = "CREATE TABLE Appuser (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"username\" varchar(50) CHECK(length(\"username\")>1 and length(\"username\")<51) NOT NULL UNIQUE,\n" +
            "    \"password\" varchar(70) CHECK(length(\"password\")>1 and length(\"password\")<71) NOT NULL \n" +
            ");";

    static String drop = "DROP TABLE Appuser;";

    UserService userService;
    User user;


    @BeforeAll
    static void containerSetup() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/purranya_test", "postgres", "");
        } catch (SQLException e) {
            try {
                DBInfoTest dbinfo = new DBInfoTest();
                connection = DriverManager.getConnection(dbinfo.get("jdbc_conn"), dbinfo.get("db_username"), dbinfo.get("db_password"));
            } catch (SQLException e2) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    @BeforeEach
    void tableSetup() throws SQLException {
        userService = new UserService(connection);
        PreparedStatement tables = connection.prepareStatement(tables_string);
        tables.executeUpdate();

        user = new User(1L, "username",
                "9830f4fabfaff141d108157700aeee3c91e8d502192d6ca0ca80e52f7f0d6d16");
    }

    @AfterEach
    void dropTables() throws SQLException {
        userService = null;
        PreparedStatement dropTables = connection.prepareStatement(drop);
        dropTables.executeUpdate();
    }

    @Test
    void validAdd() {
        assertTrue(userService.add(user));
    }

    @Test
    void addReturnsFalseWhenUserIsNull() {
        assertFalse(userService.add(null));
    }

    @Test
    void addReturnsFalseWhenUserExistsInDatabase() {
        userService.add(user);

        assertFalse(userService.add(user));
    }

    @Test
    void validGetByLogin() {
        userService.add(user);

        User result = userService.getByLogin(user.getUsername(), user.getPassword_hash());

        assertAll(
                () -> assertEquals(user.getId(), result.getId()),
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertEquals(user.getPassword_hash(), result.getPassword_hash())
        );
    }

    @Test
    void getByLoginReturnsNullWhenUserIsNotFound() {
        assertNull(userService.getByLogin(user.getUsername(), user.getPassword_hash()));
    }

    @Test
    void getByLoginReturnsNullWhenUsernameIsNull() {
        assertNull(userService.getByLogin(null, user.getPassword_hash()));
    }

    @Test
    void getByLoginReturnsNullWhenPasswordIsNull() {
        assertNull(userService.getByLogin(user.getUsername(), null));
    }

    @Test
    void getByLoginReturnsNullWhenLoginAndPasswordAreNull() {
        assertNull(userService.getByLogin(null, null));
    }

    @Test
    void validGetById() {
        userService.add(user);

        User result = userService.getById(user.getId());

        assertAll(
                () -> assertEquals(user.getId(), result.getId()),
                () -> assertEquals(user.getUsername(), result.getUsername()),
                () -> assertEquals(user.getPassword_hash(), result.getPassword_hash())
        );
    }

    @Test
    void getByIdReturnsNullWhenUserIsNotFound() {
        assertNull(userService.getById(user.getId()));
    }

    @Test
    void getReturnsNullWhenIdIsNull() {
        assertNull(userService.getById(null));
    }

    @Test
    void validUpdate() {
        userService.add(user);

        user.setUsername("newUsername");
        assertTrue(userService.update(user));
    }

    @Test
    void updateReturnsFalseWhenUserIsNull() {
        assertFalse(userService.update(null));
    }

    @Test
    void updateReturnsFalseWhenUserIsNotFound() {
        assertFalse(userService.update(user));
    }

    @Test
    void validDelete() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertEquals(user.getId(), userService.getById(user.getId()).getId()),
                () -> assertEquals(user.getUsername(), userService.getById(user.getId()).getUsername()),
                () -> assertEquals(user.getPassword_hash(), userService.getById(user.getId()).getPassword_hash()),
                () -> assertTrue(userService.delete(user.getId())),
                () -> assertNull(userService.getById(user.getId()))
        );
    }

    @Test
    void deleteReturnsFalseWhenUserIsNotFound() {
        assertFalse(userService.delete(user.getId()));
    }

    @Test
    void deleteReturnsFalseWhenIdIsNull() {
        assertFalse(userService.delete(null));
    }
}
