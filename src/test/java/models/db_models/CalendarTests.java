package models.db_models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalendarTests {
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        calendar = new Calendar(1L, "ExampleNameOfCalendar",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua.");
    }

    @Test
    @DisplayName("Checking if name of calendar is valid and returns true")
    void isNameValidTest() {
        assertTrue(calendar.isNameValid());
    }

    @Test
    @DisplayName("Checking if name of calendar is valid (name has polish digraphs) and returns true")
    void isNameValid2Test() {
        calendar.setName("ćśżń");
        assertTrue(calendar.isNameValid());
    }

    @Test
    @DisplayName("Checking if name of calendar has proper length and returns false because of invalid length (60)")
    void isNameValid3Test() {
        calendar.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertFalse(calendar.isNameValid());
    }

    @Test
    @DisplayName("Checking if name of calendar has proper length and returns false because of invalid length (0)")
    void isNameValid4Test() {
        calendar.setName("");
        assertFalse(calendar.isNameValid());
    }

    @Test
    @DisplayName("Checking if name of calendar is valid and returns false because of invalid characters")
    void isNameValid5Test() {
        calendar.setName("$@!@#$%");
        assertFalse(calendar.isNameValid());
    }

    @Test
    @DisplayName("Checking if name of calendar is valid and returns false because name is null")
    void isNameValid6Test() {
        calendar.setName(null);
        assertFalse(calendar.isNameValid());
    }

    @Test
    @DisplayName("Checking if comment of calendar is valid and returns true")
    void isCommentValidTest() {
        assertTrue(calendar.isCommentValid());
    }

    @Test
    @DisplayName("Checking if comment of calendar is valid and returns false because comment is null")
    void isCommentValid2Test() {
        calendar.setComment(null);
        assertFalse(calendar.isCommentValid());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/lorem_ipsum.csv")
    @DisplayName("Checking if comment of calendar is valid and returns false because comment is too long")
    void isCommentValid3Test(String str) {
        calendar.setComment(str);
        assertFalse(calendar.isCommentValid());
    }

    @Test
    @DisplayName("Checking if calendar is valid and returns true")
    void isValidTest() {
        assertTrue(calendar.isValid());
    }

    @Test
    @DisplayName("Checking if calendar is valid and returns false")
    void isValid2Test() {
        calendar.setName("   ");
        assertFalse(calendar.isValid());
    }

    @AfterEach
    void cleanUp() {
        calendar = null;
    }
}