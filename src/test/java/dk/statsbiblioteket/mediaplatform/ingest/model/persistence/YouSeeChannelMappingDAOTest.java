package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.digitaltv.access.model.ChannelMapping;
import dk.statsbiblioteket.digitaltv.access.model.persistence.ChannelMappingDAO;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.YouSeeChannelMapping;
import junit.framework.TestCase;

import java.io.File;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: csr
 * Date: 5/8/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class YouSeeChannelMappingDAOTest extends TestCase {

    public void testGetMappingFromYouSeeChannelId() throws Exception {
        File cfgFile = new File("src/test/java/resources/hibernate.cfg.xml");
        HibernateUtil util = HibernateUtil.initialiseFactory(cfgFile);
        YouSeeChannelMappingDAO ucDAO = new YouSeeChannelMappingDAO();
        YouSeeChannelMapping ucMapping = new YouSeeChannelMapping();
        ucMapping.setSbChannelId("barfoo_sb");
        ucMapping.setYouSeeChannelId("barfoo");
        ucMapping.setDisplayName("Bar Foo");
        ucMapping.setFromDate(new Date(100, 0, 1));
        ucMapping.setToDate(new Date(1000, 0, 1));
        Long id =ucDAO.create(ucMapping);
        assertNotNull("Expect create to return an id", id);
        YouSeeChannelMapping ucMapping2 = ucDAO.getMappingFromYouSeeChannelId("barfoo");
        assertEquals("Bar Foo", ucMapping2.getDisplayName());
    }
}
