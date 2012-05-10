package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.HibernateUtil;

import javax.servlet.Servlet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 *
 */
public class WebInitiator implements ServletContextListener {

    /**
     * Place any initialisations or configuration sanity-checks here.
     * @param sce the context in which this class is intiated.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String cfgPath= sce.getServletContext().getInitParameter("hibernate_cfg");
        HibernateUtil util = HibernateUtil.initialiseFactory(new File(cfgPath));
        util.getSession();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
