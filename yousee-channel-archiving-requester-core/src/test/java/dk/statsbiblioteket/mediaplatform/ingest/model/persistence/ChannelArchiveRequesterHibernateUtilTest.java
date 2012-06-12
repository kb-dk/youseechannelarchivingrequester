package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import junit.framework.TestCase;

import java.io.File;

/**
 */
public class ChannelArchiveRequesterHibernateUtilTest extends TestCase {

    public void testInitialiseFactory() throws Exception {
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
    }

    public void testGetSessionFactory() throws Exception {
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        util.getSessionFactory();
    }

    public void testGetSession() throws Exception {
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        util.getSession();
    }

}
