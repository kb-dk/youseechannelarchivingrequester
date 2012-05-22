
package dk.statsbiblioteket.mediaplatform.ingest.model.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface HibernateUtilIF {

    public SessionFactory getSessionFactory();

    public Session getSession();

}
