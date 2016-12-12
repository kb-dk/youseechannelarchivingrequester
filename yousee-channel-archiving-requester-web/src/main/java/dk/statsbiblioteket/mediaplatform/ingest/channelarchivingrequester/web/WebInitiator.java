package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import dk.statsbiblioteket.digitv.persistence.ChannelArchivingRequesterHibernateUtil;
import dk.statsbiblioteket.digitv.persistence.HibernateUtilIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 *
 */
public class WebInitiator implements ServletContextListener {

    private static Logger log = LoggerFactory.getLogger(WebInitiator.class);

    /**
     * Place any initialisations or configuration sanity-checks here.
     * @param sce the context in which this class is intiated.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String cfgPath= sce.getServletContext().getInitParameter("hibernate_cfg");
        final File cfgFile = new File(cfgPath);
        log.info("Reading hibernate configuration from " + cfgFile.getAbsolutePath());
        HibernateUtilIF util = ChannelArchivingRequesterHibernateUtil.initialiseFactory(cfgFile);
        util.getSession();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
