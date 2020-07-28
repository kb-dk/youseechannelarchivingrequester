/* $Id$
 * $Revision$
 * $Date$
 * $Author$
 *
 *
 */
package dk.statsbiblioteket.mediaplatform.ingest.model;

import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.HibernateUtilIF;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.NotInitialiasedException;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.YouSeeChannelMappingDAO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.hibernate.Session;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class RollbackTest extends PersistenceTestCase {

    @Test
    public void failingTestDoTest() throws NotInitialiasedException, SQLException {
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.of(10, 1, 1,0,0), ZoneId.of("Europe/Copenhagen"));
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        YouSeeChannelMappingDAO dao = new YouSeeChannelMappingDAO(util);
        Session sess = util.getSession();
        YouSeeChannelMapping mapping = new YouSeeChannelMapping();
        mapping.setSbChannelId("dr1");
        mapping.setYouSeeChannelId("");
        mapping.setFromDate(Date.from(date.toInstant()));
        mapping.setToDate(Date.from(date.toInstant()));
        dao.create(mapping);
        YouSeeChannelMapping mapping2 = new YouSeeChannelMapping();
        mapping2.setSbChannelId("dr2");
        mapping2.setYouSeeChannelId("");
        mapping2.setFromDate(Date.from(date.toInstant()));
        mapping2.setToDate(Date.from(date.toInstant()));
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
