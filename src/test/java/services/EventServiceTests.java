package services;

import app.DBInfoTest;
import models.db_models.Calendar;
import models.db_models.Event;
import models.db_models.Label;
import models.db_models.User;
import org.joda.time.DateTime;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTests {
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
    static String createEventTable = "CREATE TABLE Event (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(255) CHECK(length(\"comment\")>0 and length(\"comment\")<256),\n" +
            "    \"start_date\" timestamp NOT NULL,\n" +
            "    \"end_date\" timestamp NOT NULL,\n" +
            "    \"calendar_id\" bigint NOT NULL REFERENCES Calendar(\"id\"),\n" +
            "    \"label_id\" bigint REFERENCES Label(\"id\")\n" +
            ");";

    static String dropLabelTable = "DROP TABLE Label;";
    static String dropCalendarTable = "DROP TABLE Calendar;";
    static String dropUserTable = "DROP TABLE Appuser;";
    static String dropEventTable = "DROP TABLE Event;";

    UserService userService;
    CalendarService calendarService;
    LabelService labelService;
    EventService eventService;
    Label label;
    User user;
    Calendar calendar;
    Event event;


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
        eventService = new EventService(connection);
        PreparedStatement calendarTable = connection.prepareStatement(createCalendarTable);
        PreparedStatement labelTable = connection.prepareStatement(createLabelTable);
        PreparedStatement userTable = connection.prepareStatement(createUserTable);
        PreparedStatement eventTable = connection.prepareStatement(createEventTable);
        userTable.executeUpdate();
        calendarTable.executeUpdate();
        labelTable.executeUpdate();
        eventTable.executeUpdate();

        user = new User(1L, "username",
                "9830f4fabfaff141d108157700aeee3c91e8d502192d6ca0ca80e52f7f0d6d16");
        calendar = new Calendar(1L, "Sample name", "Sample comment", 1L);
        label = new Label(1L, "Sample name", 169, 169, 169, 1L);
        event = new Event(1L, "Sample name", "Sample comment",
                new DateTime(2019, 1, 1, 10, 0),
                new DateTime(2019, 1, 1, 11, 30), 1L, 1L);
    }

    @AfterEach
    void dropTables() throws SQLException {
        userService = null;
        labelService = null;
        calendarService = null;
        eventService = null;
        user = null;
        label = null;
        calendar = null;
        event = null;
        PreparedStatement dropUserTablePstmt = connection.prepareStatement(dropUserTable);
        PreparedStatement dropLabelTablePstmt = connection.prepareStatement(dropLabelTable);
        PreparedStatement dropCalendarTablePstmt = connection.prepareStatement(dropCalendarTable);
        PreparedStatement dropEventTablePstmt = connection.prepareStatement(dropEventTable);
        dropEventTablePstmt.executeUpdate();
        dropLabelTablePstmt.executeUpdate();
        dropCalendarTablePstmt.executeUpdate();
        dropUserTablePstmt.executeUpdate();
    }

    @Test
    void validAdd() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(calendarService.add(calendar)),
                () -> assertTrue(labelService.add(label)),
                () -> assertTrue(eventService.add(event))
        );
    }

    @Test
    void addReturnsFalseWhenEventIsNull() {
        assertFalse(eventService.add(null));
    }

    @Test
    void addReturnsFalseWhenEventExistsInDatabase() {
        eventService.add(event);

        assertFalse(eventService.add(event));
    }

    @Test
    void validGetByCalendar() {
        userService.add(user);
        calendarService.add(calendar);
        labelService.add(label);
        eventService.add(event);

        List<Event> result = eventService.getByCalendar(event.getCalendar_id());

        assertAll(
                () -> assertEquals(event.getName(), result.get(0).getName()),
                () -> assertEquals(event.getCalendar_id(), result.get(0).getCalendar_id()),
                () -> assertEquals(event.getComment(), result.get(0).getComment()),
                () -> assertEquals(event.getLabel_id(), result.get(0).getLabel_id()),
                () -> assertEquals(event.getStartDate(), result.get(0).getStartDate()),
                () -> assertEquals(event.getEndDate(), result.get(0).getEndDate())
        );
    }

    @Test
    void getByCalendarReturnsEmptyListWhenEventIsNotFound() {
        assertEquals(new ArrayList<Label>(), eventService.getByCalendar(event.getCalendar_id()));
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
        eventService.add(event);

        Event result = eventService.getById(event.getId());

        assertAll(
                () -> assertEquals(event.getName(), result.getName()),
                () -> assertEquals(event.getCalendar_id(), result.getCalendar_id()),
                () -> assertEquals(event.getComment(), result.getComment()),
                () -> assertEquals(event.getLabel_id(), result.getLabel_id()),
                () -> assertEquals(event.getStartDate(), result.getStartDate()),
                () -> assertEquals(event.getEndDate(), result.getEndDate())
        );
    }

    @Test
    void getByIdReturnsNullWhenEventIsNotFound() {
        assertNull(eventService.getById(event.getId()));
    }

    @Test
    void getByIdReturnsNullWhenIdIsNull() {
        assertNull(eventService.getById(null));
    }

    @Test
    void validUpdate() {
        userService.add(user);
        calendarService.add(calendar);
        labelService.add(label);
        eventService.add(event);

        event.setName("New name");
        assertTrue(eventService.update(event));
    }

    @Test
    void updateReturnsFalseWhenEventIsNull() {
        assertFalse(eventService.update(null));
    }

    @Test
    void updateReturnsFalseWhenEventIsNotFound() {
        assertFalse(eventService.update(event));
    }

    @Test
    void validDelete() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(calendarService.add(calendar)),
                () -> assertTrue(labelService.add(label)),
                () -> assertTrue(eventService.add(event)),
                () -> assertEquals(event.getName(), eventService.getById(event.getId()).getName()),
                () -> assertEquals(event.getCalendar_id(), eventService.getById(event.getId()).getCalendar_id()),
                () -> assertEquals(event.getComment(), eventService.getById(event.getId()).getComment()),
                () -> assertEquals(event.getLabel_id(), eventService.getById(event.getId()).getLabel_id()),
                () -> assertEquals(event.getStartDate(), eventService.getById(event.getId()).getStartDate()),
                () -> assertEquals(event.getEndDate(), eventService.getById(event.getId()).getEndDate()),
                () -> assertTrue(eventService.delete(event.getId())),
                () -> assertNull(eventService.getById(event.getId()))
        );
    }

    @Test
    void deleteReturnsFalseWhenEventIsNotFound() {
        assertFalse(eventService.delete(event.getId()));
    }

    @Test
    void deleteReturnsFalseWhenIdIsNull() {
        assertFalse(eventService.delete(null));
    }

}
