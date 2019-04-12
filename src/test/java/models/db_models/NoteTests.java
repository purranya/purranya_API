package models.db_models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoteTests {
    private Note note;

    @BeforeEach
    void setUp() {
        note = new Note(null, "Note1","Lorem Ipsum and something else here", 1L);
    }

    @Test
    @DisplayName("Checking if note name is valid and returns true")
    void isUsernameValidTest() {
        assertTrue(note.isNameValid());
    }

    @Test
    @DisplayName("Checking if note name is valid (name has polish digraphs) and returns true")
    void isUsernameValid2Test() {
        note.setName("ćśżń");
        assertTrue(note.isNameValid());
    }

    @Test
    @DisplayName("Checking if note name has proper length and returns false because of invalid length (60)")
    void isUsernameValid3Test() {
        note.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaxxxxxxxxxxxxxxxxxxxx");
        assertFalse(note.isNameValid());
    }

    @Test
    @DisplayName("Checking if note name has proper length and returns false because of invalid length (0)")
    void isUsernameValid4Test() {
        note.setName("");
        assertFalse(note.isNameValid());
    }

    @Test
    @DisplayName("Checking if note name is valid and returns false because of invalid characters")
    void isUsernameValid5Test() {
        note.setName("$@!@#$%");
        assertFalse(note.isNameValid());
    }

    @Test
    @DisplayName("Checking if note name is valid and returns false because name is null")
    void isUsernameValid6Test() {
        note.setName(null);
        assertFalse(note.isNameValid());
    }

    @Test
    @DisplayName("Checking if comment is valid and returns true")
    void isCommentValidTest() {
        assertTrue(note.isCommentValid());
    }

    @Test
    @DisplayName("Checking if comment is valid and returns false because comment is null")
    void isCommentValid2Test() {
        note.setComment(null);
        assertFalse(note.isCommentValid());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/lorem_ipsum.csv")
    @DisplayName("Checking if comment is valid and returns false because comment is too long")
    void isCommentValid3Test(String str) {
        note.setComment(str);
        assertFalse(note.isCommentValid());
    }

    @Test
    @DisplayName("Checking if user id is valid and returns true")
    void isUserIdValidTest() {
        assertTrue(note.isUserIdValid());
    }

    @Test
    @DisplayName("Checking if user id is valid and returns false because it is null")
    void isUserIdValid2Test() {
        note.setUser_id(null);
        assertFalse(note.isUserIdValid());
    }

    @Test
    @DisplayName("Checking if user id is valid and returns false because it is not more than 0")
    void isUserIdValid3Test() {
        note.setUser_id(0L);
        assertFalse(note.isUserIdValid());
    }

    @Test
    @DisplayName("Checking if note is valid and returns true")
    void isValidTest() {
        assertTrue(note.isValid());
    }

    @Test
    @DisplayName("Checking if note is valid and returns false")
    void isValid2Test() {
        note.setName("   ");
        assertFalse(note.isValid());
    }





    @AfterEach
    void cleanUp() {
        note = null;
    }

}
