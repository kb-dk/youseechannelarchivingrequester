package dk.statsbiblioteket.mediaplatform.ingest.model.service;

import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;

import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 */
public interface YouSeeChannelMappingServiceIF {

    /**
     * Gets the unique mapping corresponding to a given channel in you see on a given date. Throws an exception if there
     * is not a unique mapping.
     *
     * @param youSeeChannelId the YouSee channel
     * @param date            the date.
     * @return the unique mapping
     * @throws ServiceException if the mappings is not unique
     */
    YouSeeChannelMapping getUniqueMappingFromYouSeeChannelId(String youSeeChannelId, ZonedDateTime date) throws ServiceException;

    /**
     * Gets the unique mapping corresponding to a given SB channel on a given date. Throws an exception if there
     * is not a unique mapping.
     *
     * @param sBChannelId
     * @param date        the date.
     * @return the unique mapping
     * @throws ServiceException if the mappings is not unique
     */
    YouSeeChannelMapping getUniqueMappingFromSbChannelId(String sBChannelId, ZonedDateTime date) throws ServiceException;

    /**
     * Get a complete list of all known mappings,sorted by expiry date.
     *
     * @return The list of known mappings.
     * @throws ServiceException
     */
    List<YouSeeChannelMapping> getAllMappings() throws ServiceException;

    /**
     * Returns a list of rows with given id from the channel mapping table in the database
     *
     * @param id: Unique id of the requested channel mapping object
     * @return List of requested channel mappings object(s) with the given id
     */
    YouSeeChannelMapping getMappingByID(Long id) throws ServiceException;

    void create(YouSeeChannelMapping mapping) throws ServiceException;

    void update(YouSeeChannelMapping mapping) throws ServiceException;

    void delete(YouSeeChannelMapping mapping) throws ServiceException;

}
