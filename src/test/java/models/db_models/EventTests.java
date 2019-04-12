package models.db_models;

import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTests {
    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(null, "Note1","Lorem Ipsum and something else here", new DateTime("12-06-2019 13:40"),new DateTime("14-06-2019 13:40"), 1L,1L);
    }

    @Test
    @DisplayName("Checking if event name is valid and returns true")
    void isNameValidTest() {
        assertTrue(event.isNameValid());
    }

    @Test
    @DisplayName("Checking if event name is valid (name has polish digraphs) and returns true")
    void isNameValid2Test() {
        event.setName("ćśżń");
        assertTrue(event.isNameValid());
    }

    @Test
    @DisplayName("Checking if event name has proper length and returns false because of invalid length (60)")
    void isNameValid3Test() {
        event.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaxxxxxxxxxxxxxxxxxxxx");
        assertFalse(event.isNameValid());
    }

    @Test
    @DisplayName("Checking if event name has proper length and returns false because of invalid length (0)")
    void isNameValid4Test() {
        event.setName("");
        assertFalse(event.isNameValid());
    }

    @Test
    @DisplayName("Checking if event name is valid and returns false because of invalid characters")
    void isNameValid5Test() {
        event.setName("$@!@#$%");
        assertFalse(event.isNameValid());
    }

    @Test
    @DisplayName("Checking if event name is valid and returns false because name is null")
    void isNameValid6Test() {
        event.setName(null);
        assertFalse(event.isNameValid());
    }

    @Test
    @DisplayName("Checking if comment is valid and returns true")
    void isCommentValidTest() {
        assertTrue(event.isCommentValid());
    }

    @Test
    @DisplayName("Checking if comment is valid and returns false because comment is null")
    void isCommentValid2Test() {
        event.setComment(null);
        assertFalse(event.isCommentValid());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/lorem_ipsum.csv")
    @DisplayName("Checking if comment is valid and returns false because comment is too long")
    void isCommentValid3Test(String str) {
        event.setComment(str);
        assertFalse(event.isCommentValid());
    }

    @Test
    @DisplayName("Checking if label id is valid and returns true")
    void isLabelIdValidTest() {
        assertTrue(event.isLabelIdValid());
    }

    @Test
    @DisplayName("Checking if label id is valid and returns false because it is null")
    void isLabelIdValid2Test() {
        event.setLabel_id(null);
        assertFalse(event.isLabelIdValid());
    }

    @Test
    @DisplayName("Checking if label id is valid and returns false because it is not more than 0")
    void isLabelIdValid3Test() {
        event.setLabel_id(0L);
        assertFalse(event.isLabelIdValid());
    }

    @Test
    @DisplayName("Checking if calendar id is valid and returns true")
    void isCalendarIdValidTest() {
        assertTrue(event.isCalendarIdValid());
    }

    @Test
    @DisplayName("Checking if calendar id is valid and returns false because it is null")
    void isCalendarIdValid2Test() {
        event.setCalendar_id(null);
        assertFalse(event.isCalendarIdValid());
    }

    @Test
    @DisplayName("Checking if calendar id is valid and returns false because it is not more than 0")
    void isCalendarIdValid3Test() {
        event.setCalendar_id(0L);
        assertFalse(event.isCalendarIdValid());
    }

    @Test
    @DisplayName("Checking if event is valid and returns true")
    void isValidTest() {
        assertTrue(event.isValid());
    }

    @Test
    @DisplayName("Checking if event is valid and returns false")
    void isValid2Test() {
        event.setName("   ");
        assertFalse(event.isValid());
    }


    @AfterEach
    void cleanUp() {
        event = null;
    }


}
