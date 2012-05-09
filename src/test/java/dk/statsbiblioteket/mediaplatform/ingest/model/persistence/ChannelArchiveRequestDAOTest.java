package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.Schedule;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.ChannelArchiveRequest;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: csr
 * Date: 5/8/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelArchiveRequestDAOTest extends TestCase {

    public void testInsert() {
        ChannelArchiveRequest schedule = new ChannelArchiveRequest();
        schedule.setSchedule(Schedule.MONDAY_TO_FRIDAY);
        schedule.setsBChannelId("dr1");
        ChannelArchiveRequestDAO dao = new ChannelArchiveRequestDAO();
        dao.create(schedule);
    }

}
