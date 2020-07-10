package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;

import org.hibernate.Query;
import org.hibernate.Session;

import java.time.ZonedDateTime;
import java.util.List;

/**
 */
public class ChannelArchiveRequestDAO extends GenericHibernateDAO<ChannelArchiveRequest, Long> implements ChannelArchiveRequestDAOIF {

    public ChannelArchiveRequestDAO(HibernateUtilIF util) {
        super(ChannelArchiveRequest.class, util);
    }

    @Override
    public List<ChannelArchiveRequest> getValidRequests(ZonedDateTime queryFromDate, ZonedDateTime queryToDate) {
        Session sess = null;
        try {
            sess = getSession();
            Query query = sess.createQuery("FROM ChannelArchiveRequest WHERE " +
                            " (fromDate >= :queryFromDate AND fromDate <= :queryToDate) " +
                            " OR (toDate >= :queryFromDate AND toDate <= :queryToDate) " +
                            " OR (fromDate <= :queryFromDate AND toDate >= :queryToDate) "
            ).setParameter("queryFromDate", queryFromDate).setParameter("queryToDate", queryToDate);
            return (List<ChannelArchiveRequest>) query.list();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Override
    public List<ChannelArchiveRequest> getAllRequests() {
        Session sess = null;
        try {
            sess = getSession();
            Query query = sess.createQuery("FROM ChannelArchiveRequest ORDER BY sBChannelId, toDate desc");
            return query.list();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Override
    public ChannelArchiveRequest getRequestByID(Long id) {
        Session sess = null;
        try {
            sess = getSession();
            Query query = sess.createQuery("FROM ChannelArchiveRequest WHERE id = :id").setLong("id", id);
            if (query.list().size() == 1)
                return (ChannelArchiveRequest) query.list().get(0);
            else
                return null;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

}
