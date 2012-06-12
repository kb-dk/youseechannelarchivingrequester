package dk.statsbiblioteket.mediaplatform.ingest.model.service.validator;

/**
 *
 */
public class ValidationFailure {

    private String affectedSBChannel;
    private String cause;

    public String getAffectedSBChannel() {
        return affectedSBChannel;
    }

    public void setAffectedSBChannel(String affectedSBChannel) {
        this.affectedSBChannel = affectedSBChannel;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
