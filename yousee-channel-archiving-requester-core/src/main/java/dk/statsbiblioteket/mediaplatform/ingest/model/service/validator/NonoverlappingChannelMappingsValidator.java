package dk.statsbiblioteket.mediaplatform.ingest.model.service.validator;

import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class NonoverlappingChannelMappingsValidator implements ValidatorIF {
    @Override
    public List<ValidationFailure> getFailures() {
        List<ValidationFailure> failures = new ArrayList<ValidationFailure>();
        Session sess = ChannelArchivingRequesterHibernateUtil.getInitialisedFactory().getSession();
        Query query = sess.createQuery("select map1, map2 from YouSeeChannelMapping map1, YouSeeChannelMapping map2 " +
                "where map1.sbChannelId = map2.sbChannelId " +
                "and map1.fromDate < map2.fromDate " +
                "and map2.fromDate < map1.toDate");
        Iterator results = query.list().iterator();
        while (results.hasNext()) {
            Object[] result = (Object[]) results.next();
            YouSeeChannelMapping map1 = (YouSeeChannelMapping) result[0];
            YouSeeChannelMapping map2 = (YouSeeChannelMapping) result[1];
            ValidationFailure failure = new ValidationFailure();
            failure.setAffectedSBChannel(map1.getSbChannelId());
            failure.setCause(map1.toString() + " overlaps with " + map2.toString());
            failures.add(failure);
        }
       return failures;
    }
}
