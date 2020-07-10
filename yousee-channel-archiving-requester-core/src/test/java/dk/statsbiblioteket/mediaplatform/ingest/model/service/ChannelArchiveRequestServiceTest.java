/* $Id$
 * $Revision$
 * $Date$
 * $Author$
 *
 *
 */
package dk.statsbiblioteket.mediaplatform.ingest.model.service;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import dk.statsbiblioteket.mediaplatform.ingest.model.PersistenceTestCase;
import dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.List;

public class ChannelArchiveRequestServiceTest extends PersistenceTestCase {

    /**
     * Create an overlapping channel definition and a request for that channel. The
     * request should be returned, but marked disabled.
     */
    @Test
    public void testGetAllValid() throws ServiceException {
        ZonedDateTime date1 = ZonedDateTime.of(LocalDateTime.of(10, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date2 = ZonedDateTime.of(LocalDateTime.of(15, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date3 = ZonedDateTime.of(LocalDateTime.of(20, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date4 = ZonedDateTime.of(LocalDateTime.of(25, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));

        ZonedDateTime fromDate = ZonedDateTime.of(LocalDateTime.of(22, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime fromTime = ZonedDateTime.of(LocalDateTime.of(0, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime toDate = ZonedDateTime.of(LocalDateTime.of(23, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime toTime = ZonedDateTime.of(LocalDateTime.of(0, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));

        YouSeeChannelMappingServiceIF mappingService = new YouSeeChannelMappingService();
        YouSeeChannelMapping m1 = new YouSeeChannelMapping();
        m1.setSbChannelId("dr1");
        m1.setYouSeeChannelId("DR1");
        m1.setFromDate(date1);
        m1.setToDate(date3);
        YouSeeChannelMapping m2 = new YouSeeChannelMapping();
        m2.setSbChannelId("dr1");
        m2.setYouSeeChannelId("DR1_foobar");
        m2.setFromDate(date2);
        m2.setToDate(date4);
        mappingService.create(m1);
        mappingService.create(m2);
        ChannelArchiveRequestService requestService = new ChannelArchiveRequestService();
        ChannelArchiveRequest request = new ChannelArchiveRequest();
        request.setsBChannelId("dr1");
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setWeekdayCoverage(WeekdayCoverage.DAILY);
        request.setFromTime(fromTime);
        request.setToTime(toTime);
        requestService.insert(request);
        ZonedDateTime fromDate2 = ZonedDateTime.of(LocalDateTime.of(10,1,1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime toDate2 = ZonedDateTime.of(LocalDateTime.of(100,1,1,0,0), ZoneId.of("Europe/Copenhagen"));
        List<ChannelArchiveRequest> requests = requestService.getValidRequests(fromDate2, toDate2);
        assertEquals(1, requests.size(), "Expect to find that we have created one request, not " + requests.size());
        assertFalse(requests.get(0).isEnabled(), "Expect the request to be disabled");
        //Now fix the error
        m1.setToDate(date2);
        mappingService.update(m1);
        requests = requestService.getValidRequests(fromDate2, toDate2);
        assertEquals(1, requests.size(), "Expect to find that we have created one request, not " + requests.size());
        assertTrue(requests.get(0).isEnabled(),"Expect the request to be enabled");
    }

}
