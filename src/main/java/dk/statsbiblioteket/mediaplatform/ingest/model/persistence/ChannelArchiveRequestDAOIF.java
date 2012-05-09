package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.ChannelArchiveRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: csr
 * Date: 5/8/12
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelArchiveRequestDAOIF {
    List<ChannelArchiveRequest> getValidRequests(Date fromDate, Date toDate);
}
