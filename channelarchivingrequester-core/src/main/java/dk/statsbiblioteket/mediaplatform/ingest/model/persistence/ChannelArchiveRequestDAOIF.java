package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.digitaltv.utils.db.GenericDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface ChannelArchiveRequestDAOIF extends GenericDAO<ChannelArchiveRequest, Long>{


    /**
     * Returns a list of all ChannelArchiveRequest objects which are valid in at least part of the specified date range.
     * @param fromDate Start of date range.
     * @param toDate End of date range.
     * @return the list of vaild requests.
     */
    List<ChannelArchiveRequest> getValidRequests(Date fromDate, Date toDate);

    /**
     * Gets all requests in the database.
     * @return
     */
    List<ChannelArchiveRequest> getAllRequests();
}
