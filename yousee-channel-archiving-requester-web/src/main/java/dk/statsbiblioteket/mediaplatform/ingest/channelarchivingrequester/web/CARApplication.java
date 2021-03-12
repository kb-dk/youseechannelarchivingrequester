package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class CARApplication extends Application {

    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
            JacksonJsonProvider.class,
            ChannelArchiveRequestRESTServlet.class,
            YouSeeChannelMappingRESTServlet.class)
            );
    }

}
