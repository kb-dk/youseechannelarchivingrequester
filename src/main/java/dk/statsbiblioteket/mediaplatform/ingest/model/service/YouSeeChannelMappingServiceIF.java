package dk.statsbiblioteket.mediaplatform.ingest.model.service;

import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.ChannelArchivingRequesterException;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;

import java.util.Date;

/**
 *
 */
public interface YouSeeChannelMappingServiceIF {

    /**
     * Gets the unique mapping corresponding to a given channel in you see on a given date. Throws an exception if there
     * is not a unique mapping.
     * @param youSeeChannelId the YouSee channel
     * @param date the date.
     * @return the unique mapping
     * @throws ChannelArchivingRequesterException if the mappings is not unique
     */
    YouSeeChannelMapping getUniqueMappingFromYouSeeChannelId(String youSeeChannelId, Date date) throws ChannelArchivingRequesterException;

    /**
     * Gets the unique mapping corresponding to a given SB channel on a given date. Throws an exception if there
     * is not a unique mapping.
     * @param sBChannelId
     * @param date the date.
     * @return the unique mapping
     * @throws ChannelArchivingRequesterException if the mappings is not unique
     */
    YouSeeChannelMapping getUniqueMappingFromSbChannelId(String sBChannelId, Date date) throws ChannelArchivingRequesterException;


}
