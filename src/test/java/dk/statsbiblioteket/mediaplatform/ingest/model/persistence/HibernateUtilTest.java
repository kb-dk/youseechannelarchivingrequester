package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import junit.framework.TestCase;

import java.io.File;

/**
 */
public class HibernateUtilTest extends TestCase {

    public void testInitialiseFactory() throws Exception {
        File cfgFile = new File("src/test/java/resources/hibernate.cfg.xml");
        HibernateUtil util = HibernateUtil.initialiseFactory(cfgFile);
    }

    public void testGetSessionFactory() throws Exception {
        File cfgFile = new File("src/test/java/resources/hibernate.cfg.xml");
        HibernateUtil util = HibernateUtil.initialiseFactory(cfgFile);
        util.getSessionFactory();
    }

    public void testGetSession() throws Exception {
        File cfgFile = new File("src/test/java/resources/hibernate.cfg.xml");
        HibernateUtil util = HibernateUtil.initialiseFactory(cfgFile);
        util.getSession();
    }

}
