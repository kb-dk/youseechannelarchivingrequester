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

import dk.statsbiblioteket.mediaplatform.ingest.model.service.ServiceException;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingServiceIF;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class NonoverlappingChannelMappingsValidatorTest extends PersistenceTestCase {


    @Test
    public void testGetFailuresNoFailure() throws ServiceException {

        ZonedDateTime date1 = ZonedDateTime.of(LocalDateTime.of(10, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date2 = ZonedDateTime.of(LocalDateTime.of(15, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date3 = ZonedDateTime.of(LocalDateTime.of(20, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date4 = ZonedDateTime.of(LocalDateTime.of(25, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
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
        assertTrue(validator.getFailures().isEmpty(), "Expect no validation failures");
    }

     @Test
     public void testGetFailuresWithFailure() throws ServiceException {
        ZonedDateTime date1 = ZonedDateTime.of(LocalDateTime.of(10, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date2 = ZonedDateTime.of(LocalDateTime.of(15, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date3 = ZonedDateTime.of(LocalDateTime.of(20, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime date4 = ZonedDateTime.of(LocalDateTime.of(25, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
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
        assertTrue(validator.getFailures().size() == 1, "Expect one validation failure not " + validator.getFailures().size());
        System.out.println(validator.getFailures().get(0).getCause());
    }


}
