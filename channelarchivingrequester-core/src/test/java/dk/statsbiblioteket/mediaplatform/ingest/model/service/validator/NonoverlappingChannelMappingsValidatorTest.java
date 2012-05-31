/* $Id$
 * $Revision$
 * $Date$
 * $Author$
 *
 *
 */
package dk.statsbiblioteket.mediaplatform.ingest.model.service.validator;

import dk.statsbiblioteket.mediaplatform.ingest.model.PersistenceTestCase;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ServiceException;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingServiceIF;
import junit.framework.TestCase;

import java.io.File;
import java.util.Date;
import java.util.List;

public class NonoverlappingChannelMappingsValidatorTest extends PersistenceTestCase {



    public void testGetFailuresNoFailure() throws ServiceException {
        Date date1 = new Date(10, 1, 0);
        Date date2 = new Date(15, 1, 0);
        Date date3 = new Date(20, 1, 0);
        Date date4 = new Date(25, 1, 0);
        YouSeeChannelMappingServiceIF mappingService = new YouSeeChannelMappingService();
        YouSeeChannelMapping m1 = new YouSeeChannelMapping();
        m1.setSbChannelId("dr1");
        m1.setYouSeeChannelId("DR1");
        m1.setFromDate(date1);
        m1.setToDate(date2);
       YouSeeChannelMapping m2 = new YouSeeChannelMapping();
        m2.setSbChannelId("dr1");
        m2.setYouSeeChannelId("DR1_foobar");
        m2.setFromDate(date2);
        m2.setToDate(date3);
        mappingService.create(m1);
        mappingService.create(m2);
        NonoverlappingChannelMappingsValidator validator = new NonoverlappingChannelMappingsValidator();
        assertTrue("Expect no validation failures", validator.getFailures().isEmpty());
    }

     public void testGetFailuresWithFailure() throws ServiceException {
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
        NonoverlappingChannelMappingsValidator validator = new NonoverlappingChannelMappingsValidator();
        assertTrue("Expect one validation failure not " + validator.getFailures().size(), validator.getFailures().size() == 1);
        System.out.println(validator.getFailures().get(0).getCause());
     }

}
