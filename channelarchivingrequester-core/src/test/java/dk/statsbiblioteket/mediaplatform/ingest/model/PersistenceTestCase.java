/* $Id$
 * $Revision$
 * $Date$
 * $Author$
 *
 *
 */
package dk.statsbiblioteket.mediaplatform.ingest.model;

import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ChannelArchiveRequestService;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ServiceException;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import junit.framework.TestCase;

import java.io.File;
import java.util.List;

public class PersistenceTestCase extends TestCase {

     public void setUp() throws ServiceException {
        File cfgFile = new File("src/test/resources/hibernate.cfg.xml");
        ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        YouSeeChannelMappingService service = new YouSeeChannelMappingService();
        List<YouSeeChannelMapping> mappings = service.getAllMappings();
        for (YouSeeChannelMapping mapping: mappings) {
            service.delete(mapping);
        }
         ChannelArchiveRequestService service2 = new ChannelArchiveRequestService();
         for (ChannelArchiveRequest request: service2.getAllRequests()) {
             service2.delete(request);
         }
    }

    public void tearDown() {

    }

}
