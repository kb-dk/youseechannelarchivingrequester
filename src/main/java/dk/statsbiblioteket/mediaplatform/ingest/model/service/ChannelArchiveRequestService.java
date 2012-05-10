package dk.statsbiblioteket.mediaplatform.ingest.model.service;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAOIF;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class ChannelArchiveRequestService implements ChannelArchiveRequestServiceIF {

    @Override
    public List<ChannelArchiveRequest> getValidRequests(Date fromDate, Date toDate) {
        ChannelArchiveRequestDAOIF dao = new ChannelArchiveRequestDAO();
        return dao.getValidRequests(fromDate, toDate);
    }

}
