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
    public void insert(ChannelArchiveRequest request) throws ServiceException {
        if (request.getFromTime().after(request.getToTime())) {
            throw new ServiceException("fromTime (" + request.getFromTime() + ") must not be after toTime (" + request.getToTime() + ")");
        }
        ChannelArchiveRequestDAOIF dao = new ChannelArchiveRequestDAO();
        dao.create(request);
    }

    @Override
    public void update(ChannelArchiveRequest request) throws ServiceException {
        ChannelArchiveRequestDAOIF dao = new ChannelArchiveRequestDAO();
        dao.update(request);
    }

    @Override
    public void delete(ChannelArchiveRequest request) throws ServiceException {
        ChannelArchiveRequestDAOIF dao = new ChannelArchiveRequestDAO();
        dao.delete(request);

    }

    @Override
    public List<ChannelArchiveRequest> getValidRequests(Date fromDate, Date toDate) {
        ChannelArchiveRequestDAOIF dao = new ChannelArchiveRequestDAO();
        return dao.getValidRequests(fromDate, toDate);
    }



}
