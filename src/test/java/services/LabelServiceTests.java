package services;

import app.DBInfoTest;
import models.db_models.Calendar;
import models.db_models.Label;
import models.db_models.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LabelServiceTests {

    static Connection connection;

    static String createUserTable = "CREATE TABLE Appuser (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"username\" varchar(50) CHECK(length(\"username\")>1 and length(\"username\")<51) NOT NULL UNIQUE,\n" +
            "    \"password\" varchar(70) CHECK(length(\"password\")>1 and length(\"password\")<71) NOT NULL  \n" +
            ");";
    static String createLabelTable = "CREATE TABLE Label (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"color_r\" smallint CHECK(\"color_r\">=0 and \"color_r\"<256) NOT NULL,\n" +
            "    \"color_b\" smallint CHECK(\"color_b\">=0 and \"color_b\"<256) NOT NULL,\n" +
            "    \"color_g\" smallint CHECK(\"color_g\">=0 and \"color_g\"<256) NOT NULL,\n" +
            "    \"calendar_id\" bigint NOT NULL REFERENCES Calendar(\"id\")\n" +
            ");";
    static String createCalendarTable = "CREATE TABLE Calendar (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(255) CHECK(length(\"comment\")>0 and length(\"comment\")<256),\n" +
            "    \"appuser_id\" bigint NOT NULL REFERENCES Appuser(\"id\")\n" +
            ");";

    static String dropLabelTable = "DROP TABLE Label;";
    static String dropCalendarTable = "DROP TABLE Calendar;";
    static String dropUserTable = "DROP TABLE Appuser;";

    UserService userService;
    CalendarService calendarService;
    LabelService labelService;
    Label label;
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
        labelService = new LabelService(connection);
        userService = new UserService(connection);
        calendarService = new CalendarService(connection);
        PreparedStatement calendarTable = connection.prepareStatement(createCalendarTable);
        PreparedStatement labelTable = connection.prepareStatement(createLabelTable);
        PreparedStatement userTable = connection.prepareStatement(createUserTable);
        userTable.executeUpdate();
        calendarTable.executeUpdate();
        labelTable.executeUpdate();

        user = new User(1L, "username",
                "9830f4fabfaff141d108157700aeee3c91e8d502192d6ca0ca80e52f7f0d6d16");
        calendar = new Calendar(1L, "Sample name", "Sample comment", 1L);
        label = new Label(1L, "Sample name", 169, 169, 169, 1L);
    }

    @AfterEach
    void dropTables() throws SQLException {
        userService = null;
        labelService = null;
        calendarService = null;
        user = null;
        label = null;
        calendar = null;
        PreparedStatement dropUserTablePstmt = connection.prepareStatement(dropUserTable);
        PreparedStatement dropLabelTablePstmt = connection.prepareStatement(dropLabelTable);
        PreparedStatement dropCalendarTablePstmt = connection.prepareStatement(dropCalendarTable);
        dropLabelTablePstmt.executeUpdate();
        dropCalendarTablePstmt.executeUpdate();
        dropUserTablePstmt.executeUpdate();
    }

    @Test
    void validAdd() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(calendarService.add(calendar)),
                () -> assertTrue(labelService.add(label))
        );
    }

    @Test
    void addReturnsFalseWhenLabelIsNull() {
        assertFalse(labelService.add(null));
    }

    @Test
    void addReturnsFalseWhenLabelExistsInDatabase() {
        labelService.add(label);

        assertFalse(labelService.add(label));
    }

    @Test
    void validGetByCalendar() {
        userService.add(user);
        calendarService.add(calendar);
        labelService.add(label);

        List<Label> result = labelService.getByCalendar(label.getCalendar_id());

        assertAll(
                () -> assertEquals(label.getName(), result.get(0).getName()),
                () -> assertEquals(label.getCalendar_id(), result.get(0).getCalendar_id()),
                () -> assertEquals(label.getColor_r(), result.get(0).getColor_r()),
                () -> assertEquals(label.getColor_g(), result.get(0).getColor_g()),
                () -> assertEquals(label.getColor_b(), result.get(0).getColor_b())
        );
    }

    @Test
    void getByCalendarReturnsEmptyListWhenLabelIsNotFound() {
        assertEquals(new ArrayList<Label>(), labelService.getByCalendar(calendar.getId()));
    }

    @Test
    void getByCalendarReturnsEmptyListWhenCalendarIdIsNull() {
        assertNull(labelService.getByCalendar(null));
    }

    @Test
    void validGetById() {
        userService.add(user);
        calendarService.add(calendar);
        labelService.add(label);

        Label result = labelService.getById(label.getId());

        assertAll(
                () -> assertEquals(label.getCalendar_id(), result.getCalendar_id()),
                () -> assertEquals(label.getName(), result.getName()),
                () -> assertEquals(label.getColor_r(), result.getColor_r()),
                () -> assertEquals(label.getColor_g(), result.getColor_g()),
                () -> assertEquals(label.getColor_b(), result.getColor_b())
        );
    }

    @Test
    void getByIdReturnsNullWhenLabelIsNotFound() {
        assertNull(labelService.getById(label.getId()));
    }

    @Test
    void getByIdReturnsNullWhenIdIsNull() {
        assertNull(labelService.getById(null));
    }

    @Test
    void validUpdate() {
        userService.add(user);
        calendarService.add(calendar);
        labelService.add(label);

        label.setName("New name");
        assertTrue(labelService.update(label));
    }

    @Test
    void updateReturnsFalseWhenLabelIsNull() {
        assertFalse(labelService.update(null));
    }

    @Test
    void updateReturnsFalseWhenLabelIsNotFound() {
        assertFalse(labelService.update(label));
    }

    @Test
    void validDelete() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(calendarService.add(calendar)),
                () -> assertTrue(labelService.add(label)),
                () -> assertEquals(label.getCalendar_id(), labelService.getById(label.getId()).getCalendar_id()),
                () -> assertEquals(label.getName(), labelService.getById(label.getId()).getName()),
                () -> assertEquals(label.getColor_r(), labelService.getById(label.getId()).getColor_r()),
                () -> assertEquals(label.getColor_g(), labelService.getById(label.getId()).getColor_g()),
                () -> assertEquals(label.getColor_b(), labelService.getById(label.getId()).getColor_b()),
                () -> assertTrue(labelService.delete(label.getId())),
                () -> assertNull(labelService.getById(label.getId()))
        );
    }

    @Test
    void deleteReturnsFalseWhenLabelIsNotFound() {
        assertFalse(labelService.delete(label.getId()));
    }

    @Test
    void deleteReturnsFalseWhenIdIsNull() {
        assertFalse(labelService.delete(null));
    }
}
