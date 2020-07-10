package dk.statsbiblioteket.mediaplatform.ingest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 */
public class WeekdayCoverageTimeTest {

    @Test
    public void testCtor() {
        ZonedDateTime d1 = ZonedDateTime.of(LocalDateTime.of(100,1, 1, 8, 56), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime d2 = ZonedDateTime.of(LocalDateTime.of(50,7, 3, 8, 57), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime d3 = ZonedDateTime.of(LocalDateTime.of(55,3, 4, 6, 0), ZoneId.of("Europe/Copenhagen"));
        WeekdayCoverageTime wkt1 = new WeekdayCoverageTime(d1);
        WeekdayCoverageTime wkt2 = new WeekdayCoverageTime(d2);
        WeekdayCoverageTime wkt3 = new WeekdayCoverageTime(d3);
        assertEquals(wkt1, wkt2);
        assertFalse(wkt2.equals(wkt3));
        assertEquals(55, wkt1.getMinutes());
    }

}
