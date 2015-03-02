package dk.statsbiblioteket.mediaplatform.ingest.model;

import junit.framework.TestCase;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 */
public class WeekdayCoverageTimeTest extends TestCase {

    public void testCtor() {
        Date d1 = new GregorianCalendar(100, 0, 1, 8, 56).getTime();
        Date d2 = new GregorianCalendar(50, 7, 3, 8, 57).getTime();
        Date d3 = new GregorianCalendar(55, 3, 4, 6, 0).getTime();
        WeekdayCoverageTime wkt1 = new WeekdayCoverageTime(d1);
        WeekdayCoverageTime wkt2 = new WeekdayCoverageTime(d2);
        WeekdayCoverageTime wkt3 = new WeekdayCoverageTime(d3);
        assertEquals(wkt1, wkt2);
        assertFalse(wkt2.equals(wkt3));
        assertEquals(55, wkt1.getMinutes());
    }

}
