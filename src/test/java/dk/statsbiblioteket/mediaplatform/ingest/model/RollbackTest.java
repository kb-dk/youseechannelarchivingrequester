/* $Id$
 * $Revision$
 * $Date$
 * $Author$
 *
 *
 */
package dk.statsbiblioteket.mediaplatform.ingest.model;

import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.*;
import junit.framework.TestCase;
import org.hibernate.Session;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RollbackTest extends TestCase {


    public void failingTestDoTest() throws NotInitialiasedException, SQLException {
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        YouSeeChannelMappingDAO dao = new YouSeeChannelMappingDAO(util);
        Session sess = util.getSession();
        YouSeeChannelMapping mapping = new YouSeeChannelMapping();
        mapping.setSbChannelId("dr1");
        mapping.setYouSeeChannelId("");
        mapping.setFromDate(new Date());
        mapping.setToDate(new Date());
        dao.create(mapping);
        YouSeeChannelMapping mapping2 = new YouSeeChannelMapping();
        mapping2.setSbChannelId("dr2");
        mapping2.setYouSeeChannelId("");
        mapping2.setFromDate(new Date());
        mapping2.setToDate(new Date());
        try {
            sess.beginTransaction();
            sess.save(mapping2);
            sess.flush();
            final List<YouSeeChannelMapping> allMappings =
                    (List<YouSeeChannelMapping>) sess.createQuery("from YouSeeChannelMapping ").list();
            if (allMappings.size() > 1) {
                sess.getTransaction().rollback();
            } else {
                fail("Didn't work");
            }
        } finally {
            sess.close();
        }
        assertEquals(1, dao.getAllMappings().size());
    }

}
