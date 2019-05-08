package services;

import app.DBInfoTest;
import models.db_models.Note;
import models.db_models.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class NoteServiceTests {

    static Connection connection;

    static String userTable = "CREATE TABLE Appuser (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"username\" varchar(50) CHECK(length(\"username\")>1 and length(\"username\")<51) NOT NULL UNIQUE,\n" +
            "    \"password\" varchar(70) CHECK(length(\"password\")>1 and length(\"password\")<71) NOT NULL  \n" +
            ");";
    static String noteTable = "CREATE TABLE Note (\n" +
            "    \"id\" bigserial PRIMARY KEY,\n" +
            "    \"name\" varchar(50) CHECK(length(\"name\")>1 and length(\"name\")<51) NOT NULL,\n" +
            "    \"comment\" varchar(2048) CHECK(length(\"comment\")>0 and length(\"comment\")<2049),\n" +
            "    \"appuser_id\" bigint NOT NULL REFERENCES Appuser(\"id\")\n" +
            ");";
    static String dropUserTable = "DROP TABLE Appuser;";
    static String dropNoteTable = "DROP TABLE Note;";

    NoteService noteService;
    UserService userService;
    User user;
    Note note;


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
        noteService = new NoteService(connection);
        userService = new UserService(connection);
        PreparedStatement userTablePstmt = connection.prepareStatement(userTable);
        PreparedStatement noteTablePstmt = connection.prepareStatement(noteTable);
        userTablePstmt.executeUpdate();
        noteTablePstmt.executeUpdate();

        user = new User(1L, "username",
                "9830f4fabfaff141d108157700aeee3c91e8d502192d6ca0ca80e52f7f0d6d16");
        note = new Note(1L, "Sample note", "sample comment", 1L);
    }

    @AfterEach
    void dropTables() throws SQLException {
        noteService = null;
        userService = null;
        PreparedStatement dropNotePstmt = connection.prepareStatement(dropNoteTable);
        PreparedStatement dropUserPstmt = connection.prepareStatement(dropUserTable);
        dropNotePstmt.executeUpdate();
        dropUserPstmt.executeUpdate();
    }

    @Test
    void validAdd() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(noteService.add(note))
        );
    }

    @Test
    void addReturnsFalseWhenNoteIsNull() {
        assertFalse(noteService.add(null));
    }

    @Test
    void addReturnsFalseWhenNoteExistsInDatabase() {
        noteService.add(note);

        assertFalse(noteService.add(note));
    }

    @Test
    void validGetByUser() {
        userService.add(user);
        noteService.add(note);

        List<Note> result = noteService.getByUser(user.getId());

        assertAll(
                () -> assertThat(result.size()).isGreaterThan(0),
                () -> assertEquals(note.getUser_id(), result.get(0).getUser_id()),
                () -> assertEquals(note.getName(), result.get(0).getName()),
                () -> assertEquals(note.getComment(), result.get(0).getComment())
        );
    }

    @Test
    void getByUserReturnsEmptyListWhenUserIsNotFound() {
        assertEquals(new ArrayList<>(), noteService.getByUser(user.getId()));
    }

    @Test
    void getByUserReturnsNullWhenIdIsNull() {
        assertNull(noteService.getByUser(null));
    }

    @Test
    void validGetById() {
        userService.add(user);
        noteService.add(note);

        Note result = noteService.getById(user.getId());

        assertAll(
                () -> assertEquals(note.getUser_id(), result.getUser_id()),
                () -> assertEquals(note.getName(), result.getName()),
                () -> assertEquals(note.getComment(), result.getComment())
        );
    }

    @Test
    void getByIdReturnsNullWhenNoteIsNotFound() {
        assertNull(noteService.getById(note.getId()));
    }

    @Test
    void getByIdReturnsNullWhenIdIsNull() {
        assertNull(noteService.getById(null));
    }

    @Test
    void validUpdate() {
        userService.add(user);
        noteService.add(note);

        note.setName("New name");
        assertTrue(noteService.update(note));
    }

    @Test
    void updateReturnsFalseWhenNoteIsNull() {
        assertFalse(noteService.update(null));
    }

    @Test
    void updateReturnsFalseWhenNoteIsNotFound() {
        assertFalse(noteService.update(note));
    }

    @Test
    void validDelete() {
        assertAll(
                () -> assertTrue(userService.add(user)),
                () -> assertTrue(noteService.add(note)),
                () -> assertEquals(note.getUser_id(), noteService.getById(note.getId()).getUser_id()),
                () -> assertEquals(note.getName(), noteService.getById(note.getId()).getName()),
                () -> assertTrue(noteService.delete(note.getId())),
                () -> assertNull(noteService.getById(note.getId()))
        );
    }

    @Test
    void deleteReturnsFalseWhenNoteIsNotFound() {
        assertFalse(noteService.delete(note.getId()));
    }

    @Test
    void deleteReturnsFalseWhenIdIsNull() {
        assertFalse(noteService.delete(null));
    }
}
