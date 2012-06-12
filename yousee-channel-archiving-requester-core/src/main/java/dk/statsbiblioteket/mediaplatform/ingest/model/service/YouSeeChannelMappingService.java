package dk.statsbiblioteket.mediaplatform.ingest.model.service;

import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.NotInitialiasedException;
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
        List<YouSeeChannelMapping> mappings = null;
        try {
            mappings = getDao().getMappingsFromYouSeeChannelId(youSeeChannelId, date);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
        if (mappings.size() == 1) {
            return mappings.get(0);
        } else {
            throw new ServiceException("Expected a unique mapping for '" + youSeeChannelId + " at "
                    + date + " but found " + mappings.size());
        }
    }

    @Override
    public YouSeeChannelMapping getUniqueMappingFromSbChannelId(String sBChannelId, Date date) throws ServiceException {
        List<YouSeeChannelMapping> mappings = null;
        try {
            mappings = getDao().getMappingsFromSbChannelId(sBChannelId, date);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
        if (mappings.size() == 1) {
            return mappings.get(0);
        } else {
            throw new ServiceException("Expected a unique mapping for '" + sBChannelId  + " at "
                    + date + " but found " + mappings.size());
        }
    }

    @Override
    public List<YouSeeChannelMapping> getAllMappings() throws ServiceException {
        try {
            return getDao().getAllMappings();
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(YouSeeChannelMapping mapping) throws ServiceException {
        try {
            getDao().create(mapping);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(YouSeeChannelMapping mapping) throws ServiceException {
        try {
            getDao().update(mapping);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(YouSeeChannelMapping mapping) throws ServiceException {
        try {
            getDao().delete(mapping);
        } catch (NotInitialiasedException e) {
            throw new ServiceException(e);
        }
    }

    private YouSeeChannelMappingDAOIF getDao() throws NotInitialiasedException {
        return new YouSeeChannelMappingDAO(ChannelArchivingRequesterHibernateUtil.getInitialisedFactory());
    }

}
