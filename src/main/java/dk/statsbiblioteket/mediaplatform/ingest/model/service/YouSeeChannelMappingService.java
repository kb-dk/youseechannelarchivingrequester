package dk.statsbiblioteket.mediaplatform.ingest.model.service;

import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.YouSeeChannelMappingDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.YouSeeChannelMappingDAOIF;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class YouSeeChannelMappingService implements YouSeeChannelMappingServiceIF {
    @Override
    public YouSeeChannelMapping getUniqueMappingFromYouSeeChannelId(String youSeeChannelId, Date date) throws ServiceException {
        YouSeeChannelMappingDAOIF dao = new YouSeeChannelMappingDAO();
        List<YouSeeChannelMapping> mappings = dao.getMappingsFromYouSeeChannelId(youSeeChannelId, date);
        if (mappings.size() == 1) {
            return mappings.get(0);
        } else {
            throw new ServiceException("Expected a unique mapping for '" + youSeeChannelId + " at "
                    + date + " but found " + mappings.size());
        }
    }

    @Override
    public YouSeeChannelMapping getUniqueMappingFromSbChannelId(String sBChannelId, Date date) throws ServiceException {
        YouSeeChannelMappingDAOIF dao = new YouSeeChannelMappingDAO();
        List<YouSeeChannelMapping> mappings = dao.getMappingsFromSbChannelId(sBChannelId, date);
        if (mappings.size() == 1) {
            return mappings.get(0);
        } else {
            throw new ServiceException("Expected a unique mapping for '" + sBChannelId  + " at "
                    + date + " but found " + mappings.size());
        }
    }

    @Override
    public List<YouSeeChannelMapping> getAllMappings() throws ServiceException {
        YouSeeChannelMappingDAOIF dao = new YouSeeChannelMappingDAO();
        return dao.getAllMappings();
    }
}
