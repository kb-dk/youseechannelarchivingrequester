package dk.statsbiblioteket.mediaplatform.ingest.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a what date and time a channel has to be recorded.
 */
@Entity
@XmlRootElement
public class ChannelArchiveRequest {

    private Long Id;

    private String sBChannelId;

    private ZonedDateTime fromTime;

    private ZonedDateTime toTime;

    private ZonedDateTime fromDate;

    private ZonedDateTime toDate;

    private WeekdayCoverage weekdayCoverage;

    private boolean isEnabled = true;

    private String cause;

    /**
     * True is there are no validation problems which affect this request.
     *
     * @return whether this request is enabled.
     */
    @Transient
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * If this request is disabled, this field gives a human-readable explanation as to why.
     *
     * @return the reason for this request being disabled.
     */
    @Transient
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

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

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(ZonedDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getToDate() {
        return toDate;
    }

    public void setToDate(ZonedDateTime toDate) {
        this.toDate = toDate;
    }

    @Enumerated(EnumType.STRING)
    public WeekdayCoverage getWeekdayCoverage() {
        return weekdayCoverage;
    }

    public void setWeekdayCoverage(WeekdayCoverage weekdayCoverage) {
        this.weekdayCoverage = weekdayCoverage;
    }

    public ZonedDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(ZonedDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public ZonedDateTime getToTime() {
        return toTime;
    }

    public void setToTime(ZonedDateTime toTime) {
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return "ChannelArchiveRequest{"
                + "Id=" + Id
                + ", sBChannelId='" + sBChannelId + '\''
                + ", fromTime=" + fromTime
                + ", toTime=" + toTime
                + ", fromDate=" + fromDate
                + ", toDate=" + toDate
                + ", weekdayCoverage=" + weekdayCoverage
                + ", isEnabled=" + isEnabled
                + ", cause='" + cause + '\''
                + '}';
    }
}
