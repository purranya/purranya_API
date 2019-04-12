package models.db_models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalendarTests {
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        calendar = new Calendar();
    }

    @AfterEach
    void cleanUp() {
        calendar = null;
    }
}