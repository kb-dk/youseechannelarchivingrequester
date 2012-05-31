package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.PersistenceTestCase;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import junit.framework.TestCase;

import java.io.File;
import java.util.Date;

/**
 *
 */
public class YouSeeChannelMappingDAOTest extends PersistenceTestCase {

    public void testGetMappingFromYouSeeChannelId() throws Exception {
        //File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.getInitialisedFactory();
        YouSeeChannelMappingDAO ucDAO = new YouSeeChannelMappingDAO(util);
        YouSeeChannelMapping ucMapping = new YouSeeChannelMapping();
        ucMapping.setSbChannelId("barfoo_sb");
        ucMapping.setYouSeeChannelId("barfoo");
        ucMapping.setDisplayName("Bar Foo");
        ucMapping.setFromDate(new Date(100, 0, 1));
        ucMapping.setToDate(new Date(1000, 0, 1));
        Long id = ucDAO.create(ucMapping);
        assertNotNull("Expect create to return an id", id);
        YouSeeChannelMappingService service = new YouSeeChannelMappingService();
        YouSeeChannelMapping ucMapping2 = service.getUniqueMappingFromYouSeeChannelId("barfoo", new Date());
        assertEquals("Bar Foo", ucMapping2.getDisplayName());
    }
}
