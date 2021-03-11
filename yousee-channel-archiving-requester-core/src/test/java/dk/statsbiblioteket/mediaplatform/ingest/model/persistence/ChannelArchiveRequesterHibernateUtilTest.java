package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.PersistenceTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 */
public class ChannelArchiveRequesterHibernateUtilTest extends PersistenceTestCase {

     @Test
    public void testGetSessionFactory() throws Exception {
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        util.getSessionFactory();
    }

    @Test
    public void testGetSession() throws Exception {
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        util.getSession();
    }

}
