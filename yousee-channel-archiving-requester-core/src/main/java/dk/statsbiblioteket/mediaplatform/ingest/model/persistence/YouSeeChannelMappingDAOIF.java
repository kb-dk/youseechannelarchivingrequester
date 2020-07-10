package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.digitaltv.utils.db.GenericDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;

import java.time.ZonedDateTime;
import java.util.List;

/**
 */
public interface YouSeeChannelMappingDAOIF extends GenericDAO<YouSeeChannelMapping, Long> {


    /**
     * Gets the YouSeeChannelMapping for a given youSeeChannelId valid on a given date.
     *
     * @param youSeeChannelId the YouSee id
     * @param date            the date for the request
     * @return the mappings
     */
    List<YouSeeChannelMapping> getMappingsFromYouSeeChannelId(String youSeeChannelId, ZonedDateTime date);


    /**
     * Gets the YouSeeChannelMappings for a given sBChannelId valid on a given date.
     *
     * @param sBChannelId the SB id of the channel
     * @param date        the date for the request
     * @return the mappings
     */
    List<YouSeeChannelMapping> getMappingsFromSbChannelId(String sBChannelId, ZonedDateTime date);

    /**
     * Get all known mappings, expired or not.
     *
     * @return The mappings.
     */
    List<YouSeeChannelMapping> getAllMappings();

    /**
     * @param id The id of the wanted channel mapping
     * @return List of the wanted channel mapping objects from the database
     */
    YouSeeChannelMapping getMappingByID(Long id);
}
