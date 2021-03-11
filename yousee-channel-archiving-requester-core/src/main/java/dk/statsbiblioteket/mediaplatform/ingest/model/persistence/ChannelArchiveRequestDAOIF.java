package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.digitaltv.utils.db.GenericDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;

import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 */
public interface ChannelArchiveRequestDAOIF extends GenericDAO<ChannelArchiveRequest, Long> {


    /**
     * Returns a list of all ChannelArchiveRequest objects which are valid in at least part of the specified date range.
     *
     * @param fromDate Start of date range.
     * @param toDate   End of date range.
     * @return the list of valid requests.
     */
    List<ChannelArchiveRequest> getValidRequests(ZonedDateTime fromDate, ZonedDateTime toDate);

    /**
     * Get the request from ID in the database.
     *
     * @param id The id of the wanted request
     * @return the wanted request
     */
    ChannelArchiveRequest getRequestByID(Long id);


    /**
     * Gets all requests in the database.
     *
     * @return List of all requests
     */
    List<ChannelArchiveRequest> getAllRequests();
}
