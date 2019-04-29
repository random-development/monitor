package net.random_development.monitor.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateParsingServiceImplTest {

    private DateParsingService dateParsingService;

    @Before
    public void setUp() {
        dateParsingService = new DateParsingServiceImpl();
    }

    @Test
    public void toTimestamp() {
        // when
        String iso8601 = dateParsingService.fromTimestamp(123);

        // then
        assertEquals(iso8601, "1970-01-01T00:02:03Z");
    }

    @Test
    public void fromTimestamp() {
        // when
        int timestamp = dateParsingService.toTimestamp("1970-01-01T00:02:03Z");

        // then
        assertEquals(timestamp, 123);
    }

}
