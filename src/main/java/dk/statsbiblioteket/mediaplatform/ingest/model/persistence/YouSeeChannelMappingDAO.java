package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.generic.utils.GenericHibernateDAO;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.ChannelArchivingRequestException;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.YouSeeChannelMapping;
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
    public YouSeeChannelMappingDAO() {
        super(YouSeeChannelMapping.class);
    }


    @Override
    public YouSeeChannelMapping getMappingFromYouSeeChannelId(String youSeeChannelId) throws ChannelArchivingRequestException {
        return getMappingFromYouSeeChannelId(youSeeChannelId, new Date());
    }

    @Override
    public YouSeeChannelMapping getMappingFromYouSeeChannelId(String youSeeChannelId, Date date) throws ChannelArchivingRequestException {
        final Query query = getSession().createQuery("FROM YouSeeChannelMapping WHERE youSeeChannelId = :id AND " +
                "fromDate <= :date AND toDate >= :date");
        final List list = query.setParameter("id", youSeeChannelId).setDate("date", date).list();
        if (list.size()  != 1) {
            throw new ChannelArchivingRequestException("Expected exactly one valid mapping but found '" + list.size()
                    + "' for " + youSeeChannelId + " at " + date);
        }
        return (YouSeeChannelMapping) (list.get(0));
    }

    @Override
    public YouSeeChannelMapping getMappingFromSbChannelId(String sbChannelId) throws ChannelArchivingRequestException {
        return getMappingFromSbChannelId(sbChannelId, new Date());
    }

    @Override
    public YouSeeChannelMapping getMappingFromSbChannelId(String sBChannelId, Date date) throws ChannelArchivingRequestException {
        final Query query = getSession().createQuery("FROM YouSeeChannelMapping WHERE sbChannelId = :id AND " +
                "fromDate <= :date AND toDate >= :date");
        final List list = query.setParameter("id", sBChannelId).setDate("date", date).list();
         if (list.size()  != 1) {
            throw new ChannelArchivingRequestException("Expected exactly one valid mapping but found '" + list.size()
                    + "' for " + sBChannelId + " at " + date);
        }
        return (YouSeeChannelMapping) (list.get(0));
    }



}
