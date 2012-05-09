package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.generic.utils.GenericHibernateDAO;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.YouSeeChannelMapping;
import org.hibernate.Query;

import java.util.Date;

/**
 *
 */
public class YouSeeChannelMappingDAO extends GenericHibernateDAO<YouSeeChannelMapping, Long> {

    /**
     * Constructor for this DAO class.
     */
    public YouSeeChannelMappingDAO() {
        super(YouSeeChannelMapping.class);
    }


    public YouSeeChannelMapping getMappingFromYouSeeChannelId(String youSeeChannelId) {
        return getMappingFromYouSeeChannelId(youSeeChannelId, new Date());
    }

    public YouSeeChannelMapping getMappingFromYouSeeChannelId(String youSeeChannelId, Date date) {
        final Query query = getSession().createQuery("FROM YouSeeChannelMapping WHERE youSeeChannelId = :id AND " +
                "fromDate <= :date AND toDate >= :date");
        return (YouSeeChannelMapping) (query.setParameter("id", youSeeChannelId).setDate("date", date).list().get(0));
    }

    public YouSeeChannelMapping getMappingFromSbChannelId(String sbChannelId) {
        return getMappingFromSbChannelId(sbChannelId, new Date());
    }

    public YouSeeChannelMapping getMappingFromSbChannelId(String sBChannelId, Date date) {
        final Query query = getSession().createQuery("FROM YouSeeChannelMapping WHERE sbChannelId = :id AND " +
                "fromDate <= :date AND toDate >= :date");
        return (YouSeeChannelMapping) (query.setParameter("id", sBChannelId).setDate("date", date).list().get(0));
    }



}
