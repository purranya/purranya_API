package models.db_models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LabelTests {
    private User user;
    private Calendar calendar;
    private Label label;

    @BeforeEach
    void setUp() {
        user = new User(1L, "username",
                "65d8caf480656c763f8202ce8ff7e41846cac819038e9b5e8f568bf3a7a831c5");
        calendar = new Calendar(1L, "Calendar", "Sample comment", 1L);
        label = new Label(1L, "Label", 0, 150, 255, 1L);
    }

    @Test
    @DisplayName("Checking if name is valid and returns true")
    void isNameValidTest() {
        assertTrue(label.isNameValid());
    }

    @Test
    @DisplayName("Checking if name is valid and returns false because name is null")
    void isNameValid2Test() {
        label.setName(null);
        assertFalse(label.isNameValid());
    }

    @Test
    @DisplayName("Checking if name is valid and returns false because length is invalid (0)")
    void isNameValid3Test() {
        label.setName("");
        assertFalse(label.isNameValid());
    }

    @Test
    @DisplayName("Checking if name is valid and returns false because length is invalid (60)")
    void isNameValid4Test() {
        label.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertFalse(label.isNameValid());
    }

    @Test
    @DisplayName("Checking if name is valid and returns false because name validation is false")
    void isNameValid5Test() {
        label.setName("#####");
        assertFalse(label.isNameValid());
    }

    @Test
    @DisplayName("Checking R value of color and returns true")
    void isColorRValidTest() {
        assertTrue(label.isColorRValid());
    }

    @Test
    @DisplayName("Checking R value of color and returns false because value is negative")
    void isColorRValid2Test() {
        label.setColor_r(-1);
        assertFalse(label.isColorRValid());
    }

    @Test
    @DisplayName("Checking R value of color and returns false because value is higher than 255")
    void isColorRValid3Test() {
        label.setColor_r(256);
        assertFalse(label.isColorRValid());
    }

    @Test
    @DisplayName("Checking G value of color and returns true")
    void isColorGValidTest() {
        assertTrue(label.isColorGValid());
    }

    @Test
    @DisplayName("Checking G value of color and returns false because value is negative")
    void isColorGValid2Test() {
        label.setColor_g(-1);
        assertFalse(label.isColorGValid());
    }

    @Test
    @DisplayName("Checking R value of color and returns false because value is higher than 255")
    void isColorGValid3Test() {
        label.setColor_g(256);
        assertFalse(label.isColorGValid());
    }

    @Test
    @DisplayName("Checking B value of color and returns true")
    void isColorBValidTest() {
        assertTrue(label.isColorBValid());
    }

    @Test
    @DisplayName("Checking B value of color and returns false because value is negative")
    void isColorBValid2Test() {
        label.setColor_b(-1);
        assertFalse(label.isColorBValid());
    }

    @Test
    @DisplayName("Checking B value of color and returns false because value is higher than 255")
    void isColorBValid3Test() {
        label.setColor_b(256);
        assertFalse(label.isColorBValid());
    }

    @Test
    @DisplayName("Checking Calendar ID and returns true")
    void isCalendarIdValidTest() {
        assertTrue(label.isCalendarIdValid());
    }

    @Test
    @DisplayName("Checking Calendar ID and returns false because ID is negative")
    void isCalendarIdValid2Test() {
        label.setCalendar_id(-1L);
        assertFalse(label.isCalendarIdValid());
    }

    @AfterEach
    void cleanUp() {
        user = null;
        calendar = null;
        label = null;
    }
}
