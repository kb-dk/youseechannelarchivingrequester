package dk.statsbiblioteket.mediaplatform.ingest.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.util.Date;

/**
 *
 */
@Entity
public class ChannelArchiveRequest {

    private Long Id;

    private String sBChannelId;

    private Date fromTime;

    private Date toTime;

    private Date fromDate;

    private Date toDate;

    private WeekdayCoverage weekdayCoverage;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getsBChannelId() {
        return sBChannelId;
    }

    public void setsBChannelId(String sBChannelId) {
        this.sBChannelId = sBChannelId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Enumerated(EnumType.STRING)
    public WeekdayCoverage getWeekdayCoverage() {
        return weekdayCoverage;
    }

    public void setWeekdayCoverage(WeekdayCoverage weekdayCoverage) {
        this.weekdayCoverage = weekdayCoverage;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }
}
