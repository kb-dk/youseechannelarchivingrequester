package dk.statsbiblioteket.mediaplatform.ingest.model.service;

import dk.statsbiblioteket.digitv.persistence.ChannelArchivingRequesterHibernateUtil;
import dk.statsbiblioteket.digitv.persistence.NotInitialiasedException;
import dk.statsbiblioteket.digitv.persistence.channelarchiverequest.ChannelArchiveRequest;
import dk.statsbiblioteket.digitv.persistence.channelarchiverequest.ChannelArchiveRequestDAO;
import dk.statsbiblioteket.digitv.persistence.channelarchiverequest.ChannelArchiveRequestDAOIF;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ChannelArchivingRequesterValidator;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ValidatorIF;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class ChannelArchiveRequestService implements ChannelArchiveRequestServiceIF {

    Logger log = Logger.getLogger(ChannelArchiveRequestService.class);

    @Override
    public void insert(ChannelArchiveRequest request) throws ServiceException {
        if ((request.getFromTime() != null) && (request.getToTime() != null)) {
            if (request.getFromTime().after(request.getToTime())) {
                throw new ServiceException("fromTime (" + request.getFromTime() + ") must not be after toTime (" + request.getToTime() + ")");
            }
        }
        try {
            getDao().create(request);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(ChannelArchiveRequest request) throws ServiceException {
        try {
            getDao().update(request);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(ChannelArchiveRequest request) throws ServiceException {
        try {
            getDao().delete(request);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<ChannelArchiveRequest> getValidRequests(Date fromDate, Date toDate) throws ServiceException {
        List<ChannelArchiveRequest> validRequests;
        try {
            validRequests = getDao().getValidRequests(fromDate, toDate);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
        ValidatorIF validator = new ChannelArchivingRequesterValidator();
        ChannelArchivingRequesterValidator.markFailuresAsDisabled(validRequests, validator.getFailures());
        log.info("Found " + validator.getFailures().size() + " validation failure(s).");
        return validRequests;
    }

    @Override
    public List<ChannelArchiveRequest> getAllRequests() throws ServiceException {
        try {
            return getDao().getAllRequests();
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public ChannelArchiveRequest getRequestByID(Long id) throws ServiceException {
        try {
            return getDao().getRequestByID(id);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }

    }

    private ChannelArchiveRequestDAOIF getDao() throws NotInitialiasedException {
        return new ChannelArchiveRequestDAO(ChannelArchivingRequesterHibernateUtil.getInitialisedFactory());
    }

}
