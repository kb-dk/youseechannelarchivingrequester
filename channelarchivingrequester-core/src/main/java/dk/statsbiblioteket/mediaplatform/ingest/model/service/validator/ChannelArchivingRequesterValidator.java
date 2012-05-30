package dk.statsbiblioteket.mediaplatform.ingest.model.service.validator;

/**
 *
 */
public class ChannelArchivingRequesterValidator extends CompositeValidator {

    public ChannelArchivingRequesterValidator() {
        super();
        this.addValidator(new ChannelArchivingRequesterValidator());
    }
}
