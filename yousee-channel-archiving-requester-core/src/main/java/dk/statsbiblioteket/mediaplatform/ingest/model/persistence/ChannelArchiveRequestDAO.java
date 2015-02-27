package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;

import org.hibernate.Query;

import java.util.Date;
import java.util.List;

/**
 */
public class ChannelArchiveRequestDAO extends GenericHibernateDAO<ChannelArchiveRequest, Long> implements ChannelArchiveRequestDAOIF {

    public ChannelArchiveRequestDAO(HibernateUtilIF util) {
        super(ChannelArchiveRequest.class, util);
    }

    @Override
    public List<ChannelArchiveRequest> getValidRequests(Date queryFromDate, Date queryToDate) {
        Query query = getSession().createQuery("FROM ChannelArchiveRequest WHERE " +
                        " (fromDate >= :queryFromDate AND fromDate <= :queryToDate) " +
                        " OR (toDate >= :queryFromDate AND toDate <= :queryToDate) " +
                        " OR (fromDate <= :queryFromDate AND toDate >= :queryToDate) "
        ).setDate("queryFromDate", queryFromDate).setDate("queryToDate", queryToDate);
        return (List<ChannelArchiveRequest>) query.list();
    }

    @Override
    public List<ChannelArchiveRequest> getAllRequests() {
        Query query = getSession().createQuery("FROM ChannelArchiveRequest ORDER BY sBChannelId, toDate desc");
        return query.list();
    }

    @Override
    public ChannelArchiveRequest getRequestByID(Long id) {
        Query query = getSession().createQuery("FROM ChannelArchiveRequest WHERE id = :id").setLong("id", id);
        if (query.list().size() == 1)
            return (ChannelArchiveRequest) query.list().get(0);
        else
            return null;
    }

}
