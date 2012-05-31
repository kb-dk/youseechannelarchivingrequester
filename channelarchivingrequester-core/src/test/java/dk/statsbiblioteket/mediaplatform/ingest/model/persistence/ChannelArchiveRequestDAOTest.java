package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage;
import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import junit.framework.TestCase;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 */
public class ChannelArchiveRequestDAOTest extends TestCase {

    private static ChannelArchiveRequestDAO dao;

    public void setUp() throws NotInitialiasedException {
         File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
         HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
         dao = new ChannelArchiveRequestDAO(ChannelArchivingRequesterHibernateUtil.getInitialisedFactory());
    }

    public void testInsert() throws NotInitialiasedException {
        ChannelArchiveRequest schedule = new ChannelArchiveRequest();
        schedule.setWeekdayCoverage(WeekdayCoverage.MONDAY_TO_FRIDAY);
        schedule.setsBChannelId("dr1");
        dao.create(schedule);
    }


    /**
     * Create requests with ranges:
     * d1 d2             d3  d4           d5  d6               d7       d8
     * |----------------------------------------------------------------|
     *     |-------------|   |-------------|  |----------------|
     *
     * And queries like:
     *   |-------------------------------------------------------|
     *           |------------------|
     *         |----|
     *                                              |------------------------|
     *   d9   d10
     *          d11 d12             d13            d14          d15          d16
     * These should return 4, 3, 2, 2 requests, respectively.
     */
    public void testGetValidRequests() {
        Date d1 = new Date(100, 1, 1);
        Date d2 = new Date(100, 3, 1);
        Date d3 = new Date(100, 7, 1);
        Date d4 = new Date(100, 11, 1);
        Date d5 = new Date(101, 2, 1);
        Date d6 = new Date(101, 4, 1);
        Date d7 = new Date(102, 1, 1);
        Date d8 = new Date(102, 10, 1);
        ChannelArchiveRequest r1 = new ChannelArchiveRequest();
        r1.setFromDate(d1);
        r1.setToDate(d8);
        r1.setsBChannelId("r1");
        ChannelArchiveRequest r2 = new ChannelArchiveRequest();
        r2.setFromDate(d2);
        r2.setToDate(d3);
        r2.setsBChannelId("r2");
        ChannelArchiveRequest r3 = new ChannelArchiveRequest();
        r3.setFromDate(d4);
        r3.setToDate(d5);
        r3.setsBChannelId("r3");
        ChannelArchiveRequest r4 = new ChannelArchiveRequest();
        r4.setFromDate(d6);
        r4.setToDate(d7);
        r4.setsBChannelId("r4");
        ChannelArchiveRequestDAO carDAO = dao;
        carDAO.create(r1); carDAO.create(r2); carDAO.create(r3); carDAO.create(r4);
        //
        Date d9 = new Date(100, 2, 1);
        Date d15 = new Date(102, 5,1);
        assertEquals(4,carDAO.getValidRequests(d9, d15).size());
        Date d10 = new Date(100, 4, 1);
        Date d12 = new Date(100, 5, 1);
        final List<ChannelArchiveRequest> validRequests = carDAO.getValidRequests(d10, d12);
        assertEquals(2, validRequests.size());
        Date d11 = new Date(100, 5, 1);
        Date d13 = new Date(101, 1, 1);
        assertEquals(3,carDAO.getValidRequests(d11, d13).size());
        Date d14 = new Date(101, 8, 1);
        Date d16 = new Date(105, 1, 1);
        assertEquals(2,carDAO.getValidRequests(d14, d16).size());
    }

}
