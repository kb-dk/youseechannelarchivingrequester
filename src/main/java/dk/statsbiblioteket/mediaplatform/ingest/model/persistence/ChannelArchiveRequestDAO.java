package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.generic.utils.GenericHibernateDAO;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.ChannelArchiveRequest;

import java.util.Date;
import java.util.List;

/**
 */
public class ChannelArchiveRequestDAO extends GenericHibernateDAO<ChannelArchiveRequest, Long> implements ChannelArchiveRequestDAOIF {

    public ChannelArchiveRequestDAO() {
        super(ChannelArchiveRequest.class);
    }

    @Override
    public List<ChannelArchiveRequest> getValidRequests(Date fromDate, Date toDate) {
           throw new RuntimeException("not implemented");
    }

}
