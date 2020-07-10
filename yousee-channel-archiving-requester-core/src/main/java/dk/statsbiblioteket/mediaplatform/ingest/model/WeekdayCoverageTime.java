package dk.statsbiblioteket.mediaplatform.ingest.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility for interpreting Java Date's as times expressed as hours (0-23) and minutes (0-55) in 5 minute intervals.
 */
public class WeekdayCoverageTime {

    int hours;
    int minutes;

    public WeekdayCoverageTime(ZonedDateTime date) {
        hours = date.getHour();
        minutes = 5*((date.getMinute())/5);
    }

    /**
     * Construct a new instance
     * @param hours an integer in the range 0-23
     * @param minutes an integer in the set {0,5,10,..55}
     */
    public WeekdayCoverageTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public ZonedDateTime getDate() {
        return ZonedDateTime.of(LocalDateTime.of(0,0,0,hours,minutes), ZoneId.of("Europe/Copenhagen"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeekdayCoverageTime that = (WeekdayCoverageTime) o;

        if (hours != that.hours) return false;
        if (minutes != that.minutes) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        return result;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }


}
