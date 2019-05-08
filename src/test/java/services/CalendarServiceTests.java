package services;

import app.DBInfoTest;
import models.db_models.Calendar;
import models.db_models.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CalendarServiceTests {

    static Connection connection;

    static String tables_string = "CREATE TABLE Appuser (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"username\" varchar(50) CHECK(length(\"username\")>1 and length(\"username\")<51) NOT NULL UNIQUE,\n" +
            "    \"password\" varchar(70) CHECK(length(\"password\")>1 and length(\"password\")<71) NOT NULL \n" +
            ");";
    static String createCalendarTable = "CREATE TABLE Calendar (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(255) CHECK(length(\"comment\")>0 and length(\"comment\")<256),\n" +
            "    \"appuser_id\" bigint NOT NULL REFERENCES Appuser(\"id\")\n" +
            ");";

    static String drop = "DROP TABLE Appuser;";
    static String dropCalendarTable = "DROP TABLE Calendar;";

    UserService userService;
    CalendarService calendarService;
    User user;
    Calendar calendar;

    @BeforeAll
    static void containerSetup() {
        ServiceOptions.serviceExceptionLogging=false;
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

    @AfterAll
    static void restoreServiceLogging()
    {
        ServiceOptions.serviceExceptionLogging=true;
    }

    @BeforeEach
    void tableSetup() throws SQLException {
        userService = new UserService(connection);
        calendarService = new CalendarService(connection);
        PreparedStatement tables = connection.prepareStatement(tables_string);
        PreparedStatement createCalendarTablePstmt = connection.prepareStatement(createCalendarTable);
        tables.executeUpdate();
        createCalendarTablePstmt.executeUpdate();

        user = new User(1L, "username",
                "9830f4fabfaff141d108157700aeee3c91e8d502192d6ca0ca80e52f7f0d6d16");
        calendar = new Calendar(1L, "Sample name", "Sample comment", 1L);
    }

    @AfterEach
    void dropTables() throws SQLException {
        userService = null;
        PreparedStatement dropTables = connection.prepareStatement(drop);
        PreparedStatement dropCalendarTablePstmt = connection.prepareStatement(dropCalendarTable);
        dropCalendarTablePstmt.executeUpdate();
        dropTables.executeUpdate();
    }

    @Test
    void validAdd() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(calendarService.add(calendar))
        );
    }

    @Test
    void addReturnsFalseWhenCalendarIsNull() {
        assertFalse(calendarService.add(null));
    }

    @Test
    void addReturnsFalseWhenCalendarExistsInDatabase() {
        calendarService.add(calendar);

        assertFalse(calendarService.add(calendar));
    }

    @Test
    void validGetByUserId() {
        userService.add(user);
        calendarService.add(calendar);

        List<Calendar> result = calendarService.getByUserId(calendar.getUser_id());

        assertAll(
                () -> assertEquals(calendar.getName(), result.get(0).getName()),
                () -> assertEquals(calendar.getComment(), result.get(0).getComment()),
                () -> assertEquals(calendar.getUser_id(), result.get(0).getUser_id())
        );
    }

    @Test
    void getByUserIdReturnsNullWhenUserIsNotFound() {
        assertEquals(new ArrayList<>(), calendarService.getByUserId(calendar.getUser_id()));
    }

    @Test
    void getByUserIdReturnsNullWhenUserIdIsNull() {
        assertNull(calendarService.getByUserId(null));
    }

    @Test
    void validGetById() {
        userService.add(user);
        calendarService.add(calendar);

        Calendar result = calendarService.getById(calendar.getId());

        assertAll(
                () -> assertEquals(calendar.getName(), result.getName()),
                () -> assertEquals(calendar.getComment(), result.getComment()),
                () -> assertEquals(calendar.getUser_id(), result.getUser_id())
        );
    }

    @Test
    void getByIdReturnsNullWhenCalendarIsNotFound() {
        assertNull(calendarService.getById(calendar.getId()));
    }

    @Test
    void getByIdReturnsNullWhenIdIsNull() {
        assertNull(calendarService.getById(null));
    }

    @Test
    void validUpdate() {
        userService.add(user);
        calendarService.add(calendar);

        calendar.setComment("New comment");
        assertTrue(calendarService.update(calendar));
    }

    @Test
    void updateReturnsFalseWhenCalendarIsNull() {
        assertFalse(calendarService.update(null));
    }

    @Test
    void updateReturnsFalseWhenCalendarIsNotFound() {
        assertFalse(calendarService.update(calendar));
    }

    @Test
    void validDelete() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(calendarService.add(calendar)),
                () -> assertEquals(calendar.getName(), calendarService.getById(calendar.getId()).getName()),
                () -> assertEquals(calendar.getComment(), calendarService.getById(calendar.getId()).getComment()),
                () -> assertEquals(calendar.getUser_id(), calendarService.getById(calendar.getId()).getUser_id()),
                () -> assertTrue(calendarService.delete(calendar.getId())),
                () -> assertNull(calendarService.getById(calendar.getId()))
        );
    }

    @Test
    void deleteReturnsFalseWhenUserIsNotFound() {
        assertFalse(calendarService.delete(calendar.getId()));
    }

    @Test
    void deleteReturnsFalseWhenIdIsNull() {
        assertFalse(calendarService.delete(null));
    }
}
