package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

public class HibernateUtil {
    static Logger log = Logger.getLogger(HibernateUtil.class);

    private HibernateUtil() {}

    private static SessionFactory sessionFactory;

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
     *
     * @return
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     *
     * @return
     */
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
