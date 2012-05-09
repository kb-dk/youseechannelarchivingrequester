package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.generic.utils.GenericHibernateDAO;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.ChannelArchiveRequest;

import org.hibernate.Query;
import java.util.Date;
import java.util.List;

/**
 */
public class ChannelArchiveRequestDAO extends GenericHibernateDAO<ChannelArchiveRequest, Long> implements ChannelArchiveRequestDAOIF {

    public ChannelArchiveRequestDAO() {
        super(ChannelArchiveRequest.class);
    }

    @Override
    public List<ChannelArchiveRequest> getValidRequests(Date queryFromDate, Date queryToDate) {
        Query query = getSession().createQuery("FROM ChannelArchiveRequest WHERE " +
                  " (fromDate >= :queryFromDate AND fromDate <= :queryToDate) " +
                  " OR (toDate >= :queryFromDate AND toDate <= :queryToDate) "  +
                  " OR (fromDate <= :queryFromDate AND toDate >= :queryToDate) "
          ).setDate("queryFromDate", queryFromDate).setDate("queryToDate", queryToDate);
        return (List<ChannelArchiveRequest>) query.list();
    }

}
