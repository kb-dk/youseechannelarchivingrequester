package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;


import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
//import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

/**
 * A fairly basic hibernate utility which can be initialised from a configuration file.
 */
public class ChannelArchivingRequesterHibernateUtil implements HibernateUtilIF {
    //static Logger log = Logger.getLogger(ChannelArchivingRequesterHibernateUtil.class);

    private ChannelArchivingRequesterHibernateUtil() {
    }

    private static SessionFactory sessionFactory;

    public static HibernateUtilIF getInitialisedFactory() throws NotInitialiasedException {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            return new ChannelArchivingRequesterHibernateUtil();
        } else {
            throw new NotInitialiasedException("Attempt to access uninitialised Hibernate utility.");
        }
    }

    /**
     * Initialise hibernate from a configuration file on first call. Subsequent calls will not reinitialise the
     * hibernate connection unless the sessionFactory is first closed.
     *
     * @param cfgFile The configuration file.
     * @return An instance of this class.
     */
    public static HibernateUtilIF initialiseFactory(File cfgFile) {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                if (sessionFactory == null) {
                    AnnotationConfiguration configure = (new AnnotationConfiguration()).configure(cfgFile);
                    configure.addAnnotatedClass(ChannelArchiveRequest.class);
                    configure.addAnnotatedClass(YouSeeChannelMapping.class);
                    sessionFactory = configure.buildSessionFactory();
                }
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                ex.printStackTrace();
                throw new ExceptionInInitializerError(ex);
            }
        }
        return new ChannelArchivingRequesterHibernateUtil();
    }

    /**
     * Gets a session factory
     *
     * @return A session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Gets a hibernate session. This class must be initialised before this method is called.
     *
     * @return a Session
     */
    public Session getSession() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            throw new RuntimeException("Attempt to use ChannelArchivingRequesterHibernateUtil before it was initialised or " +
                    "after sessionFactory was closed");
        }
        return sessionFactory.openSession();
    }


}
