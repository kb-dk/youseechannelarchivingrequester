package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.PersistenceTestCase;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 */
public class YouSeeChannelMappingDAOTest extends PersistenceTestCase {
    @Test
    public void testGetMappingFromYouSeeChannelId() throws Exception {
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.getInitialisedFactory();
        YouSeeChannelMappingDAO ucDAO = new YouSeeChannelMappingDAO(util);
        YouSeeChannelMapping ucMapping = new YouSeeChannelMapping();
        ucMapping.setSbChannelId("barfoo_sb");
        ucMapping.setYouSeeChannelId("barfoo");
        ucMapping.setDisplayName("Bar Foo");
        ucMapping.setFromDate(new Date(100, 0, 1));
        ucMapping.setToDate(new Date(1000, 0, 1));
        Long id = ucDAO.create(ucMapping);
        assertNotNull(id, "Expect create to return an id");
        YouSeeChannelMappingService service = new YouSeeChannelMappingService();
        YouSeeChannelMapping ucMapping2 = service.getUniqueMappingFromYouSeeChannelId("barfoo", new Date());
        assertEquals("Bar Foo", ucMapping2.getDisplayName());
    }
}
