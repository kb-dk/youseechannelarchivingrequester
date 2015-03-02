package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import org.hibernate.Query;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class YouSeeChannelMappingDAO extends GenericHibernateDAO<YouSeeChannelMapping, Long> implements YouSeeChannelMappingDAOIF {

    /**
     * Constructor for this DAO class.
     */
    public YouSeeChannelMappingDAO(HibernateUtilIF util) {
        super(YouSeeChannelMapping.class, util);
    }

    @Override
    public List<YouSeeChannelMapping> getMappingsFromYouSeeChannelId(String youSeeChannelId, Date date) {
        final Query query = getSession().createQuery("FROM YouSeeChannelMapping WHERE youSeeChannelId = :id AND " +
                "fromDate <= :date AND toDate >= :date");
        return query.setParameter("id", youSeeChannelId).setDate("date", date).list();
    }


    @Override
    public List<YouSeeChannelMapping> getMappingsFromSbChannelId(String sBChannelId, Date date) {
        final Query query = getSession().createQuery("FROM YouSeeChannelMapping WHERE sbChannelId = :id AND " +
                "fromDate <= :date AND toDate >= :date");
        return query.setParameter("id", sBChannelId).setDate("date", date).list();
    }

    @Override
    public List<YouSeeChannelMapping> getAllMappings() {
        return getSession().createQuery("from YouSeeChannelMapping ORDER BY toDate desc").list();
    }

    @Override
    public YouSeeChannelMapping getMappingByID(Long id) {
        Query query = getSession().createQuery("FROM YouSeeChannelMapping WHERE id = :id").setLong("id", id);
        if (query.list().size() == 1)
            return (YouSeeChannelMapping) query.list().get(0);
        else
            return null;
    }

}
