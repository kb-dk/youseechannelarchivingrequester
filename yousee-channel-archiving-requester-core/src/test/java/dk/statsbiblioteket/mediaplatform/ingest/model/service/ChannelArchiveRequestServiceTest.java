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
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingServiceIF;

import java.util.Date;
import java.util.List;

public class ChannelArchiveRequestServiceTest extends PersistenceTestCase {

    /**
     * Create an overlapping channel definition and a request for that channel. The
     * request should be returned, but marked disabled.
     */
    public void testGetAllValid() throws ServiceException {
        Date date1 = new Date(10, 1, 0);
        Date date2 = new Date(15, 1, 0);
        Date date3 = new Date(20, 1, 0);
        Date date4 = new Date(25, 1, 0);
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
        request.setFromDate(new Date(22,1,0));
        request.setToDate(new Date(23,1,0));
        request.setWeekdayCoverage(WeekdayCoverage.DAILY);
        request.setFromTime(new Date(0,1,0,0,0));
        request.setToTime(new Date(0,1,1,0,0));
        requestService.insert(request);
        List<ChannelArchiveRequest> requests = requestService.getValidRequests(new Date(10, 1, 0), new Date(100, 1, 0)) ;
        assertEquals("Expect to find that we have created one request, not " + requests.size() , 1, requests.size());
        assertFalse("Expect the request to be disabled", requests.get(0).isEnabled());
        //Now fix the error
        m1.setToDate(date2);
        mappingService.update(m1);
        requests = requestService.getValidRequests(new Date(10, 1, 0), new Date(100, 1, 0)) ;
        assertEquals("Expect to find that we have created one request, not " + requests.size() , 1, requests.size());
        assertTrue("Expect the request to be enabled", requests.get(0).isEnabled());
    }

}
