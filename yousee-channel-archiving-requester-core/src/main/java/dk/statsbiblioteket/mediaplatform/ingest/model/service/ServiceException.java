package dk.statsbiblioteket.mediaplatform.ingest.model.service;

/**
 *
 */
public class ServiceException extends Exception {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ServiceException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ServiceException(String message) {
        super(message);
    }
}
