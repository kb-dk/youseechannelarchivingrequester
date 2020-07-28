package dk.statsbiblioteket.mediaplatform.ingest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * This class represents a mapping between a channel from YouSee and a channel as defined by us.
 */
@Entity
public class YouSeeChannelMapping {

    private Long Id;

    private String sbChannelId;

    private String youSeeChannelId;

    private String displayName;

    private Date fromDate;

    private Date toDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the Statsbiblioteket Channel Name for this Channel
     * @return
     */
    public String getSbChannelId() {
        return sbChannelId;
    }

    public void setSbChannelId(String sbChannelId) {
        this.sbChannelId = sbChannelId;
    }

    /**
     * Get the YouSee ChannelID corresponding to this channel.
     * @return the YouSee ChannelId.
     */
    public String getYouSeeChannelId() {
        return youSeeChannelId;
    }

    public void setYouSeeChannelId(String youSeeChannelId) {
        this.youSeeChannelId = youSeeChannelId;
    }

    @Column(nullable = false)
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Column(nullable = false)
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "YouSeeChannelMapping{" +
                "sbChannelId='" + sbChannelId + '\'' +
                ", youSeeChannelId='" + youSeeChannelId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
