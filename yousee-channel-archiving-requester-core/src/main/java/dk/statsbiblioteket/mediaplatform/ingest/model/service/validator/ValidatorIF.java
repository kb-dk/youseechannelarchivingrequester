package dk.statsbiblioteket.mediaplatform.ingest.model.service.validator;

import java.util.List;

/**
 *
 */
public interface ValidatorIF {

    /**
     * Returns a list of validation failures for this validator. Empty if there are no failures.
     * @return the validation failures.
     */
    List<ValidationFailure> getFailures();

}
