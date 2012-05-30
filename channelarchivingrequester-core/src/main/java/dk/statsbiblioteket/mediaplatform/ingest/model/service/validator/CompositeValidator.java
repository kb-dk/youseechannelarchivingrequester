package dk.statsbiblioteket.mediaplatform.ingest.model.service.validator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class CompositeValidator implements ValidatorIF {

    private List<ValidatorIF> childValidators;

    public CompositeValidator() {
        childValidators = new ArrayList<ValidatorIF>();
    }

    public void addValidator(ValidatorIF validator) {
        childValidators.add(validator);
    }

    @Override
    public List<ValidationFailure> getFailures() {
        List<ValidationFailure> result = new ArrayList<ValidationFailure>();
        for (ValidatorIF validator: childValidators) {
            result.addAll(validator.getFailures());
        }
        return result;
    }

}
