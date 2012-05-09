package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

/**
 * A fairly basic hibernate utility which can be initialised from a configuration file.
 */
public class HibernateUtil {
    static Logger log = Logger.getLogger(HibernateUtil.class);

    private HibernateUtil() {}

    private static SessionFactory sessionFactory;

    /**
     * Initialise hibernate from a configuration file on first call. Subsequent calls will not reinitialise the
     * hibernate connection unless the sessionFactory is first closed.
     * @param cfgFile  The configuration file.
     * @return An instance of this class.
     */
    public static HibernateUtil initialiseFactory(File cfgFile) {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                try {
                    sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
                } catch (Throwable e) {
                    log.info("Could not load hibernate configuration from classpath, trying from properties");
                }
                if (sessionFactory == null) {

                    sessionFactory = new AnnotationConfiguration().configure(cfgFile).buildSessionFactory();
                }
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed." + ex);
                ex.printStackTrace();
                throw new ExceptionInInitializerError(ex);
            }
        }
        return new HibernateUtil();
    }

    /**
     * Gets a session factory
     * @return
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Gets a hibernate session. This class must be initialised before this method is called.
     * @return a Session
     */
    public Session getSession() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            throw new RuntimeException("Attempt to use HibernateUtil before it was initialised or " +
                    "after sessionFactory was closed");
        }
        return sessionFactory.openSession();
    }
}
