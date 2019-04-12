package models.db_models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(null, "Admin","60a5d3e4100fe8afa5ee0103739a45711d50d7f3ba7280d8a95b51f5d04aa4b8");
    }

    @Test
    @DisplayName("Checking if username is valid and returns true")
    void isUsernameValidTest() {
        assertTrue(user.isUsernameValid());
    }

    @Test
    @DisplayName("Checking if username is valid (name has polish digraphs) and returns true")
    void isUsernameValid2Test() {
        user.setUsername("ćśżń");
        assertTrue(user.isUsernameValid());
    }

    @Test
    @DisplayName("Checking if username has proper length and returns false because of invalid length (40)")
    void isUsernameValid3Test() {
        user.setUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertFalse(user.isUsernameValid());
    }

    @Test
    @DisplayName("Checking if username has proper length and returns false because of invalid length (0)")
    void isUsernameValid4Test() {
        user.setUsername("");
        assertFalse(user.isUsernameValid());
    }

    @Test
    @DisplayName("Checking if username is valid and returns false because of invalid characters")
    void isUsernameValid5Test() {
        user.setUsername("$@!@#$%");
        assertFalse(user.isUsernameValid());
    }

    @Test
    @DisplayName("Checking if username is valid and returns false because name is null")
    void isUsernameValid6Test() {
        user.setUsername(null);
        assertFalse(user.isUsernameValid());
    }

    @Test
    @DisplayName("Checking if password hash is valid and returns true")
    void isPasswordValidTest() {
        assertTrue(user.isPasswordValid());
    }

    @Test
    @DisplayName("Checking if password hash is valid and returns false because it is null")
    void isPasswordValid2Test() {
        user.setPassword_hash(null);
        assertFalse(user.isPasswordValid());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/valid_users.csv")
    @DisplayName("Checking if password hash is valid and returns false because it is too long")
    void isPasswordValid3Test(String str) {
        user.setPassword_hash(str);
        assertFalse(user.isPasswordValid());
    }

    @Test
    @DisplayName("Checking if user is valid and returns true")
    void isValidTest() {
        assertTrue(user.isValid());
    }

    @Test
    @DisplayName("Checking if user is valid and returns false")
    void isValid2Test() {
        user.setUsername("   ");
        assertFalse(user.isValid());
    }





    @AfterEach
    void cleanUp() {
        user = null;
    }

}
