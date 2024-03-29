package dk.statsbiblioteket.mediaplatform.ingest.model.service;


import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAOIF;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.NotInitialiasedException;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ChannelArchivingRequesterValidator;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ValidatorIF;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ChannelArchiveRequestService implements ChannelArchiveRequestServiceIF {

    Logger log = LoggerFactory.getLogger(ChannelArchiveRequestService.class);

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
    public List<ChannelArchiveRequest> getValidRequests(ZonedDateTime fromDate, ZonedDateTime toDate) throws ServiceException {
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
