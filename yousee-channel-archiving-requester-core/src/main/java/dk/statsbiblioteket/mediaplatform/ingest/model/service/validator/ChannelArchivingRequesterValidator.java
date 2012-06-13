package dk.statsbiblioteket.mediaplatform.ingest.model.service.validator;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;

import java.util.List;

/**
 *
 */
public class ChannelArchivingRequesterValidator extends CompositeValidator {

    public ChannelArchivingRequesterValidator() {
        super();
        this.addValidator(new NonoverlappingChannelMappingsValidator());
    }

    /**
     * A utility method which, given a list of requests and failures, marks as disabled any requests for which there is
     * a failure.
     * @param requests
     * @param failures
     */
    public static void markFailuresAsDisabled(List<ChannelArchiveRequest> requests, List<ValidationFailure> failures) {
         for (ChannelArchiveRequest request: requests) {
             for (ValidationFailure failure: failures) {
                 if (request.getsBChannelId().equals(failure.getAffectedSBChannel())) {
                     request.setEnabled(false);
                     request.setCause(request.getCause() + failure.getCause() + " ");
                 }
             }
         }
    }
}
