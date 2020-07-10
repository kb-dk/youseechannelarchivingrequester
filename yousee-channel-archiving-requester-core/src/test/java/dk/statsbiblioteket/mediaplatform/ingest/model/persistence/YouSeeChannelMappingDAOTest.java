package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.PersistenceTestCase;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 */
public class YouSeeChannelMappingDAOTest extends PersistenceTestCase {
    @Test
    public void testGetMappingFromYouSeeChannelId() throws Exception {
        ZonedDateTime dateFrom = ZonedDateTime.of(LocalDateTime.of(100, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        ZonedDateTime dateTo = ZonedDateTime.of(LocalDateTime.of(1000, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.getInitialisedFactory();
        YouSeeChannelMappingDAO ucDAO = new YouSeeChannelMappingDAO(util);
        YouSeeChannelMapping ucMapping = new YouSeeChannelMapping();
        ucMapping.setSbChannelId("barfoo_sb");
        ucMapping.setYouSeeChannelId("barfoo");
        ucMapping.setDisplayName("Bar Foo");
        ucMapping.setFromDate(dateFrom);
        ucMapping.setToDate(dateTo);
        Long id = ucDAO.create(ucMapping);
        assertNotNull(id, "Expect create to return an id");
        YouSeeChannelMappingService service = new YouSeeChannelMappingService();
        YouSeeChannelMapping ucMapping2 = service.getUniqueMappingFromYouSeeChannelId("barfoo", ZonedDateTime.of(LocalDateTime.of(500,1,1,0,0), ZoneId.of("Europe/Copenhagen")));
        assertEquals("Bar Foo", ucMapping2.getDisplayName());
    }
}
