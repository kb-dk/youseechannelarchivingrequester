package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.ChannelArchivingRequestException;
import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.YouSeeChannelMapping;

import java.util.Date;

/**
 */
public interface YouSeeChannelMappingDAOIF {

    /**
     * Gets a YouSeeChannelMapping from a given youSeeChannelId valid now. If there is not esactly one
     * mapping it throws a ChannelArchivingRequestException
     * @param youSeeChannelId  the YouSee id
     * @return  the unique valid mapping
     * @throws ChannelArchivingRequestException is there is not a unique mapping
     */
    YouSeeChannelMapping getMappingFromYouSeeChannelId(String youSeeChannelId) throws ChannelArchivingRequestException;

    /**
     * Gets a YouSeeChannelMapping from a given youSeeChannelId valid on a given date. If there is not esactly one
     * mapping it throws a ChannelArchivingRequestException
     * @param youSeeChannelId  the YouSee id
     * @param date the date for the request
     * @return  the unique valid mapping
     * @throws ChannelArchivingRequestException is there is not a unique mapping
     */
    YouSeeChannelMapping getMappingFromYouSeeChannelId(String youSeeChannelId, Date date) throws ChannelArchivingRequestException;

     /**
     * Gets a YouSeeChannelMapping from a given sBChannelId valid now. If there is not exactly one
     * mapping it throws a ChannelArchivingRequestException
     * @param sBChannelId the SB id of the channel
     * @return  the unique valid mapping
     * @throws ChannelArchivingRequestException is there is not a unique mapping
     */
    YouSeeChannelMapping getMappingFromSbChannelId(String sBChannelId) throws ChannelArchivingRequestException;

    /**
     * Gets a YouSeeChannelMapping from a given sBChannelId valid on a given date. If there is not esactly one
     * mapping it throws a ChannelArchivingRequestException
     * @param sBChannelId the SB id of the channel
     * @param date the date for the request
     * @return  the unique valid mapping
     * @throws ChannelArchivingRequestException is there is not a unique mapping
     */
    YouSeeChannelMapping getMappingFromSbChannelId(String sBChannelId, Date date) throws ChannelArchivingRequestException;
}
